import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

// CO-4, CO-6: TaskManager handles tasks, uses collections, serialization, streams
public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private final FileHandler fileHandler;

    public TaskManager() {
        fileHandler = new FileHandler();
        try {
            tasks.addAll(fileHandler.loadTasks());
        } catch (Exception e) {
            // ignore load errors
        }
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("--- SMART STUDY PLANNER ---");
            System.out.println("1. Add task");
            System.out.println("2. List tasks");
            System.out.println("3. Edit progress");
            System.out.println("4. Search tasks");
            System.out.println("5. Sort by priority");
            System.out.println("6. Sort by deadline");
            System.out.println("7. Save and exit");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    addTask(sc);
                    break;
                case "2":
                    listTasks();
                    break;
                case "3":
                    editProgress(sc);
                    break;
                case "4":
                    searchTasks(sc);
                    break;
                case "5":
                    sortByPriority();
                    break;
                case "6":
                    sortByDeadline();
                    break;
                case "7":
                    saveAndExit();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
        sc.close();
    }

    private void addTask(Scanner sc) {
        try {
            System.out.println("Choose task category number:");
            TaskCategory[] cats = TaskCategory.values();
            for (int i = 0; i < cats.length; i++) System.out.printf("%d. %s\n", i + 1, cats[i].getDisplayName());
            int c = Integer.parseInt(sc.nextLine().trim());
            TaskCategory cat = TaskCategory.fromChoice(c);

            System.out.println("Enter task title");
            String title = sc.nextLine().trim();

            if (cat == TaskCategory.REVISION) {
                System.out.println("Enter subject for revision");
                String subj = sc.nextLine().trim();
                title = title + " - Revision: " + subj;
            } else if (cat == TaskCategory.MISCELLANEOUS) {
                System.out.println("Enter custom name for miscellaneous task");
                String custom = sc.nextLine().trim();
                title = title + " - " + custom;
            }

            // Present textual priority choices for clarity
            System.out.println("Choose priority:");
            System.out.println("1. Very High");
            System.out.println("2. High");
            System.out.println("3. Medium");
            System.out.println("4. Low");
            System.out.println("5. Very Low");
            int priority = -1;
            while (priority == -1) {
                String pchoice = sc.nextLine().trim();
                try {
                    int sel = Integer.parseInt(pchoice);
                    switch (sel) {
                        case 1: priority = 5; break; // Very High -> 5
                        case 2: priority = 4; break; // High -> 4
                        case 3: priority = 3; break; // Medium -> 3
                        case 4: priority = 2; break; // Low -> 2
                        case 5: priority = 1; break; // Very Low ->1
                        default:
                            System.out.println("Please enter a number between 1 and 5 for priority");
                            continue;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter a number between 1 and 5 for priority");
                }
            }

            LocalDateTime deadline = null;
            while (deadline == null) {
                System.out.println("Enter deadline date in DD-MM-YYYY");
                String date = sc.nextLine().trim();
                System.out.println("Enter deadline time in HH:MM a.m or p.m format");
                String time = sc.nextLine().trim();
                try {
                    deadline = DeadlineUtils.parseAndValidate(date, time);
                } catch (InvalidDeadlineException ide) {
                    System.out.println(ide.getMessage());
                }
            }

            String id = UUID.randomUUID().toString();
            Task t = new Task(id, title, cat, priority, deadline);
            tasks.add(t);
            fileHandler.saveTasks(tasks);

            System.out.println("Time left until deadline in DD-HH-MM format");
            System.out.println(DeadlineUtils.timeLeftString(deadline));

            // notifications
            BaseNotifier notifier = new NotificationService();
            notifier.check(t);

            // one blank line then motivational message
            System.out.println();
            Motivation motivator = new Motivation();
            System.out.println(motivator.getMotivationalMessage());

        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available");
            return;
        }
        // CO-2: use arrays for listing and sorting examples
        Task[] arr = tasks.toArray(new Task[0]);
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%d) %s\n", i + 1, arr[i].toString());
            // show notifications per task
            BaseNotifier notifier = new NotificationService();
            notifier.check(arr[i]);
        }
    }

    private void editProgress(Scanner sc) {
        listTasks();
        System.out.println("Enter task number to edit");
        try {
            int n = Integer.parseInt(sc.nextLine().trim());
            Task[] arr = tasks.toArray(new Task[0]);
            if (n < 1 || n > arr.length) {
                System.out.println("Invalid task number");
                return;
            }
            Task t = arr[n - 1];
            System.out.println("Choose status: 1-Not Started 2-In Progress 3-Completed");
            int s = Integer.parseInt(sc.nextLine().trim());
            switch (s) {
                case 1:
                    t.setProgress(ProgressStatus.NOT_STARTED);
                    break;
                case 2:
                    t.setProgress(ProgressStatus.IN_PROGRESS);
                    break;
                case 3:
                    t.setProgress(ProgressStatus.COMPLETED);
                    break;
                default:
                    System.out.println("Invalid status");
            }
            fileHandler.saveTasks(tasks);
            System.out.println("Progress updated");
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private void searchTasks(Scanner sc) {
        System.out.println("Enter search keyword");
        String q = sc.nextLine().trim();
        List<Task> found = tasks.stream().filter(t -> t.getTitle().toLowerCase().contains(q.toLowerCase())).collect(Collectors.toList());
        if (found.isEmpty()) System.out.println("No tasks found");
        else found.forEach(t -> System.out.println(t.toString()));
    }

    private void sortByPriority() {
        Task[] arr = tasks.toArray(new Task[0]);
        Arrays.sort(arr, Comparator.comparingInt(Task::getPriority).reversed());
        tasks.clear();
        for (Task t : arr) tasks.add(t);
        System.out.println("Tasks sorted by priority");
    }

    private void sortByDeadline() {
        Task[] arr = tasks.toArray(new Task[0]);
        Arrays.sort(arr, Comparator.comparing(Task::getDeadline));
        tasks.clear();
        for (Task t : arr) tasks.add(t);
        System.out.println("Tasks sorted by deadline");
    }

    private void saveAndExit() {
        try {
            fileHandler.saveTasks(tasks);
            System.out.println("Saved tasks. Exiting.");
        } catch (Exception e) {
            System.out.println("Failed to save tasks: " + e.getMessage());
        }
    }

    // CO-2: matrix summary by category rows and priority 1..5 columns
    public int[][] getMatrixSummary() {
        TaskCategory[] cats = TaskCategory.values();
        int[][] matrix = new int[cats.length][5];
        for (Task t : tasks) {
            int ri = t.getCategory().ordinal();
            int pr = Math.max(1, Math.min(5, t.getPriority()));
            matrix[ri][pr - 1]++;
        }
        return matrix;
    }

    // CO-3: recursive count of tasks
    public int recursiveCount() {
        return countRec(tasks, 0);
    }

    private int countRec(List<Task> lst, int idx) {
        if (idx >= lst.size()) return 0;
        return 1 + countRec(lst, idx + 1);
    }

    // CO-4/CO-5: simple factory pattern inside TaskManager to create Task objects
    public static class TaskFactory {
        public static Task create(String id, String title, TaskCategory category, int priority, LocalDateTime deadline) {
            // For this project a single Task type is sufficient; factory allows extension later
            return new Task(id, title, category, priority, deadline);
        }
    }
}
