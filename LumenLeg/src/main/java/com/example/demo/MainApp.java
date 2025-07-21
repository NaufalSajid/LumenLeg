package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {
    private static StackPane rootPane;
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        try {
            // Make sure the path to Login.fxml is correct
            URL fxmlLocation = getClass().getResource("Login.fxml");
            if (fxmlLocation == null) {
                System.err.println("Cannot find FXML file. Make sure it's in the correct package.");
                return;
            }
            Parent loginRoot = FXMLLoader.load(fxmlLocation);
            rootPane = new StackPane(loginRoot);
            Scene scene = new Scene(rootPane, 1280, 800); // Initial size
            primaryStage.setTitle("LumenLeg");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility method for smooth panel transitions
    public static void setPanel(String fxmlFile) {
        try {
            // Use absolute path for FXML resource
            URL fxmlLocation = MainApp.class.getResource("/com/example/demo/" + fxmlFile);
            if (fxmlLocation == null) {
                System.err.println("Cannot find FXML file: " + fxmlFile);
                return;
            }
            Parent newPanel = FXMLLoader.load(fxmlLocation);
            // Ensure there's a child to remove before fading out
            if (!rootPane.getChildren().isEmpty()) {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(200), rootPane.getChildren().get(0));
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event -> {
                    rootPane.getChildren().setAll(newPanel);
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newPanel);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                });
                fadeOut.play();
            } else {
                // If rootPane is empty, just add the new panel
                rootPane.getChildren().setAll(newPanel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static StackPane getRootPane() {
        return rootPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}