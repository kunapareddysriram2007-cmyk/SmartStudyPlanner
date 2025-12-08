import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

// CO-6: file handling and serialization
public class FileHandler {
    private final File storageFile;

    public FileHandler() {
        File resources = new File("resources");
        if (!resources.exists()) resources.mkdirs();
        storageFile = new File(resources, "tasks.dat");
    }

    public void saveTasks(List<Task> tasks) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(new ArrayList<>(tasks));
        }
    }

    @SuppressWarnings("unchecked")
    public List<Task> loadTasks() throws Exception {
        if (!storageFile.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            return (List<Task>) ois.readObject();
        }
    }
}
