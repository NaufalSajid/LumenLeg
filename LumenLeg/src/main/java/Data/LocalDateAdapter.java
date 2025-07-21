package Data;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Kelas ini memberitahu JAXB cara mengubah objek LocalDate menjadi String (untuk disimpan di XML)
 * dan sebaliknya (saat memuat dari XML).
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, formatter);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.format(formatter);
    }
}
