package com.example.demo.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class RehabProposal {
    private final StringProperty patientName;
    private final ObjectProperty<LocalDate> startDate;
    private final StringProperty status;
    private final StringProperty personalGoals;
    private final StringProperty preferredIntensity;

    public RehabProposal(String patientName, LocalDate startDate, String status, String personalGoals, String preferredIntensity) {
        this.patientName = new SimpleStringProperty(patientName);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.status = new SimpleStringProperty(status);
        this.personalGoals = new SimpleStringProperty(personalGoals);
        this.preferredIntensity = new SimpleStringProperty(preferredIntensity);
    }

    // Getters and Setters
    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }

    public String getPatientName() { return patientName.get(); }
    public LocalDate getStartDate() { return startDate.get(); }
    public String getPersonalGoals() { return personalGoals.get(); }
    public String getPreferredIntensity() { return preferredIntensity.get(); }

    // Property Getters
    public StringProperty patientNameProperty() { return patientName; }
    public ObjectProperty<LocalDate> startDateProperty() { return startDate; }
    public StringProperty statusProperty() { return status; }
}