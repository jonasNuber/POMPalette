package org.nuberjonas.pompalette.application.javafx_application.gui.projectsearchlist.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nuberjonas.pompalette.application.javafx_application.events.SetProjectSearchListEvent;
import org.nuberjonas.pompalette.core.model.domain.searchlist.ProjectSearchListElement;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.util.Set;

public class ProjectSearchListViewModel implements Subscribable {
    private final ListProperty<ProjectSearchListElement> items;
    private final ObservableList<ProjectSearchListElement> originalItems;

    public ProjectSearchListViewModel() {
        items = new SimpleListProperty<>(FXCollections.observableArrayList());
        originalItems = FXCollections.observableArrayList();

        EventBus.getInstance().subscribe(SetProjectSearchListEvent.class, this);
    }

    public void filterItems(String filter) {
        ObservableList<ProjectSearchListElement> sourceList = originalItems;
        if (filter == null || filter.isEmpty()) {
            items.setAll(sourceList);
        } else {
            ObservableList<ProjectSearchListElement> filteredItems = FXCollections.observableArrayList();
            for (ProjectSearchListElement item : sourceList) {
                if (item.projectVertex().element().getCoordinates().toString().toLowerCase().contains(filter.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
            items.setAll(filteredItems);
        }
    }

    public ListProperty<ProjectSearchListElement> itemsProperty() {
        return items;
    }

    public ObservableList<ProjectSearchListElement> getItems() {
        return items.get();
    }

    @Override
    public void handleEvent(Event<?> event) {
        if(event instanceof SetProjectSearchListEvent setProjectSearchListEvent){
            items.setAll(setProjectSearchListEvent.getData());
            originalItems.setAll(setProjectSearchListEvent.getData());
        }
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(SetProjectSearchListEvent.class);
    }
}
