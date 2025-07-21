package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "progressLogs")
public class ProgressLogListWrapper {

    private List<ProgressLogData> logs;

    @XmlElement(name = "progressLog")
    public List<ProgressLogData> getLogs() {
        return logs;
    }

    public void setLogs(List<ProgressLogData> logs) {
        this.logs = logs;
    }
}
