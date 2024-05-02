module javafx.application {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires smartgraph;

    exports org.nuberjonas.pompalette.application.javafx_application;
    exports org.nuberjonas.pompalette.application.javafx_application.views;
    opens org.nuberjonas.pompalette.application.javafx_application.views to javafx.fxml;
}