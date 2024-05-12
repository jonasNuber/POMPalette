package org.nuberjonas.pompalette.application.javafx_application.extensions;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

public class ProjectGraphZoomAndScrollPane extends ScrollPane {
    public static final double MIN_SCALE = 1.0;
    public static final double MAX_SCALE = 5.0;
    public static final double SCROLL_DELTA = 0.25;
    private final Pane content;
    private final DoubleProperty scaleFactorProperty;
    private final double minScaleFactor;
    private final double maxScaleFactor;
    private final double deltaScaleFactor;
    private ProjectGraphZoomAndScrollPane.PreferredSize contentPreferredSize;

    public ProjectGraphZoomAndScrollPane(Pane content, double maxScaleFactor, double deltaScaleFactor) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null.");
        } else if (maxScaleFactor < 1.0) {
            throw new IllegalArgumentException("Maximum scale factor must be >= 1.");
        } else if (deltaScaleFactor <= 0.0) {
            throw new IllegalArgumentException("Delta scale factor must be > 0.");
        } else {
            this.content = content;
            Group contentGroup = new Group();
            contentGroup.getChildren().add(this.content);
            this.setContent(contentGroup);
            this.minScaleFactor = 1.0;
            this.maxScaleFactor = maxScaleFactor;
            this.deltaScaleFactor = deltaScaleFactor;
            this.scaleFactorProperty = new ReadOnlyDoubleWrapper(this.minScaleFactor);
            this.enableContentResize();
            this.enableZoom();
            this.enablePanning();
            this.enableCentering();
            this.setHvalue(0.5);
            this.setVvalue(0.5);
        }
    }

    public ProjectGraphZoomAndScrollPane(Pane content) {
        this(content, 5.0, 0.25);
    }

    public DoubleProperty scaleFactorProperty() {
        return this.scaleFactorProperty;
    }

    public double getMinScaleFactor() {
        return this.minScaleFactor;
    }

    public double getMaxScaleFactor() {
        return this.maxScaleFactor;
    }

    public double getDeltaScaleFactor() {
        return this.deltaScaleFactor;
    }

    private void enableContentResize() {
        this.contentPreferredSize = new ProjectGraphZoomAndScrollPane.PreferredSize(this.content.getPrefWidth(), this.content.getPrefHeight());
        this.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            if (!this.contentPreferredSize.isWidthSet() && newValue.getWidth() > 0.0) {
                this.content.setPrefWidth(newValue.getWidth());
            }

            if (!this.contentPreferredSize.isHeightSet() && newValue.getHeight() > 0.0) {
                this.content.setPrefHeight(newValue.getHeight());
            }

        });
    }

    private void enablePanning() {
        this.setPannable(true);
    }

    private void enableCentering(){
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.SHORTCUT_DOWN).match(event)) {
                this.setVvalue(0.5);
                this.setHvalue(0.5);
            }
        });
    }

    private void enableZoom() {
        this.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0.0) {
                this.zoomIn(event);
            } else {
                this.zoomOut(event);
            }

            event.consume();
        });
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);

        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (!event.isConsumed() && event.isSecondaryButtonDown()) {
                getParent().fireEvent(event);
                event.consume();
            }
        });

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (!event.isConsumed() && event.isSecondaryButtonDown()) {
                getParent().fireEvent(event);
            }
        });
    }

    private void zoomOut(ScrollEvent event) {
        this.zoomContent(event.getX(), event.getY(), ProjectGraphZoomAndScrollPane.ZoomDirection.OUT);
    }

    private void zoomIn(ScrollEvent event) {
        this.zoomContent(event.getX(), event.getY(), ProjectGraphZoomAndScrollPane.ZoomDirection.IN);
    }

    private void zoomContent(double pivotX, double pivotY, ProjectGraphZoomAndScrollPane.ZoomDirection direction) {
        double previousScale = this.scaleFactorProperty.doubleValue();
        double nextScale = previousScale + direction.getValue() * this.deltaScaleFactor;
        double scaleFactor = nextScale / previousScale;
        double scaleTotal = this.scaleFactorProperty.doubleValue() * scaleFactor;

        if (scaleTotal >= this.minScaleFactor && scaleTotal <= this.maxScaleFactor) {
            this.content.setScaleX(scaleTotal);
            this.content.setScaleY(scaleTotal);

            this.setHvalue(0.5);
            this.setVvalue(0.5);
            this.scaleFactorProperty.set(scaleTotal);
        }
    }

    private static class PreferredSize {
        public double width;
        public double height;

        public PreferredSize(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double getWidth() {
            return this.width;
        }

        public double getHeight() {
            return this.height;
        }

        public boolean isWidthSet() {
            return this.getWidth() != -1.0;
        }

        public boolean isHeightSet() {
            return this.getHeight() != -1.0;
        }
    }

    private enum ZoomDirection {
        IN(1),
        OUT(-1);

        private final int value;

        ZoomDirection(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
