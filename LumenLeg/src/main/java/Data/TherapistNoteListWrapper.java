package Data;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "notes")
public class TherapistNoteListWrapper {

    private List<TherapistNoteData> notes;

    @XmlElement(name = "note")
    public List<TherapistNoteData> getNotes() {
        return notes;
    }

    public void setNotes(List<TherapistNoteData> notes) {
        this.notes = notes;
    }
}
