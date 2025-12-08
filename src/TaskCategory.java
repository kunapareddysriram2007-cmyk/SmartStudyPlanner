// CO-4: enum representing task categories
public enum TaskCategory {
    READING("Reading"),
    WRITING("Writing"),
    RESEARCH("Research"),
    REVISION("Revision"),
    ASSIGNMENT("Assignment"),
    PROJECT("Project"),
    EXAM_PREPARATION("Exam Preparation"),
    HOMEWORK("Homework"),
    MISCELLANEOUS("Miscellaneous");

    private final String displayName;

    TaskCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TaskCategory fromChoice(int choice) {
        TaskCategory[] vals = TaskCategory.values();
        if (choice >= 1 && choice <= vals.length) return vals[choice - 1];
        return MISCELLANEOUS;
    }
}
