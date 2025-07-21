package Data;

import java.util.ArrayList;
import java.util.List;
import com.example.demo.Data.Exercise;

public class ExerciseLibrary {

    public static List<Exercise> getAllExercises() {
        List<com.example.demo.Data.Exercise> exercises = new ArrayList<>();

        // Pastikan path dan ekstensi file (.jpg, .png, .gif) sudah benar
        exercises.add(new Exercise(
                "ex001",
                "Heel Slides",
                "Berbaring telentang. Geser perlahan satu tumit di sepanjang lantai ke arah bokong, lalu luruskan kembali.",
                "/images/ex001.jpg", // Path ke gambar lokal
                "Low"
        ));

        exercises.add(new Exercise(
                "ex002",
                "Wall Sits",
                "Bersandar di dinding, turunkan tubuh hingga paha sejajar dengan lantai. Tahan posisi.",
                "/images/ex002.jpg", // Path ke gambar lokal
                "Medium"
        ));

        exercises.add(new Exercise(
                "ex003",
                "Standing Hamstring Curls",
                "Berdiri tegak, tekuk satu lutut untuk membawa tumit ke arah bokong. Turunkan perlahan.",
                "/images/ex003.jpg", // Path ke gambar lokal
                "Low"
        ));

        return exercises;
    }

    public static Exercise getExerciseById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        for (Exercise exercise : getAllExercises()) {
            if (id.equalsIgnoreCase(exercise.getId())) {
                return exercise;
            }
        }
        return null;
    }
}