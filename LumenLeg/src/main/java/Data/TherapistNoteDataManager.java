package Data;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TherapistNoteDataManager {

    private static final String FILE_PATH = "therapist_notes.xml";

    public static List<TherapistNoteData> loadNotes() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();

            JAXBContext context = JAXBContext.newInstance(TherapistNoteListWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            TherapistNoteListWrapper wrapper = (TherapistNoteListWrapper) unmarshaller.unmarshal(file);

            return wrapper.getNotes() != null ? wrapper.getNotes() : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveNotes(List<TherapistNoteData> noteList) {
        try {
            JAXBContext context = JAXBContext.newInstance(TherapistNoteListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            TherapistNoteListWrapper wrapper = new TherapistNoteListWrapper();
            wrapper.setNotes(noteList);

            marshaller.marshal(wrapper, new File(FILE_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
