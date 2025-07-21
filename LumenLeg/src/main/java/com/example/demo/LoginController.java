package com.example.demo;

import Data.PatientData;
import Data.PatientDataManager;
import Data.UserSession; // <-- DITAMBAHKAN
import com.example.demo.PatientCont.PatientDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            showAlert("Login Failed", "Please fill in both email and password.");
            return;
        }

        if (email.equals("admin@lumenleg.com") && password.equals("123")) {
            navigateToDashboard(event, "/com/example/demo/AdminDashboard.fxml", null);
            return;
        }

        Optional<PatientData> patientOpt = PatientDataManager.findPatientByEmail(email);
        if (patientOpt.isPresent()) {
            // DITAMBAHKAN: Menyimpan data pasien ke session global
            UserSession.getInstance().setLoggedInPatient(patientOpt.get());

            navigateToDashboard(event, "/com/example/demo/PatientDashboard.fxml", patientOpt.get());
        } else {
            showAlert("Login Failed", "Patient with this email does not exist.");
        }
    }

    private void navigateToDashboard(ActionEvent event, String fxmlFile, PatientData patientData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            if (patientData != null && fxmlFile.contains("PatientDashboard")) {
                PatientDashboardController dashboardController = loader.getController();
                dashboardController.setPatient(patientData);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.setMaximized(true);
            stage.setTitle("LumenLeg Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleForgotPassword(ActionEvent event) {
        System.out.println("Forgot Password link clicked.");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/SignUp.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) currentScene.getWindow();

            Scene signUpScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());

            stage.setScene(signUpScene);
            stage.setTitle("LumenLeg - Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}