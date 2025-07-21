package Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Optional;

public class PatientDataManager {

    private static final String PATIENTS_FILE = "patients.xml";
    private static ObservableList<PatientData> patientDataList;

    static {
        patientDataList = FXCollections.observableArrayList();
        loadPatients();
    }

    public static ObservableList<PatientData> getPatients() {
        return patientDataList;
    }

    public static Optional<PatientData> findPatientByEmail(String email) {
        return patientDataList.stream()
                .filter(p -> p.getEmail() != null && p.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * Adds a new patient or updates an existing one based on email.
     * Best used for initial creation or simple updates.
     */
    public static void addOrUpdatePatient(PatientData patient) {
        Optional<PatientData> existingPatientOpt = findPatientByEmail(patient.getEmail());

        if (existingPatientOpt.isPresent()) {
            // Patient exists, update data
            PatientData existingPatient = existingPatientOpt.get();
            existingPatient.setName(patient.getName());
            existingPatient.setAge(patient.getAge());
            existingPatient.setGender(patient.getGender());
            existingPatient.setPhoneNumber(patient.getPhoneNumber());
            existingPatient.setEmergencyContact(patient.getEmergencyContact());
            existingPatient.setMedicalHistory(patient.getMedicalHistory());
            existingPatient.setRehabStatus(patient.getRehabStatus());
        } else {
            // New patient, assign an ID and add to the list
            int nextId = patientDataList.stream()
                    .mapToInt(PatientData::getPatientId)
                    .max().orElse(0) + 1;
            patient.setPatientId(nextId);
            patientDataList.add(patient);
        }
        savePatients(); // Save changes to the file
    }

    /**
     * Updates an existing patient's data using their unique ID.
     * This is the correct method for the edit form.
     * @param updatedPatient The patient data object with the new information.
     */
    public static void updatePatient(PatientData updatedPatient) {
        // Find the patient by their unique ID
        Optional<PatientData> patientToUpdateOpt = patientDataList.stream()
                .filter(p -> p.getPatientId() == updatedPatient.getPatientId())
                .findFirst();

        if (patientToUpdateOpt.isPresent()) {
            PatientData patientToUpdate = patientToUpdateOpt.get();
            // Update all fields
            patientToUpdate.setName(updatedPatient.getName());
            patientToUpdate.setAge(updatedPatient.getAge());
            patientToUpdate.setGender(updatedPatient.getGender());
            patientToUpdate.setEmail(updatedPatient.getEmail());
            patientToUpdate.setPhoneNumber(updatedPatient.getPhoneNumber());
            patientToUpdate.setRehabStatus(updatedPatient.getRehabStatus());
            // You can add other fields like Medical History here if needed

            savePatients(); // Save the updated list to the file
        } else {
            System.err.println("Error: Could not find patient with ID " + updatedPatient.getPatientId() + " to update.");
        }
    }


    public static void deletePatient(PatientData patient) {
        patientDataList.remove(patient);
        savePatients();
    }

    public static void savePatients() {
        try {
            JAXBContext context = JAXBContext.newInstance(PatientDataListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PatientDataListWrapper wrapper = new PatientDataListWrapper();
            wrapper.setPatients(patientDataList);

            File file = new File(PATIENTS_FILE);
            m.marshal(wrapper, file);
            System.out.println("Patient data successfully saved to: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPatients() {
        try {
            File file = new File(PATIENTS_FILE);
            if (file.exists()) {
                JAXBContext context = JAXBContext.newInstance(PatientDataListWrapper.class);
                Unmarshaller um = context.createUnmarshaller();

                PatientDataListWrapper wrapper = (PatientDataListWrapper) um.unmarshal(file);
                patientDataList.clear();
                if (wrapper.getPatients() != null) {
                    patientDataList.addAll(wrapper.getPatients());
                }
                System.out.println("Patient data successfully loaded from: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}