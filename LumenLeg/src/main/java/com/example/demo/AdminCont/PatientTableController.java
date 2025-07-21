package com.example.demo.AdminCont;

import Data.PatientData;
import Data.PatientDataManager; // <-- PENTING: Import PatientDataManager
import com.example.demo.Transition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PatientTableController {

    @FXML
    private TableView<PatientData> patientTable;
    @FXML
    private TableColumn<PatientData, String> nameColumn;
    @FXML
    private TableColumn<PatientData, String> emailColumn;
    @FXML
    private TableColumn<PatientData, String> statusColumn;

    @FXML
    public void initialize() {
        // Setup kolom tabel
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rehabStatus"));

        // DIPERBAIKI: Muat data langsung dari PatientDataManager
        // Ini akan secara otomatis mendapatkan semua data pasien yang sudah disimpan.
        patientTable.setItems(PatientDataManager.getPatients());
    }

    @FXML
    private void handleAddPatient(ActionEvent event) {
        navigateToScene("/com/example/demo/AdminAddPatient.fxml", event);
    }

    @FXML
    private void handleUpdatePatient(ActionEvent event) {
        PatientData selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Logika untuk membuka form update dan mengirim data pasien yang dipilih
            // (Mirip dengan navigasi, tetapi Anda perlu mengirim 'selectedPatient' ke controller berikutnya)
            System.out.println("Update patient: " + selectedPatient.getName());
        } else {
            showAlert("No Selection", "Please select a patient to update.");
        }
    }

    @FXML
    private void handleDeletePatient(ActionEvent event) {
        PatientData selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Patient: " + selectedPatient.getName());
            alert.setContentText("Are you sure you want to delete this patient record?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // DIPERBAIKI: Gunakan PatientDataManager untuk menghapus data
                PatientDataManager.deletePatient(selectedPatient);
                // Tabel akan diperbarui secara otomatis karena menggunakan ObservableList
            }
        } else {
            showAlert("No Selection", "Please select a patient to delete.");
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        navigateToScene("/com/example/demo/AdminDashboard.fxml", event);
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
