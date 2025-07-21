package com.example.demo.AdminCont;

import Data.PatientData;
import Data.PatientDataManager;
import com.example.demo.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminEditPatientController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> rehabStatusComboBox;

    private PatientData currentPatient;

    @FXML
    public void initialize() {
        // Populate ComboBoxes with options
        genderComboBox.getItems().addAll("Male", "Female");
        rehabStatusComboBox.getItems().addAll("Active", "Completed", "Inactive");
    }

    public void setPatientData(PatientData patient) {
        this.currentPatient = patient;
        // Populate the fields with the patient's data
        nameField.setText(patient.getName());
        ageField.setText(String.valueOf(patient.getAge()));
        genderComboBox.setValue(patient.getGender());
        emailField.setText(patient.getEmail());
        phoneField.setText(patient.getPhoneNumber());
        rehabStatusComboBox.setValue(patient.getRehabStatus());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        // Update the currentPatient object with the new data from the form
        currentPatient.setName(nameField.getText());
        currentPatient.setAge(Integer.parseInt(ageField.getText()));
        currentPatient.setGender(genderComboBox.getValue());
        currentPatient.setEmail(emailField.getText());
        currentPatient.setPhoneNumber(phoneField.getText());
        currentPatient.setRehabStatus(rehabStatusComboBox.getValue());

        // Use the manager to update the patient data persistently
        PatientDataManager.updatePatient(currentPatient);

        // Show confirmation and navigate back
        showAlert(Alert.AlertType.INFORMATION, "Success", "Patient information updated successfully.");
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}