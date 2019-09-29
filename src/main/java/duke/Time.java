package duke;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * duke.Time is a class that handles all time related methods.
 */
public class Time {

    /**
     * Returns a LocalDateTime variable converted from the specified String timeStr that
     * was in the format 'dd/MM/yyyy HHmm'.
     * @param timeStr String to be converted into DateTimeFormatter type.
     * @return A LocalDateTime variable that the computer can understand as time.
     */
    public static LocalDateTime readDateTime(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
        LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
        return time;
    }

    /**
     * Returns a LocalDateTime variable converted from the specified String timeStr that
     * was in the format 'dd/MM/yyyy'.
     * @param timeStr String to be converted into DateTimeFormatter type.
     * @return A LocalDateTime variable that the computer can understand as time.
     */
    public static LocalDate readDate(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate time = LocalDate.parse(timeStr, formatter);
        return time;
    }
}
