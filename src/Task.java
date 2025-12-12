import java.io.Serializable;
import java.time.LocalDateTime;

// CO-4: OOP concepts used here (classes, constructors, this keyword)
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private String title;
    private TaskCategory category;
    private int priority;
    private LocalDateTime deadline;
    private ProgressStatus progress;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public Task(String id, String title, TaskCategory category, int priority, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.priority = priority;
        this.deadline = deadline;
        this.progress = ProgressStatus.NOT_STARTED;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public ProgressStatus getProgress() {
        return progress;
    }

    public void setProgress(ProgressStatus progress) {
        this.progress = progress;
        if (progress == ProgressStatus.COMPLETED) {
            this.completedAt = LocalDateTime.now();
        } else {
            this.completedAt = null;
        }
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public String getProgressLabel() {
        switch (this.progress) {
            case IN_PROGRESS: return "In Progress";
            case COMPLETED: return "Submitted";
            default: return "Not Started";
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | priority=%d | %s | status=%s",
                id, title, category.getDisplayName(), priority,
                DeadlineUtils.formatForDisplay(deadline), progress.name());
    }
}

// CO-5: simple enum for progress states
enum ProgressStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}

