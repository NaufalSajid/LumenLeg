package com.example.demo.PatientCont;

import com.example.demo.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class TrackProgressController implements Initializable {

    @FXML private Label dateLabel;
    @FXML private Label sessionsCompletedLabel;
    @FXML private Label painLevelLabel;
    @FXML private VBox activitiesContainer;
    @FXML private Button logoutButton;
    @FXML private Button logProgressButton;
    @FXML private Button goToScheduleButton;
    @FXML private ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    }

    // ✅ CORRECT: Navigates by swapping content, preserving window size.
    private void navigateTo(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            // If navigating to PatientDashboard, select My Info tab
            if (fxmlFile.equals("PatientDashboard.fxml")) {
                Object controller = loader.getController();
                if (controller instanceof com.example.demo.PatientCont.PatientDashboardController) {
                    ((com.example.demo.PatientCont.PatientDashboardController) controller).selectMyInfoTab();
                }
            }
            Transition.applyFadeTransition(root, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ CORRECT: Logs out and resets the window to login screen size.
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
    private void handleLogout(ActionEvent event) {
        logout(event);
    }

    @FXML
    private void handleLogProgress(ActionEvent event) {
        navigateTo("PatientDashboard.fxml", event);
    }

    @FXML
    private void handleGoToSchedule(ActionEvent event) {
        navigateTo("Schedule.fxml", event);
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        navigateTo("PatientDashboard.fxml", event);
    }
}