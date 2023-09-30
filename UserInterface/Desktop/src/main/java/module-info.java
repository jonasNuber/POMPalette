module de.nuberjonas.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens de.nuberjonas.desktop to javafx.fxml;
    exports de.nuberjonas.desktop;
}