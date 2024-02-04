package de.nuberjonas.pompalette.mapping.mappingapi.mapper;

import de.nuberjonas.pompalette.mapping.mappingapi.exceptions.MappingException;

import java.lang.reflect.Field;

public class SuperClassMapper {

    private SuperClassMapper() {
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static <T, S extends T> S copyFields(T source, Class<S> targetType) {
        if (source == null) {
            return null;
        }

        try {
            S target = targetType.getDeclaredConstructor().newInstance();

            Class<?> targetClass = targetType;
            while (targetClass != null) {
                for (Field field : targetClass.getDeclaredFields()) {
                    copyField(source, target, field);
                }
                targetClass = targetClass.getSuperclass();
            }

            return target;
        } catch (Exception e) {
            throw new MappingException("Cannot copy fields of super class to extending class", e);
        }
    }

    private static void copyField(Object source, Object target, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Field sourceField = getField(source.getClass(), field.getName());
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

