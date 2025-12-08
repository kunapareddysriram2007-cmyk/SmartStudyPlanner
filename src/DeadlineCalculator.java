import java.time.Duration;
import java.time.LocalDateTime;

// CO-3: mathematical logic for deadline difference
public class DeadlineCalculator {
    public static String timeLeftString(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        Duration dur = Duration.between(now, deadline);
        boolean overdue = dur.isNegative();
        if (overdue) dur = dur.abs();
        long totalMinutes = dur.toMinutes();
        long days = totalMinutes / (60 * 24);
        long hours = (totalMinutes % (60 * 24)) / 60;
        long minutes = totalMinutes % 60;
        String formatted = String.format("%02d-%02d-%02d", days, hours, minutes);
        if (overdue) return "Overdue by " + formatted;
        return formatted;
    }

    // returns true if within approaching threshold (48 hours)
    public static boolean isApproaching(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        Duration dur = Duration.between(now, deadline);
        return !dur.isNegative() && dur.toHours() <= 48;
    }

    // returns true if close (24 hours)
    public static boolean isApproached(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        Duration dur = Duration.between(now, deadline);
        return !dur.isNegative() && dur.toHours() <= 24;
    }
}
