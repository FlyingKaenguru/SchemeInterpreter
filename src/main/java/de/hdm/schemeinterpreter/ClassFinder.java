package de.hdm.schemeinterpreter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

public class ClassFinder {
    public static <T> Class<T>[] getImplementations(Class<T> interfacce, String packageName) {
        return Arrays.stream(getAllClasses(packageName))
                .filter(e -> !e.isInterface())
                .filter(interfacce::isAssignableFrom).toArray(Class[]::new);
    }

    public static Class<?>[] getAllClasses(String packageName) {
        final String directory = Thread.currentThread().getContextClassLoader().getResource(packageName.replace('.', '/')).getFile();
        final File packageDirectory = new File(directory);

        if (packageDirectory.exists()) {
            return Arrays.stream(Objects.requireNonNull(packageDirectory.list()))
                    .filter(e -> e.endsWith(".class") && !e.contains("$"))
                    .map(e -> packageName + "." + e)
                    .map(ClassFinder::getClass)
                    .filter(Objects::nonNull)
                    .toArray(Class[]::new);
        }

        return new Class[]{};
    }

    public static Class<?> getClass(String fullClassName) {
        try {
            return Class.forName(fullClassName.substring(0, fullClassName.length() - 6));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T getClassInstance(Class<? extends T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
