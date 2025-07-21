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
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class AddNoteController {

    @FXML private ComboBox<String> patientNameComboBox;
    @FXML private DatePicker sessionDatePicker;
    @FXML private ComboBox<String> painLevelComboBox;
    @FXML private TextArea observationsTextArea;
    @FXML private TextField rehabStageField;

    // DITAMBAHKAN: Deklarasi FXML untuk Slider
    @FXML private Slider progressSlider;
    @FXML private Label progressLabel; // Label untuk menampilkan persentase

    @FXML
    public void initialize() {
        patientNameComboBox.setItems(
                PatientDataManager.getPatients().stream()
                        .map(PatientData::getName)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
        painLevelComboBox.setItems(FXCollections.observableArrayList(
                "None", "Low", "Medium", "High", "Severe"
        ));
        sessionDatePicker.setValue(LocalDate.now());

        // DITAMBAHKAN: Listener untuk memperbarui label persentase
        if (progressSlider != null && progressLabel != null) {
            progressSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                    progressLabel.setText(String.format("%.0f%%", newVal.doubleValue()))
            );
        }
    }

    @FXML
    private void handleSaveNote(ActionEvent event) {
        String patientName = patientNameComboBox.getValue();
        String pain = painLevelComboBox.getValue();
        String observations = observationsTextArea.getText();
        String stage = rehabStageField.getText();

        // DITAMBAHKAN: Mengambil nilai progress dari slider
        double progress = progressSlider.getValue();

        if (patientName == null || pain == null || observations.isEmpty() || stage.isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        PatientDataManager.getPatients().stream()
                .filter(p -> p.getName().equals(patientName))
                .findFirst()
                .ifPresent(patient -> {
                    patient.setTherapistNotes(observations);
                    patient.setPainLevel(pain);
                    patient.setRehabStage(stage);

                    // DITAMBAHKAN: Menyimpan nilai progress (dibagi 100 agar menjadi 0.0 - 1.0)
                    patient.setRehabProgress(progress / 100.0);
                });

        PatientDataManager.savePatients();
        showAlert("Success", "Note and progress have been saved successfully.");
        navigateToScene("/com/example/demo/AdminDashboard.fxml", event);
    }

    // ... (metode lainnya tetap sama)
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToScene(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        navigateToScene("/com/example/demo/AdminDashboard.fxml", event);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        navigateToScene("/com/example/demo/Login.fxml", event);
    }
}