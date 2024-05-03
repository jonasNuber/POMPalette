package org.nuberjonas.pompalette.application.javafx_application.gui.loadproject.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nuberjonas.pompalette.application.javafx_application.events.LoadProjectInitializationEvent;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;

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
