package org.nuberjonas.pompalette.application.javafx_application.extensions;

import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class DraggablePane extends Pane{
    private double mouseX;
    private double mouseY;
    private double initialTranslateX;
    private double initialTranslateY;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public DraggablePane(double width, double height, Parent parent) {
        setPrefSize(width, height);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setStyle("-fx-border-color: gray");
        setStyle("-fx-background-color: #f5f5f5");
        setEffect(new DropShadow(10, Color.BLACK));

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
            initialTranslateX = getTranslateX();
            initialTranslateY = getTranslateY();
            toFront();
        });

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.MIDDLE) {
                setVisible(false); // Hides the DraggablePane
                event.consume();
            }
        });

        setOnMouseDragged(event -> {
            double newX = initialTranslateX + event.getSceneX() - mouseX;
            double newY = initialTranslateY + event.getSceneY() - mouseY;

            // Calculate movement bounds
            minX = 0;
            minY = 0;
            maxX = ((Pane) getParent()).getWidth() - getWidth();
            maxY = ((Pane) getParent()).getHeight() - getHeight();

            // Restrict movement within bounds
            newX = Math.min(Math.max(minX, newX), maxX);
            newY = Math.min(Math.max(minY, newY), maxY);

            setTranslateX(newX);
            setTranslateY(newY);
        });

        setOnMouseEntered(Event::consume);
        setOnMouseExited(Event::consume);

        parent.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            // Update movement bounds when the parent pane size changes
            maxX = newValue.getWidth() - getWidth();
            maxY = newValue.getHeight() - getHeight();

            // Ensure the draggable pane stays within bounds
            setTranslateX(Math.min(Math.max(minX, getTranslateX()), maxX));
            setTranslateY(Math.min(Math.max(minY, getTranslateY()), maxY));
        });
    }

    public void setInitialPosition(double initialTranslateX, double initialTranslateY){
        setTranslateX(initialTranslateX);
        setTranslateY(initialTranslateY);
    }
}
