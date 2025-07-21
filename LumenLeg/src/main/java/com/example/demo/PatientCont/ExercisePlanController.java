package com.example.demo.PatientCont;

import com.example.demo.Data.Exercise;
import Data.ExerciseLibrary; // Added import
import Data.UserSession; // Added import
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExercisePlanController {

    @FXML private ProgressBar dailyProgressBar;
    @FXML private Label progressLabel;
    @FXML private VBox exerciseListVBox;

    private List<Exercise> todayExercises;
    private final Set<String> completedExerciseNames = new HashSet<>();

    @FXML
    public void initialize() {
        todayExercises = ExerciseLibrary.getAllExercises();
        exerciseListVBox.getChildren().clear();

        if (todayExercises != null) {
            for (Exercise exercise : todayExercises) {
                exerciseListVBox.getChildren().add(createExerciseCard(exercise));
            }
        }
        updateProgress();
    }

    private Node createExerciseCard(Exercise exercise) {
        HBox card = new HBox(20);
        card.getStyleClass().add("exercise-card");
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER_LEFT);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);
        imageView.setPreserveRatio(true);

        try {
            String imagePath = exercise.getImageUrl();
            if (imagePath != null && !imagePath.isEmpty()) {
                InputStream imageStream = getClass().getResourceAsStream(imagePath);
                if (imageStream != null) {
                    imageView.setImage(new Image(imageStream));
                } else {
                    System.err.println("Gagal memuat gambar dari resource: " + imagePath);
                }
            }
        } catch (Exception e) {
            System.err.println("Error saat memuat gambar lokal: " + e.getMessage());
            e.printStackTrace();
        }

        VBox detailsBox = new VBox(5);
        Label nameLabel = new Label(exercise.getName());
        nameLabel.getStyleClass().add("exercise-title");
        Label descLabel = new Label(exercise.getDescription());
        descLabel.getStyleClass().add("exercise-description");
        descLabel.setWrapText(true);
        detailsBox.getChildren().addAll(nameLabel, descLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button doneButton = new Button("Mark as Done");
        doneButton.getStyleClass().add("done-button");
        doneButton.setOnAction(e -> handleMarkAsDone(exercise, doneButton));

        card.getChildren().addAll(imageView, detailsBox, spacer, doneButton);
        return card;
    }

    private void handleMarkAsDone(Exercise exercise, Button doneButton) {
        completedExerciseNames.add(exercise.getName());
        doneButton.setText("Completed ✓");
        doneButton.setDisable(true);
        doneButton.getStyleClass().add("completed-button");
        updateProgress();
    }

    private void updateProgress() {
        if (todayExercises == null || todayExercises.isEmpty()) {
            dailyProgressBar.setProgress(1.0);
            progressLabel.setText("100%");
            return;
        }
        double progress = (double) completedExerciseNames.size() / todayExercises.size();
        progressLabel.setText(String.format("%.0f%%", progress * 100));
        dailyProgressBar.setProgress(progress);
    }

    @FXML
    void handleGoToDashboard(ActionEvent event) {
        navigateTo(event, "/com/example/demo/PatientDashboard.fxml");
    }

    @FXML
    void handleGoToHistory(ActionEvent event) {
        navigateTo(event, "/com/example/demo/ExerciseHistory.fxml");
    }

    @FXML
    void handleLogout(ActionEvent event) {
        UserSession.getInstance().cleanUserSession();
        navigateTo(event, "/com/example/demo/Login.fxml");
    }

    private void navigateTo(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}