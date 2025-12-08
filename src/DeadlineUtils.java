import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

// CO-3: String handling, regex validation, date/time parsing
public class DeadlineUtils {
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
    private static final DateTimeFormatter DISPLAY_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

    public static LocalDate parseDate(String s) throws DateTimeParseException {
        return LocalDate.parse(s, DATE_FMT);
    }

    public static LocalTime parseTime(String s) throws DateTimeParseException {
        // normalize a.m / p.m to AM/PM
        String norm = s.replace("a.m", "AM").replace("p.m", "PM").replace("am", "AM").replace("pm", "PM");
        return LocalTime.parse(norm, TIME_FMT);
    }

    public static LocalDateTime parseAndValidate(String dateStr, String timeStr) throws InvalidDeadlineException {
        try {
            if (!dateStr.matches("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$") || !timeStr.matches("^(0[1-9]|1[0-2]):[0-5][0-9] (a\\.m|p\\.m)$")) {
                throw new InvalidDeadlineException("ENTER A CORRECT DEADLINE DATE");
            }
            LocalDate d = parseDate(dateStr);
            LocalTime t = parseTime(timeStr);
            LocalDateTime dt = LocalDateTime.of(d, t);
            if (dt.isBefore(LocalDateTime.now())) {
                throw new InvalidDeadlineException("ENTER A CORRECT DEADLINE DATE");
            }
            return dt;
        } catch (DateTimeParseException ex) {
            throw new InvalidDeadlineException("ENTER A CORRECT DEADLINE DATE");
        }
    }

    public static String formatForDisplay(LocalDateTime dt) {
        String s = dt.format(DISPLAY_FMT);
        return s.replace("AM", "a.m").replace("PM", "p.m");
    }

    public static String timeLeftString(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        Duration d = Duration.between(now, deadline);
        boolean overdue = d.isNegative();
        if (overdue) d = d.abs();
        long totalMinutes = d.toMinutes();
        long days = totalMinutes / (60 * 24);
        long hours = (totalMinutes % (60 * 24)) / 60;
        long minutes = totalMinutes % 60;
        String fmt = String.format("%02d-%02d-%02d", days, hours, minutes);
        if (overdue) return "Overdue by " + fmt;
        return fmt;
    }

    public static boolean isApproaching(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        Duration d = Duration.between(now, deadline);
        return !d.isNegative() && d.toHours() <= 24;
    }

    public static boolean isApproached(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(deadline);
    }

    // CO-3: recursion example: count days between two dates recursively (inefficient but demonstrative)
    public static long recursiveDaysBetween(LocalDate start, LocalDate end) {
        if (!start.isBefore(end)) return 0;
        return 1 + recursiveDaysBetween(start.plusDays(1), end);
    }

    // CO-3: small bit manipulation example: return 1 if PM else 0
    public static int amPmBit(LocalTime t) {
        return (t.getHour() >= 12) ? 1 : 0;
    }
}
