package org.nuberjonas.pompalette.application.javafx_application.loadproject.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.LoadProjectInitializationEvent;

public class LoadProjectViewModel {

    private StringProperty projectPathProperty;

    public LoadProjectViewModel(){
        projectPathProperty = new SimpleStringProperty();
    }

    public void initiateLoading(){
        EventBus.getInstance().publish(new LoadProjectInitializationEvent(projectPathProperty.get()));
    }

    public StringProperty projectPathProperty(){
        return projectPathProperty;
    }
}
