package com.example.demo.PatientCont;

import Data.PatientData;
import Data.UserSession;
import com.example.demo.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import java.io.IOException;

public class PatientDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button logOutButton;
    @FXML private TabPane mainTabPane;

    private PatientData currentPatient;

    @FXML
    public void initialize() {
        this.currentPatient = UserSession.getInstance().getLoggedInPatient();

        if (currentPatient != null && currentPatient.getName() != null) {
            welcomeLabel.setText("Welcome back, " + currentPatient.getName());
        } else {
            welcomeLabel.setText("Welcome back, Patient");
        }
    }

    public void setPatient(PatientData patient) {
        this.currentPatient = patient;
        if (patient != null && patient.getName() != null) {
            welcomeLabel.setText("Welcome back, " + patient.getName());
        }
    }

    @FXML
    private void handleMyProfileClicked(javafx.scene.input.MouseEvent event) {
        this.currentPatient = UserSession.getInstance().getLoggedInPatient();

        if (currentPatient == null) {
            System.out.println("Error: Data pasien tidak tersedia. Silakan login ulang.");
            return;
        }

        try {
            // Pastikan path ini benar
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Profile.fxml"));
            Parent root = loader.load();
            // Anda mungkin perlu import ProfileController jika belum ada
            // import com.example.demo.PatientCont.ProfileController;
            ProfileController profileController = loader.getController();
            profileController.setPatient(this.currentPatient);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metode handleRehabStatusClicked diperbarui sesuai permintaan
    @FXML
    private void handleRehabStatusClicked(javafx.scene.input.MouseEvent event) {
        this.currentPatient = UserSession.getInstance().getLoggedInPatient();
        if (currentPatient == null) {
            System.out.println("Error: Patient data not available.");
            return;
        }

        try {
            // Pastikan path ini benar
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/RehabStatus.fxml"));
            Parent root = loader.load();

            RehabStatusController statusController = loader.getController();
            statusController.setPatient(this.currentPatient);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(String fxmlFile, javafx.scene.input.MouseEvent event) {
        try {
            // Metode ini sekarang hanya digunakan untuk navigasi yang tidak perlu membawa data
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/" + fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExercisePlanClicked(javafx.scene.input.MouseEvent event) {
        navigateTo("ExercisePlan.fxml", event);
    }

    @FXML
    private void handleTrackProgressClicked(javafx.scene.input.MouseEvent event) {
        navigateTo("TrackProgress.fxml", event);
    }

    @FXML
    private void handleMyScheduleClicked(javafx.scene.input.MouseEvent event) {
        navigateTo("Schedule.fxml", event);
    }

    @FXML
    private void handleProposePlanClicked(javafx.scene.input.MouseEvent event) {
        navigateTo("ProposePlan.fxml", event);
    }

    @FXML
    private void handleLogOutClicked(ActionEvent event) {
        UserSession.getInstance().cleanUserSession();

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

    public void selectMyInfoTab() {
        if (mainTabPane != null) {
            mainTabPane.getSelectionModel().select(1);
        }
    }

    public void selectActionTab() {
        if (mainTabPane != null) {
            mainTabPane.getSelectionModel().select(2);
        }
    }
}