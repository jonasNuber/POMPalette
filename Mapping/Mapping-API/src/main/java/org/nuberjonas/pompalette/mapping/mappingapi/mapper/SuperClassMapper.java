package org.nuberjonas.pompalette.mapping.mappingapi.mapper;

import org.nuberjonas.pompalette.mapping.mappingapi.exceptions.MappingException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SuperClassMapper {

    private SuperClassMapper() {
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static <T, S extends T> S mapFields(T source, Class<S> targetType) {
        if (source == null) {
            return null;
        }

        if (!source.getClass().isAssignableFrom(targetType)) {
            throw new MappingException("Target type must be a subclass of source type");
        }

        try {
            S target = targetType.getDeclaredConstructor().newInstance();

            Class<?> sourceClass = source.getClass();
            while (sourceClass != null && !sourceClass.equals(Object.class)) {
                for (Field field : sourceClass.getDeclaredFields()) {
                    copyField(source, target, field, sourceClass);
                }
                sourceClass = sourceClass.getSuperclass();
            }

            return target;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new MappingException("Cannot copy fields of super class to extending class", e);
        }
    }

    private static void copyField(Object source, Object target, Field field, Class<?> sourceClass) throws IllegalAccessException {
        field.setAccessible(true);
        Field sourceField = getField(sourceClass, field.getName());
        if (sourceField != null) {
            sourceField.setAccessible(true);
            field.set(target, sourceField.get(source));
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}

