package com.example.demo.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class Patient {
    private final StringProperty name;
    private final ObjectProperty<LocalDate> dob;
    private final StringProperty contact;
    private final StringProperty status;

    public Patient(String name, LocalDate dob, String contact, String status) {
        this.name = new SimpleStringProperty(name);
        this.dob = new SimpleObjectProperty<>(dob);
        this.contact = new SimpleStringProperty(contact);
        this.status = new SimpleStringProperty(status);
    }

    // Getters
    public String getName() { return name.get(); }
    public LocalDate getDob() { return dob.get(); }
    public String getContact() { return contact.get(); }
    public String getStatus() { return status.get(); }

    // Property Getters
    public StringProperty nameProperty() { return name; }
    public ObjectProperty<LocalDate> dobProperty() { return dob; }
    public StringProperty contactProperty() { return contact; }
    public StringProperty statusProperty() { return status; }
}