package Data;


import java.time.LocalDateTime;

public class ExerciseLog {
    private String exerciseId;
    private String patientId;
    private LocalDateTime completedAt;
    private int painLevel;
    private String note;

    public ExerciseLog(String exerciseId, String patientId, LocalDateTime completedAt, int painLevel, String note) {
        this.exerciseId = exerciseId;
        this.patientId = patientId;
        this.completedAt = completedAt;
        this.painLevel = painLevel;
        this.note = note;
    }

    // Getters dan setters (bisa generate otomatis)
    public String getExerciseId() { return exerciseId; }
    public String getPatientId() { return patientId; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public int getPainLevel() { return painLevel; }
    public String getNote() { return note; }
}
