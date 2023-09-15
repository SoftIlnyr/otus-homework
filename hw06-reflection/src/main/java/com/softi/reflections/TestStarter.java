package com.softi.reflections;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestStarter {

    @SneakyThrows
    public static void main(String[] args) {
        TestStarter testStarter = new TestStarter();

        List<String> classesForTest = List.of("com.softi.reflections.FruitBoxCustomTest",
                "com.softi.reflections.FruitBoxCustomTest2");

        for (String classForTest : classesForTest) {
            Class<?> testClass = Class.forName(classForTest);
            testStarter.start(testClass);
        }
    }

    @SneakyThrows
    public void start(Class<?> testClass) {
        Method[] declaredMethods = testClass.getDeclaredMethods();

        List<Method> beforeTestMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterTestMethods = new ArrayList<>();

        Arrays.stream(declaredMethods)
                .filter(method -> method.getAnnotation(Before.class) != null)
                .forEach(beforeTestMethods::add);

        Arrays.stream(declaredMethods)
                .filter(method -> method.getAnnotation(Test.class) != null)
                .forEach(testMethods::add);

        Arrays.stream(declaredMethods)
                .filter(method -> method.getAnnotation(After.class) != null)
                .forEach(afterTestMethods::add);

        System.out.printf("Start test for class %s%n", testClass.getName());
        
        Object classInstance = testClass.getDeclaredConstructor().newInstance();

        int passedTestCount = 0;
        int failedTestCount = 0;
        try {
            for (Method method : beforeTestMethods) {
                method.invoke(classInstance);
            }
            for (Method method : testMethods) {
                System.out.println(method.getName());
                try {
                    method.invoke(classInstance);
                    passedTestCount++;
                } catch (Exception testClassException) {
                    System.out.println(testClassException);
                    failedTestCount++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            for (Method method : afterTestMethods) {
                method.invoke(classInstance);
            }
        }
        

        System.out.printf("End test for class %s%n", testClass.getName());
        System.out.printf("Overall tests: %s%n", testMethods.size());
        System.out.printf("Passed tests: %s%n", passedTestCount);
        System.out.printf("Failed tests: %s%n", failedTestCount);

    }

}
