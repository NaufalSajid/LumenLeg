package Data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.UUID;

@XmlRootElement(name = "proposal")
public class RehabProposalData {

    // Menggunakan JavaFX Properties agar bisa diikat ke TableView
    private final StringProperty proposalId = new SimpleStringProperty();
    private final StringProperty patientName = new SimpleStringProperty();
    private final StringProperty personalGoals = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> preferredStartDate = new SimpleObjectProperty<>();
    private final StringProperty preferredIntensity = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public RehabProposalData() {
        setProposalId("prop-" + UUID.randomUUID().toString());
    }

    public RehabProposalData(String patientName, String personalGoals, LocalDate preferredStartDate,
                             String preferredIntensity, String status) {
        this(); // Panggil constructor kosong untuk mendapatkan ID
        setPatientName(patientName);
        setPersonalGoals(personalGoals);
        setPreferredStartDate(preferredStartDate);
        setPreferredIntensity(preferredIntensity);
        setStatus(status);
    }

    // --- Property Getters (Digunakan oleh JavaFX) ---
    public StringProperty proposalIdProperty() { return proposalId; }
    public StringProperty patientNameProperty() { return patientName; }
    public StringProperty personalGoalsProperty() { return personalGoals; }
    public ObjectProperty<LocalDate> preferredStartDateProperty() { return preferredStartDate; }
    public StringProperty preferredIntensityProperty() { return preferredIntensity; }
    public StringProperty statusProperty() { return status; }

    // --- Standard Getters/Setters (Digunakan oleh JAXB untuk menyimpan/memuat XML) ---
    @XmlElement
    public String getProposalId() { return proposalId.get(); }
    public void setProposalId(String proposalId) { this.proposalId.set(proposalId); }

    @XmlElement
    public String getPatientName() { return patientName.get(); }
    public void setPatientName(String patientName) { this.patientName.set(patientName); }

    @XmlElement
    public String getPersonalGoals() { return personalGoals.get(); }
    public void setPersonalGoals(String personalGoals) { this.personalGoals.set(personalGoals); }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getPreferredStartDate() { return preferredStartDate.get(); }
    public void setPreferredStartDate(LocalDate preferredStartDate) { this.preferredStartDate.set(preferredStartDate); }

    @XmlElement
    public String getPreferredIntensity() { return preferredIntensity.get(); }
    public void setPreferredIntensity(String preferredIntensity) { this.preferredIntensity.set(preferredIntensity); }

    @XmlElement
    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
}
