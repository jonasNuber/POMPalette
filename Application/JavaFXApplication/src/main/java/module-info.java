module javafx.application {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires smartgraph;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.commons.lang3;

    requires org.nuberjonas.pompalette.core.model;
    requires org.nuberjonas.pompalette.infrastructure.eventbus;

    exports org.nuberjonas.pompalette.application.javafx_application;
    opens org.nuberjonas.pompalette.application.javafx_application.loadproject.view to javafx.fxml;
    opens org.nuberjonas.pompalette.application.javafx_application.main.view to javafx.fxml;
    opens org.nuberjonas.pompalette.application.javafx_application.projectgraph.view to javafx.fxml;
}