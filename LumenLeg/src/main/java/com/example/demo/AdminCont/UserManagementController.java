package com.example.demo.AdminCont;

import com.example.demo.model.Patient;
import com.example.demo.Transition;
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

public class UserManagementController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> nameColumn;
    @FXML
    private TableColumn<Patient, LocalDate> dobColumn;
    @FXML
    private TableColumn<Patient, String> contactColumn;
    @FXML
    private TableColumn<Patient, String> statusColumn;
    @FXML
    private TableColumn<Patient, Void> actionsColumn;

    private ObservableList<Patient> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadPatientData();
        setupSearchFilter();
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        formatDateColumn(dobColumn);
        styleStatusColumn(statusColumn);
        addActionsToTable(actionsColumn);
    }

    private void loadPatientData() {
        masterData.addAll(
                new Patient("Sophia Carter", LocalDate.of(1988, 5, 12), "(555) 123-4567", "Active"),
                new Patient("Ethan Bennett", LocalDate.of(1992, 8, 24), "(555) 987-6543", "Completed"),
                new Patient("Olivia Hayes", LocalDate.of(1975, 3, 3), "(555) 246-8013", "Active"),
                new Patient("Noah Thompson", LocalDate.of(1980, 11, 15), "(555) 135-7924", "Inactive"),
                new Patient("Ava Mitchell", LocalDate.of(1995, 7, 9), "(555) 369-1215", "Active")
        );
        patientTable.setItems(masterData);
    }

    private void setupSearchFilter() {
        FilteredList<Patient> filteredData = new FilteredList<>(masterData, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return patient.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        patientTable.setItems(filteredData);
    }

    private void addActionsToTable(TableColumn<Patient, Void> column) {
        column.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("View Details");
            {
                viewButton.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    System.out.println("View details for: " + patient.getName());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(viewButton);
                    container.setAlignment(Pos.CENTER);
                    setGraphic(container);
                }
            }
        });
    }

    private void navigateToScene(String fxmlFile, javafx.event.ActionEvent event) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/com/example/demo/" + fxmlFile));
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleStatusColumn(TableColumn<Patient, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
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
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    private void formatDateColumn(TableColumn<Patient, LocalDate> column) {
        column.setCellFactory(col -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });
    }
}
