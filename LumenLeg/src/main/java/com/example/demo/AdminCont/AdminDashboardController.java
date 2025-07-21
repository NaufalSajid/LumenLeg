package com.example.demo.AdminCont;

import com.example.demo.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private HBox manageRecordsCard;
    @FXML
    private HBox reviewProposalsCard;
    @FXML
    private HBox addNotesCard;

    @FXML
    private void initialize() {
        manageRecordsCard.setOnMouseClicked(event -> navigateToScene("PatientRecords.fxml", event));
        reviewProposalsCard.setOnMouseClicked(event -> navigateToScene("ReviewProposals.fxml", event));
        addNotesCard.setOnMouseClicked(event -> navigateToScene("AddNote.fxml", event));
    }

    private void navigateToScene(String fxmlFile, MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/" + fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToScene(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/" + fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageRecords(ActionEvent event) {
        navigateToScene("PatientRecords.fxml", event);
    }

    @FXML
    private void handleReviewProposals(ActionEvent event) {
        navigateToScene("ReviewProposal.fxml", event);
    }

    @FXML
    private void handleAddNotes(ActionEvent event) {
        navigateToScene("AddNote.fxml", event);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
            // Resize window to 1280x800 and center it
            javafx.geometry.Rectangle2D bounds = javafx.stage.Screen.getPrimary().getVisualBounds();
            stage.setMaximized(false);
            stage.setWidth(1280);
            stage.setHeight(800);
            stage.setX(bounds.getMinX() + (bounds.getWidth() - 1280) / 2);
            stage.setY(bounds.getMinY() + (bounds.getHeight() - 800) / 2);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
