package Data;

import java.io.Serializable;
import java.time.LocalDate;

public class TrackingData implements Serializable {
    private LocalDate date;
    private double rangeOfMotion;
    private double strengthLevel;
    private double swellingLevel;

    public TrackingData() {
        // Required no-arg constructor for serialization
    }

    public TrackingData(LocalDate date, double rangeOfMotion, double strengthLevel, double swellingLevel) {
        this.date = date;
        this.rangeOfMotion = rangeOfMotion;
        this.strengthLevel = strengthLevel;
        this.swellingLevel = swellingLevel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getRangeOfMotion() {
        return rangeOfMotion;
    }

    public void setRangeOfMotion(double rangeOfMotion) {
        this.rangeOfMotion = rangeOfMotion;
    }

    public double getStrengthLevel() {
        return strengthLevel;
    }

    public void setStrengthLevel(double strengthLevel) {
        this.strengthLevel = strengthLevel;
    }

    public double getSwellingLevel() {
        return swellingLevel;
    }

    public void setSwellingLevel(double swellingLevel) {
        this.swellingLevel = swellingLevel;
    }
}
