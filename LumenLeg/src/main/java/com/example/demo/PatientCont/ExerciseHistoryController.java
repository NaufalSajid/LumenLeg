package com.example.demo.PatientCont;

import com.example.demo.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

public class ExerciseHistoryController {

    // ✅ CORRECT: Navigates by swapping content, preserving window size.
    private void navigateTo(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/" + fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ CORRECT: Logs out and resets the window to login screen size.
    private void logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);

            stage.setMaximized(false);
            stage.setWidth(1280);
            stage.setHeight(800);
            stage.centerOnScreen();
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleGoToPlan(ActionEvent event) {
        navigateTo("ExercisePlan.fxml", event);
    }

    @FXML
    void handleGoToDashboard(ActionEvent event) {
        navigateTo("PatientDashboard.fxml", event);
    }

    @FXML
    void handleLogout(ActionEvent event) {
        logout(event);
    }
}