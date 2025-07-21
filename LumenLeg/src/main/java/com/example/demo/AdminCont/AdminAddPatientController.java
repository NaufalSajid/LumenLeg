package com.example.demo.AdminCont;

import Data.PatientData;
import Data.PatientDataManager;
import com.example.demo.Transition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAddPatientController {

    // FXML declarations for all fields in the form
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField phoneField;
    @FXML private TextField emergencyContactField;
    @FXML private TextArea medicalHistoryArea;

    @FXML
    public void initialize() {
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
    }

    @FXML
    private void handleSavePatient(ActionEvent event) {
        String name = fullNameField.getText();
        String email = emailField.getText();
        String ageText = ageField.getText(); // Get age as text
        String gender = genderComboBox.getValue();

        // Validate that required fields are filled
        if (name.trim().isEmpty() || email.trim().isEmpty() || ageText.trim().isEmpty() || gender == null) {
            showAlert("Validation Error", "Please fill in all required fields (*).");
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

        if (PatientDataManager.findPatientByEmail(email).isPresent()) {
            showAlert("Creation Error", "A patient with this email already exists.");
            return;
        }

        // Get data from the new fields
        String phone = phoneField.getText();
        String emergencyContact = emergencyContactField.getText();
        String medicalHistory = medicalHistoryArea.getText();

        // Create a new PatientData object and fill all its properties
        PatientData newPatient = new PatientData();
        newPatient.setName(name);
        newPatient.setEmail(email);
        newPatient.setAge(age); // Set age as a number
        newPatient.setGender(gender);
        newPatient.setPhoneNumber(phone);
        newPatient.setEmergencyContact(emergencyContact);
        newPatient.setMedicalHistory(medicalHistory);
        newPatient.setRehabStatus("Active"); // Default status for new patients

        // Save data using PatientDataManager
        PatientDataManager.addOrUpdatePatient(newPatient);

        showAlert("Success", "New patient has been added successfully.");

        navigateToRecords(event);
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        navigateToRecords(event);
    }

    private void navigateToRecords(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/PatientRecords.fxml"));
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