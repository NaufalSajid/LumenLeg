package com.example.demo.AdminCont;

import com.example.demo.model.ExerciseLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ExerciseLogMonitoringController {

    @FXML
    private ComboBox<String> patientFilterComboBox;
    @FXML
    private TableView<ExerciseLog> logTable;
    @FXML
    private TableColumn<ExerciseLog, String> patientNameColumn;
    @FXML
    private TableColumn<ExerciseLog, String> exerciseTypeColumn;
    @FXML
    private TableColumn<ExerciseLog, LocalDate> dateColumn;
    @FXML
    private TableColumn<ExerciseLog, String> durationColumn;
    @FXML
    private TableColumn<ExerciseLog, String> statusColumn;
    @FXML
    private TableColumn<ExerciseLog, Void> actionsColumn;

    private ObservableList<ExerciseLog> masterLogData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadLogData();
        setupPatientFilter();
    }

    private void setupTableColumns() {
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        exerciseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        formatDateColumn(dateColumn);
        addActionsToTable(actionsColumn);
    }

    private void loadLogData() {
        // Data contoh. Di aplikasi nyata, ini akan dimuat dari file XML.
        masterLogData.addAll(
                new ExerciseLog("Sophia Carter", "Knee Extension", LocalDate.of(2025, 7, 18), "15 mins", "Completed", "No pain reported."),
                new ExerciseLog("Ethan Bennett", "Leg Press", LocalDate.of(2025, 7, 18), "20 mins", "Completed", "Slight discomfort."),
                new ExerciseLog("Sophia Carter", "Hamstring Curl", LocalDate.of(2025, 7, 19), "15 mins", "In Progress", ""),
                new ExerciseLog("Olivia Hayes", "Balance Training", LocalDate.of(2025, 7, 19), "30 mins", "Completed", "Good stability."),
                new ExerciseLog("Ethan Bennett", "Stationary Bike", LocalDate.of(2025, 7, 20), "25 mins", "Completed", "Felt strong.")
        );
        logTable.setItems(masterLogData);

        // Isi ComboBox filter dengan nama pasien yang unik
        ObservableList<String> patientNames = FXCollections.observableArrayList();
        masterLogData.stream()
                .map(ExerciseLog::getPatientName)
                .distinct()
                .forEach(patientNames::add);
        patientFilterComboBox.setItems(patientNames);
    }

    private void setupPatientFilter() {
        FilteredList<ExerciseLog> filteredData = new FilteredList<>(masterLogData, p -> true);

        patientFilterComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(log -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true; // Tidak ada filter, tampilkan semua
                }
                return log.getPatientName().equals(newVal); // Filter berdasarkan nama
            });
        });

        // Tambahkan opsi untuk menampilkan semua pasien kembali
        patientFilterComboBox.getItems().add(0, "All Patients");
        patientFilterComboBox.getSelectionModel().selectFirst();

        logTable.setItems(filteredData);
    }

    private void addActionsToTable(TableColumn<ExerciseLog, Void> column) {
        column.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(editButton, deleteButton);

            {
                pane.setSpacing(10);
                pane.setAlignment(Pos.CENTER);
                editButton.getStyleClass().add("edit-button");
                deleteButton.getStyleClass().add("delete-button");

                editButton.setOnAction(event -> {
                    ExerciseLog log = getTableView().getItems().get(getIndex());
                    handleEditLog(log);
                });

                deleteButton.setOnAction(event -> {
                    ExerciseLog log = getTableView().getItems().get(getIndex());
                    handleDeleteLog(log);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void handleEditLog(ExerciseLog log) {
        System.out.println("Editing log: " + log.getExerciseType() + " for " + log.getPatientName());
        // TODO: Buka dialog/form untuk mengedit log
    }

    private void handleDeleteLog(ExerciseLog log) {
        // Tampilkan dialog konfirmasi sebelum menghapus
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Log");
        alert.setHeaderText("Are you sure you want to delete this log?");
        alert.setContentText(log.getPatientName() + " - " + log.getExerciseType() + " on " + log.getDate());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Deleting log: " + log.getExerciseType());
            masterLogData.remove(log); // Hapus dari list utama
            // TODO: Panggil DataManager untuk menyimpan perubahan ke file XML
        }
    }

    private void formatDateColumn(TableColumn<ExerciseLog, LocalDate> column) {
        column.setCellFactory(col -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });
    }
}
