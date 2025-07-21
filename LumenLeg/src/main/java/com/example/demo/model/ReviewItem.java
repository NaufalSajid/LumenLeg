package com.example.demo.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Kelas model untuk merepresentasikan item yang memerlukan tinjauan manual oleh admin.
 */
public class ReviewItem {
    private final StringProperty patientName;
    private final StringProperty requestType;
    private final StringProperty details;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty status; // "Pending", "Approved", "Rejected"

    public ReviewItem(String patientName, String requestType, String details, LocalDate date, String status) {
        this.patientName = new SimpleStringProperty(patientName);
        this.requestType = new SimpleStringProperty(requestType);
        this.details = new SimpleStringProperty(details);
        this.date = new SimpleObjectProperty<>(date);
        this.status = new SimpleStringProperty(status);
    }

    // Getter
    public String getPatientName() { return patientName.get(); }
    public String getRequestType() { return requestType.get(); }
    public String getDetails() { return details.get(); }
    public LocalDate getDate() { return date.get(); }
    public String getStatus() { return status.get(); }

    // Setter
    public void setStatus(String status) { this.status.set(status); }

    // Property Getter (diperlukan oleh TableView)
    public StringProperty patientNameProperty() { return patientName; }
    public StringProperty requestTypeProperty() { return requestType; }
    public StringProperty detailsProperty() { return details; }
    public ObjectProperty<LocalDate> dateProperty() { return date; }
    public StringProperty statusProperty() { return status; }
}
