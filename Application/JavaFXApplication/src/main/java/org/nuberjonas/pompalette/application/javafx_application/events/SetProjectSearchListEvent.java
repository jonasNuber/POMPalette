package org.nuberjonas.pompalette.application.javafx_application.events;

import org.nuberjonas.pompalette.core.model.domain.searchlist.ProjectSearchListElement;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.util.List;

public class SetProjectSearchListEvent extends Event<List<ProjectSearchListElement>> {
    public SetProjectSearchListEvent(List<ProjectSearchListElement> data) {
        super(data);
    }
}
