module javafx.application {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires smartgraph;
    requires org.kordamp.bootstrapfx.core;

    requires org.nuberjonas.pompalette.core.model;

    exports org.nuberjonas.pompalette.application.javafx_application;
    exports org.nuberjonas.pompalette.application.javafx_application.views.loadProject;
    opens org.nuberjonas.pompalette.application.javafx_application.views.loadProject to javafx.fxml;
    exports org.nuberjonas.pompalette.application.javafx_application.views.main;
    opens org.nuberjonas.pompalette.application.javafx_application.views.main to javafx.fxml;
}