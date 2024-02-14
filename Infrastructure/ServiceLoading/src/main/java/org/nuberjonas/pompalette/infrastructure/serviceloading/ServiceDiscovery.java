package org.nuberjonas.pompalette.infrastructure.serviceloading;

import org.nuberjonas.pompalette.infrastructure.serviceloading.exceptions.ServiceNotFoundException;

import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;

public class ServiceDiscovery {

    private ServiceDiscovery(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    @SuppressWarnings("unchecked")
    public static <T> T loadService(Class<T> serviceType) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(serviceType);

        return (T) serviceLoader.stream().findFirst().orElseThrow(
                () -> new ServiceNotFoundException(String.format("No Service of type: %s could be found.", serviceType.getName())));
    }

    public static <T> T loadService(Class<T> serviceType, Predicate<? super ServiceLoader.Provider<T>> filter) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(serviceType);

        return serviceLoader.stream()
                .filter(filter)
                .findFirst()
                .map(ServiceLoader.Provider::get)
                .orElseThrow(() -> new ServiceNotFoundException(
                        String.format("No Service of type: %s could be found with the specified Predicate.", serviceType.getName())));
    }

    public static <T> Iterator<T> loadServices(Class<T> serviceType) {
        return Optional.of(ServiceLoader.load(serviceType).iterator())
                .filter(Iterator::hasNext)
                .orElseThrow(
                        () -> new ServiceNotFoundException(String.format("No service implementations found for %s", serviceType.getName())));
    }
}
