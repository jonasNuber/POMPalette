package org.nuberjonas.pompalette.application.javafx_application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.nuberjonas.pompalette.application.javafx_application.main.ViewHandler;
import org.nuberjonas.pompalette.application.javafx_application.main.ViewModelFactory;
import org.nuberjonas.pompalette.core.model.ModelFactory;

public class PomPaletteApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        var modelFactory = new ModelFactory();
        var viewModelFactory = new ViewModelFactory(modelFactory);
        var viewHandler = new ViewHandler(stage, viewModelFactory);
        viewHandler.start();
    }
}
