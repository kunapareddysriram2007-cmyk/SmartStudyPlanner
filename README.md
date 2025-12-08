# SmartStudyPlanner

This project is a small console-based Java application named SmartStudyPlanner.

Requirements and usage

- Build and run using the provided `build.bat` and `run.bat` scripts on Windows.
- The program uses only core Java, no external libraries or build systems.
- Tasks are saved to `resources/tasks.dat` using Java serialization and are loaded automatically on startup.

Compile and run

Open PowerShell and run:

```powershell
cd C:\Smart-Study-Planner\SmartStudyPlanner
.\build.bat
.\run.bat
```

Files of interest

- `src\Main.java` - program entry
- `src\Task.java` - Task model
- `src\TaskManager.java` - interactive menu and task operations
- `src\DeadlineUtils.java` - parsing and time calculations
- `src\FileHandler.java` - persistence to `resources/tasks.dat`
- `src\Motivation.java` - motivational messages
- `src\NotificationService.java` - notifications

Deadline and time formats

- Date: `DD-MM-YYYY`
- Time: `HH:MM a.m` or `HH:MM p.m` (lowercase with dots)

If a past date/time is entered the program prints exactly:

```
ENTER A CORRECT DEADLINE DATE
```

Motivational message appears after one blank line after adding a task.
# Smart Study Planner

This is a simple Java console application named Smart Study Planner. It provides functionality to create study tasks, revision tasks, miscellaneous tasks, manage deadlines, and receive notifications and motivational messages.

How to compile and run (Windows PowerShell):

```powershell
cd c:\Smart-Study-Planner\SmartStudyPlanner\src
javac *.java -d ../bin
cd ..\bin
java Main
```

Notes:
- Deadline date format: `DD-MM-YYYY`
- Time format: `HH:MM a.m` or `HH:MM p.m` (lower-case with dots)
- If a past date/time is entered the application prints: `ENTER A CORRECT DEADLINE DATE`
