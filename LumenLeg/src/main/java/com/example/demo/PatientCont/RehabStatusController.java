package com.example.demo.PatientCont;

import Data.PatientData; // Import PatientData
import com.example.demo.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;

public class RehabStatusController {

    @FXML private ProgressBar progressBar;
    @FXML private Label currentStageLabel;
    @FXML private TextArea notesTextArea;
    @FXML private Button backButton;

    // Field untuk menyimpan data pasien yang sedang login
    private PatientData currentPatient;

    // Metode ini akan dipanggil oleh PatientDashboardController
    public void setPatient(PatientData patient) {
        this.currentPatient = patient;
        updateView(); // Panggil metode untuk mengisi data ke UI
    }

    // Mengisi UI dengan data dari objek currentPatient
    private void updateView() {
        if (currentPatient != null) {
            // Gunakan data dari pasien, berikan nilai default jika null
            progressBar.setProgress(currentPatient.getRehabProgress()); // Contoh: 0.65 -> 65%
            currentStageLabel.setText(currentPatient.getRehabStage() != null ? currentPatient.getRehabStage() : "No stage assigned.");
            notesTextArea.setText(currentPatient.getTherapistNotes() != null ? currentPatient.getTherapistNotes() : "No notes from the therapist yet.");
        }
    }

    // --- Metode navigasi tidak berubah ---
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

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        navigateTo("PatientDashboard.fxml", event);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // ... (kode logout tidak berubah)
    }
}