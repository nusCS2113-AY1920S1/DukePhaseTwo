package duke;

/**
 * Custom exception class for all duke.Duke-related exceptions.
 */
public class DukeException extends Exception {

    private String input;
    private String type = "other";

    /**
     * Constructor for duke.DukeException for default type.
     *
     * @param input input message that triggered the exception
     */
    public DukeException(String input) {
        super(input);
        this.input = input;
    }

    /**
     * Constructor for duke.DukeException for specific Tasks, in order to provide
     * type-specific error messages.
     *
     * @param input input message that triggered the exception
     * @param type the type of the duke.tasks.Task that was attempted to be created which
     *             caused the exception (i.e. duke.tasks.ToDo, duke.tasks.Event, or duke.tasks.Deadline)
     */
    public DukeException(String input, String type) {
        super(input);
        this.input = input;
        this.type = type;
    }

    /**
     * Returns the message associated with the type of duke.DukeException that has occurred.
     *
     * @return the error message associated with the exception
     */
    public String getMessage() {

        String message = "An unknown exception has occurred.";
        String word = input.trim().equals("event") ? "an " : "a ";

        if (input.trim().equals("todo") || input.trim().equals("event")
                || input.trim().equals("deadline") || input.trim().equals("doafter")) {

            message = "OOPS!!! The description of "
                    + word
                    + input.trim()
                    + " task cannot be empty.";
        } else if (!type.equals("other")) {
            switch (type) {
            case "todo": {
                message = "OOPS!!! I'm sorry, but I don't know what that means :-(";
                break;
            }
            case "event": {
                if (!input.contains("/at")) {
                    message = "OOPS!!! duke.tasks.Event is missing a location.";
                }
                break;
            }
            case "deadline": {
                if (!input.contains("/by")) {
                    message = "OOPS!!! duke.tasks.Deadline is missing a deadline.";
                }
                break;
            }
            case "doafter": {
                if (!input.contains("/after")) {
                    message = "OOPS!!! duke.tasks.DoAfter is missing a task it is supposed to be done after.";
                } else {
                    message = "Please enter the task number of the task that the DoAfter should be after";
                }
                break;
            }
            case "io": {
                message = "OOPS!!! An IO exception has occurred.";
                break;
            }
            case "empty": {
                message = "List is empty! Please enter a valid command.";
                break;
            }
            case "conflict": {
                message = "There is a conflict between this event and another event!";
                break;
            }
            case "index": {
                message = "Invalid index! Please try again.";
                break;
            }
            case "between": {
                message = "Invalid input for a between task. "
                        + "Please follow this format: between <task_description> /between <start> and <end>";
                break;
            }
            case "recur": {
                message = "Invalid input for a recurring task. Please follow this format:"
                        + " recur <frequency> <description> /on <date> /at <time>\n";
                message += "<frequency> could only be one of: daily, weekly, monthly or yearly\n";
                message += "<date> has to follow the specific format of: dd/mm/yy\n";
                message += "/at <time> is optional.";
                break;
            }
            default: {
                message = "OOPS!!! I'm sorry, but I don't know what that means :-(";
            }
            }
        } else {
            message = "OOPS!!! I'm sorry, but I don't know what that means :-(";
        }
        return Ui.wrap(message);
        // wrap is called from Ui in order to standardize the formatting of the output
    }
}
