import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ToDoListApp {
    private static final String FILENAME = "tasks.txt";

    public static void main(String[] args) {
        ToDoList toDoList = new ToDoList();
        try {
            toDoList.loadTasksFromFile(FILENAME);
        } catch (IOException e) {
            System.out.println("Could not load tasks from file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Update Task");
            System.out.println("4. Shift Task");
            System.out.println("5. Toggle Task Status");
            System.out.println("6. List Tasks");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            
            int option = -1;
            try {
                option = scanner.nextInt();
                scanner.nextLine();  // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                scanner.nextLine();  // Consume the invalid input
                continue;
            }

            switch (option) {
                case 1:
                    while (true) {
                        System.out.print("Enter task description (or type 'back' to return to the main menu): ");
                        String description = scanner.nextLine();
                        if (description.equalsIgnoreCase("back")) {
                            break;
                        }
                        System.out.print("Enter due date (yyyy-MM-dd): ");
                        LocalDate dueDate = LocalDate.parse(scanner.nextLine(), formatter);
                        toDoList.addTask(description, dueDate);
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.print("Enter task ID to remove (or type 'back' to return to the main menu): ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("back")) {
                            break;
                        }
                        int removeId = Integer.parseInt(input);
                        toDoList.removeTask(removeId);
                    }
                    break;
                case 3:
                    while (true) {
                        System.out.print("Enter task ID to update (or type 'back' to return to the main menu): ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("back")) {
                            break;
                        }
                        int updateId = Integer.parseInt(input);
                        System.out.print("Enter new task description: ");
                        String newDescription = scanner.nextLine();
                        System.out.print("Enter new due date (yyyy-MM-dd): ");
                        LocalDate newDueDate = LocalDate.parse(scanner.nextLine(), formatter);
                        toDoList.updateTask(updateId, newDescription, newDueDate);
                    }
                    break;
                case 4:
                    while (true) {
                        System.out.print("Enter task ID to shift (or type 'back' to return to the main menu): ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("back")) {
                            break;
                        }
                        int shiftId = Integer.parseInt(input);
                        System.out.print("Enter new due date (yyyy-MM-dd): ");
                        LocalDate shiftDueDate = LocalDate.parse(scanner.nextLine(), formatter);
                        toDoList.shiftTask(shiftId, shiftDueDate);
                    }
                    break;
                case 5:
                    while (true) {
                        System.out.print("Enter task ID to toggle status (or type 'back' to return to the main menu): ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("back")) {
                            break;
                        }
                        int toggleId = Integer.parseInt(input);
                        toDoList.toggleTaskStatus(toggleId);
                    }
                    break;
                case 6:
                    for (Task task : toDoList.getTasks()) {
                        System.out.println(task);
                    }
                    break;
                case 7:
                    try {
                        toDoList.saveTasksToFile(FILENAME);
                    } catch (IOException e) {
                        System.out.println("Could not save tasks to file: " + e.getMessage());
                    }
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
