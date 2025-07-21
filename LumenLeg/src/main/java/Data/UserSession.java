package Data;

// Kelas ini akan menyimpan data pengguna yang sedang login
public final class UserSession {

    private static UserSession instance;
    private PatientData loggedInPatient;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setLoggedInPatient(PatientData patient) {
        this.loggedInPatient = patient;
    }

    public PatientData getLoggedInPatient() {
        return this.loggedInPatient;
    }

    public void cleanUserSession() {
        loggedInPatient = null;
    }
}