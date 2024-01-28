package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory;

@FunctionalInterface
public interface MapperFunction<T, U> {
    U map(T input);
}
