package com.example.demo.PatientCont;

import com.example.demo.Transition;
import com.example.demo.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

public class ScheduleController {

    @FXML private TableView<Appointment> scheduleTable;
    @FXML private TableColumn<Appointment, String> dateColumn;
    @FXML private TableColumn<Appointment, String> timeColumn;
    @FXML private TableColumn<Appointment, String> activityColumn;
    @FXML private TableColumn<Appointment, String> therapistColumn;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        therapistColumn.setCellValueFactory(new PropertyValueFactory<>("therapist"));
        scheduleTable.setItems(getAppointmentData());
    }

    private ObservableList<Appointment> getAppointmentData() {
        return FXCollections.observableArrayList(
                new Appointment("Mon, Jul 15", "9:00 AM", "Physical Therapy", "Dr. Emily Carter"),
                new Appointment("Tue, Jul 16", "10:30 AM", "Occupational Therapy", "Dr. David Lee")
        );
    }

    private void navigateTo(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/" + fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout(ActionEvent event) {
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

    @FXML
    private void handleBackToTrackProgress(ActionEvent event) {
        navigateTo("TrackProgress.fxml", event);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        logout(event);
    }
}