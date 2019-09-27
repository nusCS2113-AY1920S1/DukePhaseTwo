package duke.parsers;
import duke.commands.*;
import duke.exceptions.DukeException;
import duke.tasks.dinner;
import duke.tasks.breakfast;
import duke.tasks.lunch;

/**
 * Parser is a public class that help to parse the command that is inputted from the user
 * And generate the appropriate command with their appropriate arguments
 */
public class Parser {
    /**
     * This is the main function that parse the command inputted by the user
     * @param fullCommand the string the user input in the CLI
     * @return <code>new ExitCommand()</code> if the user input "bye"
     *         <code>new AddCommand(new ToDo())</code> if the user input "todo" followed by the description of the activity
     *         <code>new AddCommand(new Event()</code> if the user input "event" followed by the time the event is held
     *         <code>new ListCommand()</code> if the user input list
     *         <code>new MarkDoneCommand(index)</code> if the user input "done" followed by the index of the task to be marked done
     *         <code>new FindCommand(description)</code> if the user input "find" followed by the string that needs to be added
     *         <code>new DeleteCommand(index) </code> if the sure input "delete" followed by the index of the task to be deleted
     * @throws DukeException either there is no description in "done", "todo", "event", and "deadline" command
     *                       or the command is not recognized
     */
    public static Command parse(String fullCommand) throws DukeException {
        //TODO: Put error for invalid input and what not
        String[] splitCommand = fullCommand.split(" ", 2);
        String command = splitCommand[0];
        String description = "";

        if (splitCommand.length >= 2) {
            description = splitCommand[1];
        }
        if (command.equals("done") || command.equals("breakfast") || command.equals("lunch") || command.equals("dinner")) {
            if (description.trim().length() == 0) {
                throw new DukeException("\u2639 OOPS!!! The description of a " + command + " cannot be empty.");
            }
        }
        String[] splitString;
        int index;
        switch (command) {
            case "bye":
                return new ExitCommand();
            case "breakfast":
                splitString = description.split(" ", 2);
                return new AddCommand(new breakfast(splitString[0], splitString[1]));
            case "lunch":
                splitString = description.split(" ", 2);
                return new AddCommand(new lunch(splitString[0], splitString[1]));
            case "dinner":
                splitString = description.split(" ", 2);
                return new AddCommand(new dinner(splitString[0], splitString[1]));
            case "list":
                return new ListCommand();
            case "done":
                index = Integer.parseInt(description);
                return (new MarkDoneCommand(index));
            case "find":
                return new FindCommand(description);
            case "delete":
                index = Integer.parseInt(description);
                return new DeleteCommand(index);
            default:
                throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
