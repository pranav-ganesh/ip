package duke.command;

import duke.task.Task;
import duke.task.TaskList;
import duke.task.Event;
import duke.Storage;
import duke.exception.DukeException;
import duke.exception.EmptyDescriptionException;
import duke.exception.InvalidDateTimeException;
import duke.exception.InvalidTaskCommandException;
import duke.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the user command when the user adds an event to the task list.
 */
public class EventCommand extends Command {
    private String command;

    /**
     * Represents a constructor for the EventCommand class where the user command is initialized.
     *
     * @param command Command entered by the user.
     */
    public EventCommand(String command) {
        super(command);
        this.command = command;
    }

    /**
     * Returns the string representation of the class.
     *
     * @return String description of the class.
     */
    public String toString() {
        return "This is an event command";
    }

    /**
     * Executes the response when the user enters an event command and updates the task list and storage 
     * file (duke.txt)
     *
     * @param taskList TaskList that stores the tasks.
     * @param storage Storage that deals with loading tasks from the file and saving tasks in the file. 
     * @return String representation of the new event task as well as the number of tasks in the task list.
     * @throws DukeException If user doesn't provide a description for the command or enters the command in invalid format.
     */
    public String execute(TaskList taskList, Storage storage) throws DukeException {
        if (command.trim().length() <= 5) {
            throw new EmptyDescriptionException();
        } 
        
        String[] parts = command.split("/");
        if (parts.length <= 1) {
            throw new InvalidTaskCommandException();
        }

        if (parts[1].trim().equals("") || !parts[1].startsWith("at ")) {
            throw new InvalidTaskCommandException();
        }

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime dateTime = LocalDateTime.parse(parts[1].substring(3).trim(), dtf);
            
            Task task = new Event(parts[0].substring(6), dateTime);
            taskList.addTask(task);
            assert taskList.getTask(taskList.getSize() - 1).equals(task): "last element in the task list should be " 
                    + "equivalent to the most recently added task";
            
            storage.writeToFile("./duke.txt", taskList);
            
            Ui ui = new Ui(taskList, storage);
            String response = ui.taskResponse(task);
            return response;
            
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException();
        }
    }
}
