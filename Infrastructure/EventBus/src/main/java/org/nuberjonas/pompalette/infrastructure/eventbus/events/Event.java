package org.nuberjonas.pompalette.infrastructure.eventbus.events;

public class Event<T> {
    private final T data;

    public Event(T data){
        this.data = data;
    }

    public T getData(){
        return data;
    }
}
