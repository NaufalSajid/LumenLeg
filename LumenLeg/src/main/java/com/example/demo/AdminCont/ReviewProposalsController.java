package com.example.demo.AdminCont;

import Data.RehabProposalData;
import Data.RehabProposalDataManager;
import com.example.demo.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
// Hapus import PropertyValueFactory karena sudah tidak digunakan
// import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class ReviewProposalsController {

    @FXML private TableView<RehabProposalData> proposalTable;
    @FXML private TableColumn<RehabProposalData, String> patientNameColumn;
    @FXML private TableColumn<RehabProposalData, LocalDate> startDateColumn;
    @FXML private TableColumn<RehabProposalData, String> statusColumn;
    @FXML private TextArea goalsTextArea;
    @FXML private TextField intensityField;

    @FXML
    public void initialize() {
        // DIPERBAIKI: Menggunakan ekspresi lambda untuk menghindari masalah refleksi modular.
        // Ini adalah cara yang lebih modern dan aman untuk mengikat data.
        patientNameColumn.setCellValueFactory(cellData -> cellData.getValue().patientNameProperty());
        startDateColumn.setCellValueFactory(cellData -> cellData.getValue().preferredStartDateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Muat data langsung dari RehabProposalDataManager
        proposalTable.setItems(RehabProposalDataManager.getProposals());

        proposalTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> showProposalDetails(newSelection));
    }

    private void showProposalDetails(RehabProposalData proposal) {
        if (proposal != null) {
            goalsTextArea.setText(proposal.getPersonalGoals());
            intensityField.setText(proposal.getPreferredIntensity());
        } else {
            goalsTextArea.clear();
            intensityField.clear();
        }
    }

    @FXML
    private void handleApprove(ActionEvent event) {
        updateProposalStatus("Approved");
    }

    @FXML
    private void handleReject(ActionEvent event) {
        updateProposalStatus("Rejected");
    }

    private void updateProposalStatus(String newStatus) {
        RehabProposalData selected = proposalTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus(newStatus);
            // Gunakan manager untuk menyimpan perubahan
            RehabProposalDataManager.updateProposal(selected);
            proposalTable.refresh(); // Perbarui tampilan tabel
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
}
