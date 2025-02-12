import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private List<Task> tasks;
    private int nextId;

    public ToDoList() {
        tasks = new ArrayList<>();
        nextId = 1;
    }

    public void addTask(String description, LocalDate dueDate) {
        Task task = new Task(nextId++, description, dueDate);
        tasks.add(task);
    }

    public void removeTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    public void updateTask(int id, String newDescription, LocalDate newDueDate) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(newDescription);
                task.setDueDate(newDueDate);
                break;
            }
        }
    }

    public void shiftTask(int id, LocalDate newDueDate) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDueDate(newDueDate);
                break;
            }
        }
    }

    public void toggleTaskStatus(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDone(!task.isDone());
                break;
            }
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void saveTasksToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.write(task.getId() + "," + task.getDescription() + "," + task.isDone() + "," + task.getDueDate());
                writer.newLine();
            }
        }
    }

    public void loadTasksFromFile(String filename) throws IOException {
        tasks.clear();
        nextId = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String description = parts[1];
                boolean isDone = Boolean.parseBoolean(parts[2]);
                LocalDate dueDate = LocalDate.parse(parts[3]);
                Task task = new Task(id, description, dueDate);
                task.setDone(isDone);
                tasks.add(task);
                nextId = Math.max(nextId, id + 1);
            }
        }
    }
}