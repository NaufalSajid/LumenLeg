package Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// Menambahkan properti baru ke dalam urutan XML
@XmlRootElement(name = "patient")
@XmlType(propOrder = {
        "patientId", "name", "age", "gender", "email", "phoneNumber",
        "emergencyContact", "medicalHistory", "rehabStatus",
        "therapistNotes", "painLevel", "rehabStage", "rehabProgress" // Properti baru ditambahkan
})
public class PatientData {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String email;
    private String phoneNumber;
    private String emergencyContact;
    private String medicalHistory;
    private String rehabStatus;

    // --- FIELD BARU ---
    private String therapistNotes;
    private String painLevel;
    private String rehabStage;
    private double rehabProgress;

    public PatientData() {} // Wajib ada untuk JAXB

    // Constructor bisa diupdate jika perlu, namun untuk saat ini kita biarkan
    public PatientData(String name, int age, String gender, String email, String rehabStatus) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.rehabStatus = rehabStatus;
    }

    // --- GETTER DAN SETTER YANG SUDAH ADA ---

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public String getRehabStatus() { return rehabStatus; }
    public void setRehabStatus(String rehabStatus) { this.rehabStatus = rehabStatus; }

    // --- GETTER DAN SETTER UNTUK FIELD BARU ---

    public String getTherapistNotes() { return therapistNotes; }
    public void setTherapistNotes(String therapistNotes) { this.therapistNotes = therapistNotes; }

    public String getPainLevel() { return painLevel; }
    public void setPainLevel(String painLevel) { this.painLevel = painLevel; }

    public String getRehabStage() { return rehabStage; }
    public void setRehabStage(String rehabStage) { this.rehabStage = rehabStage; }

    public double getRehabProgress() { return rehabProgress; }
    public void setRehabProgress(double rehabProgress) { this.rehabProgress = rehabProgress; }
}