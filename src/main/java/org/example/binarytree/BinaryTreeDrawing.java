package org.example.binarytree;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class BinaryTreeDrawing extends Application {
    private static final DecimalFormat df = new DecimalFormat("0.00"); // Angle formatting;
    private double angle = 45.0; // Branch angle
    private int depth = 10; // Tree depth

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(1000, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        VBox slidersBox = new VBox();
        slidersBox.setAlignment(Pos.CENTER);
        slidersBox.setSpacing(10);

        // Label that shows angle
        Label angleLabel = new Label("Angle: " + df.format(angle));

        // Angle choice
        Slider angleSlider = new Slider(0, 90, angle);
        angleSlider.setMaxWidth(800);
        angleSlider.setShowTickLabels(true);
        angleSlider.setShowTickMarks(true);
        angleSlider.setMajorTickUnit(30);
        angleSlider.setMinorTickCount(5);
        angleSlider.setBlockIncrement(5);
        angleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            angle = newValue.doubleValue();
            angleLabel.setText("Angle: " + df.format(angle));
            drawTreeInit(gc);
        });

        // Label that shows depth
        Label depthLabel = new Label("Depth: " + depth);

        // Depth choice
        Slider depthSlider = new Slider(1, 20, depth);
        depthSlider.setMaxWidth(800);
        depthSlider.setShowTickLabels(true);
        depthSlider.setShowTickMarks(true);
        depthSlider.setMajorTickUnit(5);
        depthSlider.setMinorTickCount(1);
        depthSlider.setBlockIncrement(1);
        depthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            depth = newValue.intValue();
            depthLabel.setText("Depth: " + depth);
            drawTreeInit(gc);
        });

        slidersBox.getChildren().addAll(angleLabel, angleSlider, depthLabel, depthSlider);

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setBottom(slidersBox);

        primaryStage.setTitle("Binary Tree Drawing");
        primaryStage.setScene(new Scene(root, 1200, 900));
        primaryStage.setFullScreen(true);
        primaryStage.show();

        drawTreeInit(gc);
    }

    private void drawTreeInit(GraphicsContext gc) {
        gc.clearRect(0, 0, 1000, 800); // Clear canvas before drawing

        double initialLength = 150.0; // Initial branch length
        drawTreeRecursive(gc, depth, 500, 600, -90, initialLength); // Initial position and angle
    }

    private void drawTreeRecursive(GraphicsContext gc, int depth, double x, double y, double angle, double length) {
        if (depth == 0) return;

        double x2 = x + Math.cos(Math.toRadians(angle)) * length;
        double y2 = y + Math.sin(Math.toRadians(angle)) * length;

        gc.strokeLine(x, y, x2, y2);

        double newLength = length * 0.75; // Shortening the length of the next branch
        int newDepth = depth - 1;

        // Recursive drawing of left and right branch
        drawTreeRecursive(gc, newDepth, x2, y2, angle + this.angle, newLength);
        drawTreeRecursive(gc, newDepth, x2, y2, angle - this.angle, newLength);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
