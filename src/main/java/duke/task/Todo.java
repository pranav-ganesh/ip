package duke.task;

/**
 * Represents a todo task in the task list.
 */
public class Todo extends Task {
    /**
     * Constructor for the Todo class where the description of task is initialized.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo task stored on duke.txt.
     *
     * @return String representation of the todo task stored on duke.txt.
     */
    public String taskListOnDisk() {
        return "duke.task.Todo |" + super.getStatusIcon() + "| " + description;
    }

    /**
     * Returns the string representation of the todo task stored in the list variable.
     *
     * @return String description of the todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
