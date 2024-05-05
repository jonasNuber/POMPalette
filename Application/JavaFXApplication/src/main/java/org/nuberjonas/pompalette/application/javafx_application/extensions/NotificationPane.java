package org.nuberjonas.pompalette.application.javafx_application.extensions;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class NotificationPane extends StackPane {
    private Label label;
    private Rectangle background;

    public NotificationPane() {
        label = new Label();
        label.setFont(Font.font(20));
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-background-color: transparent;");
        label.setTranslateY(-10);

        background = new Rectangle(300, 60);
        background.setArcWidth(40);
        background.setArcHeight(40);
        background.setFill(Color.TRANSPARENT);
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(2);

        setAlignment(Pos.BOTTOM_CENTER);
        getChildren().addAll(background, label);
        setTranslateY(100); // Initial position (off-screen)
    }

    public void showError(String message) {
        showNotification(message, Color.RED);
    }

    public void showWarning(String message) {
        showNotification(message, Color.rgb(255,153,102));
    }

    public void showInfo(String message) {
        showNotification(message, Color.GREEN);
    }


    private void showNotification(String message, Color color) {
        label.setText(message);
        label.setTextFill(color);
        label.setStyle("-fx-foreground-color: " + toRGBCode(color) + "; -fx-padding: 5px;");
        background.setStroke(color);

        TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.5), this);
        slideIn.setToY(-10);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), this);
        fadeIn.setToValue(1);

        ParallelTransition parallelTransition = new ParallelTransition(slideIn, fadeIn);
        parallelTransition.play();

        // Hide notification after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> hideNotification());
        pause.play();
    }

    private void hideNotification() {
        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.5), this);
        slideOut.setToY(100);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), this);
        fadeOut.setToValue(0);

        ParallelTransition parallelTransition = new ParallelTransition(slideOut, fadeOut);
        parallelTransition.setOnFinished(event -> label.setText("")); // Clear the label text after hiding
        parallelTransition.play();
    }

    // Utility method to convert Color to RGB code
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
