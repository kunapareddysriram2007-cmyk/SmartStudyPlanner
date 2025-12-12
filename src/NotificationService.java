import java.time.LocalDateTime;

// CO-5: notifier base and implementation
class BaseNotifier {
    public void check(Task t) {
        // default no-op
    }
}

public class NotificationService extends BaseNotifier {
    @Override
    public void check(Task t) {
        LocalDateTime dl = t.getDeadline();
        // If task is completed
        if (t.getProgress() == ProgressStatus.COMPLETED) {
            System.out.println("submitted");
            if (DeadlineUtils.isApproaching(dl)) System.out.println("Deadline is approaching soon");
            if (DeadlineUtils.isApproached(dl)) System.out.println("Deadline is approached");
            return;
        }

        // Not completed
        // If overdue
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if (!now.isBefore(dl)) {
            // overdue
            String overdue = DeadlineUtils.timeLeftString(dl);
            System.out.println(overdue);
            System.out.println("Deadline is approached");
            return;
        }

        // approaching within 24 hours
        if (DeadlineUtils.isApproaching(dl)) {
            System.out.println("Deadline is approaching soon");
        }
    }
}
