package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Kelas ini berfungsi sebagai "pembungkus" untuk daftar PatientData.
 * JAXB menggunakan kelas ini untuk membuat struktur file XML yang benar.
 */
@XmlRootElement(name = "patients")
public class PatientDataListWrapper {

    private List<PatientData> patients = new java.util.ArrayList<>();

    @XmlElement(name = "patient")
    public List<PatientData> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientData> patients) {
        this.patients = patients;
    }
}
