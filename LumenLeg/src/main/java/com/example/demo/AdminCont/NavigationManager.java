package com.example.demo.AdminCont;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavigationManager {

    private static final String FXML_PATH_PREFIX = "/com/example/demo/";

    public static void switchScene(ActionEvent event, String fxmlFilename) {
        Node sourceNode = (Node) event.getSource();
        loadAndSwitchScene(sourceNode, fxmlFilename);
    }

    public static void switchSceneFromNode(Node node, String fxmlFilename) {
        loadAndSwitchScene(node, fxmlFilename);
    }

    private static void loadAndSwitchScene(Node node, String fxmlFilename) {
        if (node == null || fxmlFilename == null || fxmlFilename.isEmpty()) {
            return;
        }

        try {
            String fullPath = FXML_PATH_PREFIX + fxmlFilename;

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(NavigationManager.class.getResource(fullPath)));
            Parent root = loader.load();

            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}