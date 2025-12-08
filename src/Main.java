import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// CO-1: main application loop, input/output, control flow
public class Main {
    public static void main(String[] args) {
        // Automatic date, day and time capture
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
        String nowStr = now.format(fmt).replace("AM", "a.m").replace("PM", "p.m");
        System.out.println("Current date and time: " + nowStr);

        TaskManager manager = new TaskManager();
        manager.start();
    }
}
