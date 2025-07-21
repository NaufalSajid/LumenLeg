package Data;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(name = "progressLog")
public class ProgressLogData {

    private String patientName;
    private LocalDate date;
    private int painLevel; // 1–10
    private String mobility; // Low, Moderate, High
    private String note;

    public ProgressLogData() {}

    public ProgressLogData(String patientName, LocalDate date, int painLevel, String mobility, String note) {
        this.patientName = patientName;
        this.date = date;
        this.painLevel = painLevel;
        this.mobility = mobility;
        this.note = note;
    }

    @XmlElement
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @XmlElement
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @XmlElement
    public int getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(int painLevel) {
        this.painLevel = painLevel;
    }

    @XmlElement
    public String getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    @XmlElement
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
