package org.nuberjonas.pompalette.application.javafx_application;

import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane mainPane = new Pane();
        mainPane.setPrefSize(600, 400);

        DraggablePane draggableWindow = new DraggablePane(400, 200, mainPane);
        DraggablePane draggablePane = new DraggablePane(300, 200, mainPane);
        draggableWindow.setStyle("-fx-background-color: lightblue;");
        draggablePane.setStyle("-fx-background-color: black;");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            // Close the draggable window
            mainPane.getChildren().remove(draggableWindow);
        });
        closeButton.setLayoutX(draggableWindow.getPrefWidth() - 60);
        closeButton.setLayoutY(10);

        draggableWindow.getChildren().add(closeButton);

        mainPane.getChildren().addAll(draggableWindow, draggablePane);

        Scene scene = new Scene(new StackPane(mainPane), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    class DraggablePane extends Pane {
        private double mouseX;
        private double mouseY;
        private double initialTranslateX;
        private double initialTranslateY;
        private double minX;
        private double minY;
        private double maxX;
        private double maxY;

        private Parent parent;

        public DraggablePane(double width, double height, Parent parent) {
            setPrefSize(width, height);
            setEffect(new DropShadow(10, Color.GRAY));

            setOnMousePressed(event -> {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                initialTranslateX = getTranslateX();
                initialTranslateY = getTranslateY();
                toFront(); // Bring the draggable pane to front
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

            // Prevent the event from propagating to parent if the mouse is within the bounds
            setOnMouseEntered(Event::consume);

            // Prevent the event from propagating to parent if the mouse is within the bounds
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
    }
}
