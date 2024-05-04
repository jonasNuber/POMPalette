module javafx.application {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires smartgraph;
    requires atlantafx.base;
    requires org.apache.commons.lang3;

    requires org.nuberjonas.pompalette.core.model;
    requires org.nuberjonas.pompalette.infrastructure.eventbus;

    exports org.nuberjonas.pompalette.application.javafx_application;
    opens org.nuberjonas.pompalette.application.javafx_application.gui.loadproject.view to javafx.fxml;
    opens org.nuberjonas.pompalette.application.javafx_application.gui.main.view to javafx.fxml;
    opens org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.view to javafx.fxml;
}