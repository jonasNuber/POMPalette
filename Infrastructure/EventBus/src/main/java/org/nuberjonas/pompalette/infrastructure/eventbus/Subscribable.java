package org.nuberjonas.pompalette.infrastructure.eventbus;

import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.util.Set;

public interface Subscribable {

    void handleEvent(Event<?> event);

    Set<Class<?>> supports();
}
