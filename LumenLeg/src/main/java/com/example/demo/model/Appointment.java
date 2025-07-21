package com.example.demo.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty activity;
    private final StringProperty therapist;

    public Appointment(String date, String time, String activity, String therapist) {
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.activity = new SimpleStringProperty(activity);
        this.therapist = new SimpleStringProperty(therapist);
    }

    // Getters
    public String getDate() { return date.get(); }
    public String getTime() { return time.get(); }
    public String getActivity() { return activity.get(); }
    public String getTherapist() { return therapist.get(); }

    // Property Getters
    public StringProperty dateProperty() { return date; }
    public StringProperty timeProperty() { return time; }
    public StringProperty activityProperty() { return activity; }
    public StringProperty therapistProperty() { return therapist; }
}