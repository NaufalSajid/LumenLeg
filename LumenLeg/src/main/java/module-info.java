module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind; // Pastikan ini ada untuk JAXB

    // PENTING: Tambahkan baris ini
    // Ini mengizinkan JavaFX (javafx.base) dan JAXB (java.xml.bind)
    // untuk mengakses kelas-kelas di dalam paket Data Anda.
    opens Data to javafx.base, java.xml.bind;

    // Buka paket controller dan model Anda ke FXML
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;

    opens com.example.demo.AdminCont to javafx.fxml;
    exports com.example.demo.AdminCont;

    opens com.example.demo.PatientCont to javafx.fxml;
    exports com.example.demo.PatientCont;

    opens com.example.demo.model to javafx.fxml;
    exports com.example.demo.model;
}
