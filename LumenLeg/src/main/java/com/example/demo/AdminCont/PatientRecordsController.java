package com.example.demo.AdminCont;

import Data.PatientData;
import Data.PatientDataManager;
import com.example.demo.Transition;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientRecordsController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<PatientData> patientsTable;

    // Deklarasi FXML untuk semua kolom dari FXML
    @FXML private TableColumn<PatientData, String> patientNameColumn;
    @FXML private TableColumn<PatientData, String> ageColumn;
    @FXML private TableColumn<PatientData, String> genderColumn;
    @FXML private TableColumn<PatientData, String> emailColumn;
    @FXML private TableColumn<PatientData, String> phoneColumn;
    @FXML private TableColumn<PatientData, String> statusColumn;
    @FXML private TableColumn<PatientData, Void> actionsColumn;

    // Variabel diganti untuk mendukung pemfilteran
    private ObservableList<PatientData> allPatientsData;
    private FilteredList<PatientData> filteredData;

    @FXML
    public void initialize() {
        setupTableColumns();
        // Urutan metode diganti untuk logika baru
        loadAndFilterData();
        setupSearchListener();
    }

    /**
     * Menghubungkan setiap kolom di tabel dengan properti yang sesuai di kelas PatientData.
     * Tidak ada perubahan di sini.
     */
    private void setupTableColumns() {
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber")); // Pastikan nama properti "phoneNumber" benar
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rehabStatus"));

        styleStatusColumn(statusColumn);
        // Metode addActionsToTable milik Anda dipertahankan
        addActionsToTable(actionsColumn);
    }

    /**
     * Metode baru untuk memuat semua data pasien dan langsung memfilternya
     * untuk menyembunyikan status "Pending Profile".
     */
    private void loadAndFilterData() {
        allPatientsData = PatientDataManager.getPatients();
        filteredData = new FilteredList<>(allPatientsData, p ->
                p.getRehabStatus() != null && !p.getRehabStatus().equalsIgnoreCase("Pending Profile")
        );
        patientsTable.setItems(filteredData);
    }

    /**
     * Metode baru yang menambahkan listener ke search field.
     * Listener ini akan memfilter data berdasarkan nama/email DAN tetap menyembunyikan "Pending Profile".
     */
    private void setupSearchListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                // Kondisi 1: Status tidak boleh "Pending Profile"
                boolean isProfileComplete = patient.getRehabStatus() != null && !patient.getRehabStatus().equalsIgnoreCase("Pending Profile");
                if (!isProfileComplete) {
                    return false;
                }

                // Kondisi 2: Jika search field kosong, tampilkan semua yang lolos kondisi 1
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Kondisi 3: Filter berdasarkan nama atau email
                String lowerCaseFilter = newValue.toLowerCase();
                if (patient.getName() != null && patient.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getEmail() != null && patient.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false; // Tidak cocok dengan kriteria pencarian
            });
        });
    }

    // Metode handleAddPatient milik Anda dipertahankan karena menggunakan navigateToScene
    @FXML
    private void handleAddPatient(ActionEvent event) {
        navigateToScene("/com/example/demo/AdminAddPatient.fxml", event, null);
    }

    // Metode styleStatusColumn milik Anda dipertahankan karena sudah benar
    private void styleStatusColumn(TableColumn<PatientData, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label statusLabel = new Label(item);
                    statusLabel.getStyleClass().add("status-badge");
                    statusLabel.getStyleClass().removeIf(style -> style.startsWith("status-"));
                    if ("Active".equalsIgnoreCase(item)) {
                        statusLabel.getStyleClass().add("status-active");
                    } else if ("Completed".equalsIgnoreCase(item)) {
                        statusLabel.getStyleClass().add("status-completed");
                    } else if ("Inactive".equalsIgnoreCase(item)) {
                        statusLabel.getStyleClass().add("status-inactive");
                    }
                    setGraphic(statusLabel);
                    setText(null);
                }
            }
        });
    }

    // Metode addActionsToTable milik Anda dipertahankan karena lebih fungsional
    private void addActionsToTable(TableColumn<PatientData, Void> column) {
        column.setCellFactory(param -> new TableCell<>() {
            private final HBox pane = new HBox(5);
            private final Hyperlink editLink = createActionLink("Edit");
            private final Hyperlink deleteLink = createActionLink("Delete");

            {
                pane.getChildren().addAll(editLink, new Label("|"), deleteLink);
                pane.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    PatientData patient = getTableView().getItems().get(getIndex());
                    editLink.setOnAction(event -> {
                        // Fixed directory for edit panel
                        navigateToScene("/com/example/demo/AdminEditPanel.fxml", event, patient);
                    });
                    deleteLink.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + patient.getName() + "?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES) {
                                PatientDataManager.deletePatient(patient);
                            }
                        });
                    });
                    setGraphic(pane);
                }
            }
        });
    }

    // Metode createActionLink milik Anda dipertahankan
    private Hyperlink createActionLink(String text) {
        Hyperlink link = new Hyperlink(text);
        link.getStyleClass().add("action-link");
        return link;
    }

    // Metode navigateToScene canggih milik Anda dipertahankan
    private void navigateToScene(String fxmlFile, ActionEvent event, PatientData patient) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Jika ada pasien, teruskan ke controller baru
            // Anda perlu mengganti AdminEditPatientController dengan controller yang benar jika berbeda
            if (patient != null && loader.getController() instanceof AdminEditPatientController) {
                AdminEditPatientController controller = loader.getController();
                controller.setPatientData(patient);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Pastikan Anda memiliki AdminEditPatientController atau ganti dengan yang sesuai
    public interface AdminEditPatientController {
        void setPatientData(PatientData patient);
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/AdminDashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}