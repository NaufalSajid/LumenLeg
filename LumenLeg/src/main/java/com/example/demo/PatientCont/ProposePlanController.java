package com.example.demo.PatientCont;

import Data.RehabProposalData;
import Data.RehabProposalDataManager;
import com.example.demo.Transition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class ProposePlanController {

    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox<String> intensityComboBox;
    @FXML private TextArea goalsTextArea;

    @FXML
    public void initialize() {
        intensityComboBox.setItems(FXCollections.observableArrayList("Low - 1-2 times a week", "Medium - 3-4 times a week", "High - 5+ times a week"));
    }

    @FXML
    private void handleSubmitProposal(ActionEvent event) {
        String patientName = "Current Patient"; // Ganti dengan nama pasien yang login
        String personalGoals = goalsTextArea.getText();
        LocalDate preferredStartDate = startDatePicker.getValue();
        String preferredIntensity = intensityComboBox.getValue();

        if (personalGoals == null || personalGoals.trim().isEmpty() || preferredStartDate == null || preferredIntensity == null) {
            showAlert("Validation Error", "Please fill all fields.");
            return;
        }

        RehabProposalData proposal = new RehabProposalData(
                patientName, personalGoals, preferredStartDate, preferredIntensity, "Pending"
        );

        // DIPERBAIKI: Gunakan manager untuk menambah dan menyimpan proposal
        RehabProposalDataManager.addProposal(proposal);

        showAlert("Success", "Your proposal has been submitted for review.");
        navigateTo("PatientDashboard.fxml", event);
    }

    // Metode navigasi dan lainnya tetap sama...
    private void navigateTo(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/PatientDashboard.fxml"));
            Parent root = loader.load();
            PatientDashboardController dashboardController = loader.getController();
            dashboardController.selectActionTab();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
