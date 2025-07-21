package com.example.demo.AdminCont;

import com.example.demo.model.ReviewItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManualReviewController {

    // Tabel dan kolomnya
    @FXML
    private TableView<ReviewItem> reviewTable;
    @FXML
    private TableColumn<ReviewItem, String> patientNameColumn;
    @FXML
    private TableColumn<ReviewItem, String> requestTypeColumn;
    @FXML
    private TableColumn<ReviewItem, LocalDate> dateColumn;
    @FXML
    private TableColumn<ReviewItem, String> statusColumn;

    // Panel detail di sebelah kanan
    @FXML
    private Label patientNameDetailLabel;
    @FXML
    private Label requestTypeDetailLabel;
    @FXML
    private TextArea detailsTextArea;
    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;

    private ObservableList<ReviewItem> reviewItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadReviewItems();
        setupSelectionListener();

        // Nonaktifkan tombol sampai ada item yang dipilih
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    private void setupTableColumns() {
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        requestTypeColumn.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        formatDateColumn(dateColumn);
        // Anda bisa menambahkan style untuk status seperti di halaman sebelumnya
    }

    private void loadReviewItems() {
        // Data contoh
        reviewItems.addAll(
                new ReviewItem("Sophia Carter", "New Plan Proposal", "Proposing a new 3-month plan focusing on strength.", LocalDate.now().minusDays(2), "Pending"),
                new ReviewItem("Ethan Bennett", "Feedback Submitted", "Patient reported high pain level after the last session.", LocalDate.now().minusDays(1), "Pending"),
                new ReviewItem("Olivia Hayes", "New Plan Proposal", "Requesting to switch to aquatic therapy.", LocalDate.now().minusDays(5), "Approved"),
                new ReviewItem("Noah Thompson", "Feedback Submitted", "Patient feels no improvement.", LocalDate.now().minusDays(3), "Rejected")
        );
        reviewTable.setItems(reviewItems);
    }

    private void setupSelectionListener() {
        reviewTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Isi panel detail dengan data dari item yang dipilih
                patientNameDetailLabel.setText(newSelection.getPatientName());
                requestTypeDetailLabel.setText(newSelection.getRequestType());
                detailsTextArea.setText(newSelection.getDetails());

                // Aktifkan atau nonaktifkan tombol berdasarkan status
                if ("Pending".equalsIgnoreCase(newSelection.getStatus())) {
                    approveButton.setDisable(false);
                    rejectButton.setDisable(false);
                } else {
                    approveButton.setDisable(true);
                    rejectButton.setDisable(true);
                }
            } else {
                // Kosongkan panel detail jika tidak ada yang dipilih
                clearDetailsPanel();
            }
        });
    }

    @FXML
    private void handleApprove() {
        ReviewItem selectedItem = reviewTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setStatus("Approved");
            System.out.println("Approved: " + selectedItem.getRequestType() + " for " + selectedItem.getPatientName());
            reviewTable.refresh(); // Perbarui tampilan tabel
            // Nonaktifkan tombol lagi setelah aksi
            approveButton.setDisable(true);
            rejectButton.setDisable(true);
        }
    }

    @FXML
    private void handleReject() {
        ReviewItem selectedItem = reviewTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setStatus("Rejected");
            System.out.println("Rejected: " + selectedItem.getRequestType() + " for " + selectedItem.getPatientName());
            reviewTable.refresh(); // Perbarui tampilan tabel
            // Nonaktifkan tombol lagi setelah aksi
            approveButton.setDisable(true);
            rejectButton.setDisable(true);
        }
    }

    private void clearDetailsPanel() {
        patientNameDetailLabel.setText("");
        requestTypeDetailLabel.setText("");
        detailsTextArea.clear();
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    private void formatDateColumn(TableColumn<ReviewItem, LocalDate> column) {
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
