package ru.otus.mapper;

import ru.otus.annotations.Id;
import ru.otus.annotations.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private Class<T> typeClass;

    public EntityClassMetaDataImpl(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public Class<T> getTypeClass() {
        return typeClass;
    }

    @Override
    public String getName() {
        Table annotation = typeClass.getAnnotation(Table.class);
        if (annotation != null) {
            return annotation.name().toLowerCase();
        } else {
            return typeClass.getName().toLowerCase();
        }
    }

    @Override
    public Constructor<T> getConstructor() {
        //Какой именно конструктор брать, если в объекте их несколько?
        Constructor<?>[] declaredConstructors = typeClass.getDeclaredConstructors();
        Constructor<?> result = Arrays.stream(declaredConstructors)
                .filter(constructor -> constructor.getParameterCount() == getAllFields().size())
                .findFirst().orElse(declaredConstructors[0]);
        return (Constructor<T>) result;
    }

    @Override
    public Field getIdField() {
        Field idField = Arrays.stream(typeClass.getDeclaredFields())
                .filter(field -> field.getAnnotation(Id.class) != null)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Отсутсвует аннотация @Id в классе!"));
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(typeClass.getDeclaredFields()).collect(Collectors.toList());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(typeClass.getDeclaredFields())
                .filter(field -> field.getAnnotation(Id.class) == null)
                .collect(Collectors.toList());
    }
}
