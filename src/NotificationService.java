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
        if (DeadlineUtils.isApproaching(dl)) {
            System.out.println("Deadline is approaching soon");
        }
        if (DeadlineUtils.isApproached(dl)) {
            System.out.println("Deadline is approached");
        }
    }
}
