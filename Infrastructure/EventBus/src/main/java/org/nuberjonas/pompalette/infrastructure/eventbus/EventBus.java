package org.nuberjonas.pompalette.infrastructure.eventbus;

import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class EventBus {
    private static EventBus instance;
    private Map<Class<? extends Event<?>>, Set<Subscribable>> subscribers;

    private EventBus(){
        subscribers = new HashMap<>();
    }

    public static synchronized EventBus getInstance(){
        if(instance == null){
            instance = new EventBus();
        }

        return instance;
    }

    public synchronized <T> void subscribe(Class<? extends Event<T>> eventType, Subscribable subscriber){
        if(subscriber.supports().contains(eventType)){
            subscribers.computeIfAbsent(eventType, key -> ConcurrentHashMap.newKeySet()).add(subscriber);
        } else {
            throw new UnsupportedEventException(String.format("The event type %s is not supported by the subscribable %s", eventType.getName(), subscriber.getClass().getName()));
        }
    }

    public synchronized <T> void unsubscribe(Class<? extends Event<T>> eventType, Subscribable subscriber){
        subscribers.computeIfPresent(eventType, (key, value) -> {
            Set<Subscribable> updatedSubscribers = value.stream()
                    .filter(sub -> !sub.equals(subscriber))
                    .collect(Collectors.toSet());
            return updatedSubscribers.isEmpty() ? null : updatedSubscribers;
        });
    }


    public synchronized <T> void publish(Event<T> event){
        Class<?> eventType = event.getClass();
        subscribers.getOrDefault(eventType, Set.of())
                .forEach(subscriber -> subscriber.handleEvent(event));
    }
}
