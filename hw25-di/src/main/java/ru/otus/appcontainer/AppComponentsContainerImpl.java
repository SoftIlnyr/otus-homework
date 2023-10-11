package ru.otus.appcontainer;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @Getter
    @Setter
    private static final class AppBeanMetadata {
        private String beanName;
        private String className;
        private int order;
        private Method beanConstructor;
        private Class<?> type;
    }

    @SneakyThrows
    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        List<AppBeanMetadata> appBeanMetadataList = new ArrayList<>();
        for (Method method : configClass.getDeclaredMethods()) {
            AppComponent annotation = method.getAnnotation(AppComponent.class);
            if (annotation == null) {
                continue;
            }

            Class<?> beanType = method.getReturnType();
            AppBeanMetadata appBeanMetadata = new AppBeanMetadata();
            appBeanMetadata.setType(beanType);
            appBeanMetadata.setOrder(annotation.order());
            appBeanMetadata.setBeanName(annotation.name());
            appBeanMetadata.setClassName(beanType.getName());
            appBeanMetadata.setBeanConstructor(method);
            appBeanMetadataList.add(appBeanMetadata);
        }

        appBeanMetadataList.sort(Comparator.comparingInt(o -> o.order));

        Object configClassInstance = configClass.getDeclaredConstructors()[0].newInstance();

        for (AppBeanMetadata appBeanMetadata : appBeanMetadataList) {
            Method beanConstructor = appBeanMetadata.getBeanConstructor();

            if (appComponentsByName.containsKey(appBeanMetadata.beanName)) {
                throw new RuntimeException(String.format("Bean with name '%s' already exists, ", appBeanMetadata.beanName));
            }

            Object bean = null;
            if (beanConstructor.getParameterCount() == 0) {
                bean = beanConstructor.invoke(configClassInstance);
            } else {
                Object[] enterParameters = new Object[beanConstructor.getParameterCount()];
                int parameterCount = 0;
                for (Parameter parameter : beanConstructor.getParameters()) {
                    Class<?> parameterType = parameter.getType();
                    Object appComponent = findMatchingComponent(parameterType);
                    if (appComponent == null) {
                        throw new RuntimeException(String.format("Couldn't find matching candidates for type %s.", parameterType.getName()));
                    }
                    enterParameters[parameterCount] = appComponent;
                    parameterCount++;
                }
                bean = beanConstructor.invoke(configClassInstance, enterParameters);
            }
            if (bean != null) {
                appComponents.add(bean);
                appComponentsByName.put(appBeanMetadata.beanName, bean);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Object appComponent = findMatchingComponent(componentClass);
        if (appComponent == null) {
            throw new RuntimeException(String.format("Couldn't find matching candidates for type %s.", componentClass.getName()));
        }
        return (C) appComponent;
    }

    private <C> Object findMatchingComponent(Class<?> componentClass) {
        List<Object> foundComponents = appComponents.stream()
                .filter(componentClass::isInstance)
                .toList();
        if (foundComponents.size() > 1) {
            throw new RuntimeException(String.format("Expected one mathcing bean with type '%s' but found %d.", componentClass.getName(), foundComponents.size()));
        }
        return foundComponents.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object appComponent = appComponentsByName.get(componentName);
        if (appComponent == null) {
            throw new RuntimeException(String.format("Couldn't find matching candidates for name '%s'.", componentName));
        }
        return (C) appComponent;
    }
}
