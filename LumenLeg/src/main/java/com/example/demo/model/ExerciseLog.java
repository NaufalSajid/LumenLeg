package com.example.demo.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Kelas model untuk merepresentasikan satu entri log latihan.
 */
public class ExerciseLog {
    private final StringProperty patientName;
    private final StringProperty exerciseType;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty duration;
    private final StringProperty status;
    private final StringProperty notes;

    public ExerciseLog(String patientName, String exerciseType, LocalDate date, String duration, String status, String notes) {
        this.patientName = new SimpleStringProperty(patientName);
        this.exerciseType = new SimpleStringProperty(exerciseType);
        this.date = new SimpleObjectProperty<>(date);
        this.duration = new SimpleStringProperty(duration);
        this.status = new SimpleStringProperty(status);
        this.notes = new SimpleStringProperty(notes);
    }

    // Getter untuk setiap properti
    public String getPatientName() { return patientName.get(); }
    public String getExerciseType() { return exerciseType.get(); }
    public LocalDate getDate() { return date.get(); }
    public String getDuration() { return duration.get(); }
    public String getStatus() { return status.get(); }
    public String getNotes() { return notes.get(); }

    // Setter
    public void setStatus(String status) { this.status.set(status); }

    // Property getter (diperlukan oleh TableView)
    public StringProperty patientNameProperty() { return patientName; }
    public StringProperty exerciseTypeProperty() { return exerciseType; }
    public ObjectProperty<LocalDate> dateProperty() { return date; }
    public StringProperty durationProperty() { return duration; }
    public StringProperty statusProperty() { return status; }
    public StringProperty notesProperty() { return notes; }
}
