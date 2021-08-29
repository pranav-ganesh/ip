package duke.command;

import duke.exception.DukeException;
import duke.exception.InvalidCommandException;
import duke.Storage;
import duke.task.TaskList;

public class InvalidCommand extends Command {
    private String command;

    public InvalidCommand(String command) {
        super(command);
        this.command = command;
    }

    public String toString() {
        return "This is an invalid command";
    }

    public void execute(TaskList taskList, Storage storage) {
        DukeException exp = new InvalidCommandException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        System.out.println(exp);
    }
}