package Data;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProgressLogDataManager {

    private static final String FILE_PATH = "progress_logs.xml";

    public static List<ProgressLogData> loadLogs() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();

            JAXBContext context = JAXBContext.newInstance(ProgressLogListWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ProgressLogListWrapper wrapper = (ProgressLogListWrapper) unmarshaller.unmarshal(file);

            return wrapper.getLogs() != null ? wrapper.getLogs() : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveLogs(List<ProgressLogData> logs) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProgressLogListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ProgressLogListWrapper wrapper = new ProgressLogListWrapper();
            wrapper.setLogs(logs);

            marshaller.marshal(wrapper, new File(FILE_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
