package Data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(name = "note")
public class TherapistNoteData {

    private String patientName;
    private LocalDate date;
    private String note;

    public TherapistNoteData() {} // Required by JAXB

    public TherapistNoteData(String patientName, LocalDate date, String note) {
        this.patientName = patientName;
        this.date = date;
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
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
