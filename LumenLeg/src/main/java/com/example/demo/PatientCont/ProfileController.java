package com.example.demo.PatientCont;

import Data.PatientData;
import Data.PatientDataManager;
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

public class ProfileController {

    // FXML declarations for all components from the FXML file
    @FXML private TextField fullNameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField emergencyContactField;
    @FXML private TextArea medicalHistoryArea;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private PatientData currentPatient;

    @FXML
    public void initialize() {
        // Fill choices for the Gender ComboBox
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
    }

    /**
     * This method is called to fill the form with existing patient data.
     */
    public void setPatient(PatientData patient) {
        this.currentPatient = patient;
        if (patient != null) {
            fullNameField.setText(patient.getName());
            ageField.setText(String.valueOf(patient.getAge())); // Convert int to String for display
            genderComboBox.setValue(patient.getGender());
            emailField.setText(patient.getEmail());
            phoneField.setText(patient.getPhoneNumber());
            emergencyContactField.setText(patient.getEmergencyContact());
            medicalHistoryArea.setText(patient.getMedicalHistory());

            if (patient.getEmail() != null && !patient.getEmail().trim().isEmpty()) {
                emailField.setEditable(false);
                emailField.setStyle("-fx-background-color: #EEEEEE; -fx-text-fill: #555555;");
            } else {
                emailField.setEditable(true);
                emailField.setStyle("");
            }
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String ageText = ageField.getText();

        // Validate required inputs
        if (fullNameField.getText().trim().isEmpty() || ageText.trim().isEmpty() || genderComboBox.getValue() == null) {
            showAlert("Validation Error", "Full Name, Age, and Gender cannot be empty.");
            return;
        }

        // --- MODIFICATIONS START HERE ---

        int age;
        try {
            // Convert the age text to a number
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            // Show an error if the age is not a valid number
            showAlert("Validation Error", "Age must be a valid number.");
            return;
        }

        // --- MODIFICATIONS END HERE ---

        if (currentPatient == null) {
            currentPatient = new PatientData();
        }

        if (emailField.isEditable()) {
            currentPatient.setEmail(emailField.getText());
        }

        currentPatient.setName(fullNameField.getText());
        currentPatient.setAge(age); // Set age as a number
        currentPatient.setGender(genderComboBox.getValue());
        currentPatient.setPhoneNumber(phoneField.getText());
        currentPatient.setEmergencyContact(emergencyContactField.getText());
        currentPatient.setMedicalHistory(medicalHistoryArea.getText());
        currentPatient.setRehabStatus("Active");

        // Use the manager to save (will add new or update existing)
        PatientDataManager.addOrUpdatePatient(currentPatient);

        showAlert("Success", "Your information has been saved successfully.");
        navigateToDashboard(event, currentPatient);
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        // Return to the dashboard without saving
        navigateToDashboard(event, this.currentPatient);
    }

    private void navigateToDashboard(ActionEvent event, PatientData patient) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/PatientDashboard.fxml"));
            Parent root = loader.load();

            // Get the dashboard controller and send the updated patient data
            PatientDashboardController dashboardController = loader.getController();
            dashboardController.setPatient(patient);
            dashboardController.selectMyInfoTab();

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
}