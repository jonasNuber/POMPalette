package org.nuberjonas.pompalette.mapping.projectmapping;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface ElementComparator<T, U> {
    void compareElements(T element1, U element2) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
}
