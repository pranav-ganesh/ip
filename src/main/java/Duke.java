import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from Duke!");
        System.out.println("");
        User user1 = new User();
        Storage storage = new Storage("./duke.txt");
        storage.loadTaskListData();
        System.out.println("");
        System.out.println("Hope you are doing well. How can I help you?");
        TaskList taskList = new TaskList();
        while (true) {
            String command = user1.command();
            if (command.equals("")) {
                break;
            }
            if (command.equals("bye")) {
                System.out.println("Bye. Have a great day!");
                break;
            } else if (command.equals("list")) {
                taskList.getList();
            } else if (command.startsWith("done") && Character.isDigit(command.charAt(command.length() - 1))
                    && command.length() <= 8 && !Character.isAlphabetic(command.charAt(command.length() - 2))
                    && Character.isDigit(command.charAt(5))) {
                int value = Integer.parseInt(command.replaceAll("[^0-9]", ""));
                if (value > taskList.size()) {
                    System.out.println("Sorry the task doesn't exist yet, please try again!");
                } else {
                    Task t = taskList.getTask(value - 1);
                    t.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(t.toString());
                    storage.writeToFile("./duke.txt", taskList);
                }
            } else {
                if (command.startsWith("todo")) {
                    if (command.length() <= 5) {
                        displayError("todo");
                    } else {
                        Task task = new Todo(command.substring(5));
                        taskList.addTask(task);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(task.toString());
                        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        storage.writeToFile("./duke.txt", taskList);
                    }
                } else if (command.startsWith("deadline")) {
                    if (command.length() <= 9) {
                        displayError("deadline");
                    } else {
                        String[] parts = command.split("/",2);
                        try {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                            LocalDateTime dateTime = LocalDateTime.parse(parts[1].substring(3).trim(), dtf);
                            Task task = new Deadline(parts[0].substring(9), dateTime);
                            taskList.addTask(task);
                            System.out.println("Got it. I've added this task:");
                            System.out.println(task.toString());
                            System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        } catch (DateTimeParseException e) {
                            DukeException exp = new InvalidDateTimeException("The format of your command is incorrect! It should be deadline/by " + 
                                    "<yyyy-mm-dd HHmm>");
                            System.out.println(exp);
                        }
                    }
                } else if (command.startsWith("event")) {
                    if (command.length() <= 6) {
                        displayError("event");
                    } else {
                        String[] parts = command.split("/");
                        try {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                            LocalDateTime dateTime = LocalDateTime.parse(parts[1].substring(3).trim(), dtf);
                            Task task = new Event(parts[0].substring(6), dateTime);
                            taskList.addTask(task);
                            System.out.println("Got it. I've added this task:");
                            System.out.println(task.toString());
                            System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        } catch (DateTimeParseException e) {
                            DukeException exp = new InvalidDateTimeException("The format of your command is incorrect! It should be event/at " +
                                    "<yyyy-mm-dd HHmm>");
                            System.out.println(exp);
                        }
                    }
                } else if (command.startsWith("delete")) {
                    int value = Integer.parseInt(command.replaceAll("[^0-9]", ""));
                    Task task = taskList.getTask(value-1);
                    taskList.removeTask(value-1);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(task);
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                    storage.writeToFile("./duke.txt", taskList);
                } else {
                    DukeException exp = new InvalidCommandException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    System.out.println(exp);
                }
            }
        }
    }
    
    private static void displayError(String str) {
        DukeException exp = new EmptyDescriptionException("OOPS!!! The description of a " + str + " cannot be empty.");
        System.out.println(exp);
    }

    
}