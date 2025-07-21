package Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Kelas untuk mengelola ExerciseLog secara global (Singleton Pattern).
 * Diperbarui untuk menyediakan metode yang lebih kaya untuk analisis dan tampilan data.
 */
public class LogManager {

    private static final List<ExerciseLog> exerciseLogs = new ArrayList<>();

    // Blok statis untuk mengisi data dummy saat aplikasi pertama kali berjalan.
    // Data ini membantu untuk testing UI tanpa harus input manual.
    static {
        exerciseLogs.add(new ExerciseLog("ex001", "PATIENT001", LocalDateTime.now().minusDays(2), 3, "Terasa sedikit tegang di awal."));
        exerciseLogs.add(new ExerciseLog("ex002", "PATIENT001", LocalDateTime.now().minusDays(2), 5, ""));
        exerciseLogs.add(new ExerciseLog("ex003", "PATIENT001", LocalDateTime.now().minusDays(1), 2, "Cukup mudah dilakukan."));
        exerciseLogs.add(new ExerciseLog("ex001", "PATIENT001", LocalDateTime.now(), 4, "Hari ini terasa lebih baik."));
    }

    /**
     * Menambahkan log latihan baru ke dalam daftar.
     * @param log ExerciseLog yang akan ditambahkan.
     */
    public static void addLog(ExerciseLog log) {
        if (log != null) {
            exerciseLogs.add(log);
        }
    }

    /**
     * Mengembalikan semua daftar log latihan, diurutkan dari yang terbaru.
     * @return List<ExerciseLog> yang tidak dapat diubah (read-only).
     */
    public static List<ExerciseLog> getLogs() {
        exerciseLogs.sort(Comparator.comparing(ExerciseLog::getCompletedAt).reversed());
        return Collections.unmodifiableList(exerciseLogs);
    }

    /**
     * PEMBARUAN: Mengembalikan log yang sudah dikelompokkan berdasarkan tanggal.
     * Ini sangat berguna untuk halaman riwayat agar tampilan lebih rapi.
     * @return Map dengan kunci tanggal dan nilai berupa daftar log pada tanggal tersebut.
     */
    public static Map<LocalDate, List<ExerciseLog>> getLogsGroupedByDate() {
        return getLogs().stream()
                .collect(Collectors.groupingBy(
                        log -> log.getCompletedAt().toLocalDate(),
                        // Menggunakan LinkedHashMap untuk menjaga urutan tanggal (dari terbaru)
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    /**
     * PEMBARUAN: Menghitung jumlah total latihan yang telah dicatat.
     * @return Jumlah total log.
     */
    public static int getTotalExerciseCount() {
        return exerciseLogs.size();
    }

    /**
     * PEMBARUAN: Menghitung rata-rata tingkat nyeri dari semua log.
     * Jika tidak ada log, mengembalikan 0.
     * @return Rata-rata tingkat nyeri dalam bentuk double.
     */
    public static double getAveragePainLevel() {
        if (exerciseLogs.isEmpty()) {
            return 0.0;
        }
        return exerciseLogs.stream()
                .mapToInt(ExerciseLog::getPainLevel)
                .average()
                .orElse(0.0);
    }
}