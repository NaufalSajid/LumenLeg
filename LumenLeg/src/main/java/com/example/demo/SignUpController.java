package com.example.demo;

import Data.PatientData;
import Data.PatientDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleSignUp(ActionEvent event) {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validasi input
        if (fullName.trim().isEmpty() || email.trim().isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all required fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match.");
            return;
        }

        // Cek apakah email sudah ada
        if (PatientDataManager.findPatientByEmail(email).isPresent()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "An account with this email already exists.");
            return;
        }

        // Buat objek pasien baru hanya dengan email dan nama
        PatientData newPatient = new PatientData();
        newPatient.setName(fullName);
        newPatient.setEmail(email);
        newPatient.setRehabStatus("Pending Profile");

        // Simpan pasien baru ke file XML
        PatientDataManager.getPatients().add(newPatient);
        PatientDataManager.savePatients();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Registration successful! You can now log in.");

        // Kembali ke halaman login
        handleBackToLogin(event);
    }

    /**
     * DIPERBARUI: Metode ini sekarang akan kembali ke halaman Login
     * dengan mempertahankan ukuran jendela yang sama.
     */
    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Login.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) currentScene.getWindow();

            // Buat scene baru dengan ukuran yang sama persis seperti scene sign-up
            Scene loginScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());

            stage.setScene(loginScene);
            stage.show();
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
