package duke;

import duke.command.Command;
import duke.command.CompleteCommand;
import duke.command.RemoveCommand;
import duke.command.ShowListCommand;
import duke.command.FindStringCommand;
import duke.command.ViewScheduleCommand;
import duke.command.SnoozeCommand;
import duke.command.ErrorCommand;
import duke.command.AddTodoCommand;
import duke.command.AddDeadlineCommand;
import duke.command.AddEventCommand;
import duke.command.AddDoAfterTaskCommand;
import duke.command.AddFixDurationCommand;
import duke.command.AddRecurringTaskCommand;

import duke.task.TaskList;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parser checks the user input and creates a command corresponding to the user input.
 */
public class Parser {

    /**
     * Returns a command corresponding to the user input.
     * <p>
     *     This method checks the first word of the 'inputLine' and returns the case
     *     accordingly.
     * </p>
     * <p>
     *     If the first word is not 'list', 'done', 'remove' or 'find', addToList() will
     *     run instead.
     * </p>
     * <p>
     *     If a number is not provided in a done or remove command, an error will be printed,
     *     and an ErrorCommand will be returned.
     * </p>
     * @param inputLine The entire line input from the user.
     * @return a command corresponding to the user input.
     */
    public static Command handleInput(String inputLine, TaskList tasks) {
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
        case "list":
            return new ShowListCommand();
        case "done":
            try {
                return new CompleteCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(Arrays.asList("Please use the format 'done <number>'!"));
                Ui.printMsg(msg);
                break;
            }
        case "remove":
            try {
                return new RemoveCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                        "Please use the format 'remove <number>'!"
                ));
                Ui.printMsg(msg);
                break;
            }
        case "find":
            return new FindStringCommand(inputLine);
        case "view":
            try {
                return new ViewScheduleCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                        "Please use the format 'view today' or 'view <date>'!"
                ));
                Ui.printMsg(msg);
                break;
            } catch (DateTimeParseException e) {
                Ui.printDateFormatError();
                break;
            }
        case "snooze":
            try {
                String dateTimeArray = inputArray[2] + " " + inputArray[3];
                return new SnoozeCommand(inputArray[1], dateTimeArray);
            } catch (IndexOutOfBoundsException e) {
                Ui.printMsg("Please use the format 'snooze <task number> <new date> <new time>'!");
                break;
            } catch (DateTimeParseException e) {
                Ui.printDateFormatError();
                break;
            }
        default:
            return addToList(command, inputLine);
        }
        return new ErrorCommand();
    }

    /**
     * Returns an add command corresponding to the specified command, otherwise alert the user
     * that the command is invalid.
     * @param command The command to be created,
     * @param inputLine The entire line input from the user.
     * @return Add command corresponding to the specified command.
     */
    public static Command addToList(String command, String inputLine) {

        String taskDescription;
        Command commandToRun = new ErrorCommand();

        try {
            taskDescription = inputLine.substring(command.length() + 1);
            switch (command) {
            case "todo":
                commandToRun = new AddTodoCommand(taskDescription);
                break;
            case "event":
                commandToRun = new AddEventCommand(taskDescription);
                break;
            case "deadline":
                commandToRun = new AddDeadlineCommand(taskDescription);
                break;
            case "duration":
                commandToRun = new AddFixDurationCommand(taskDescription);
                break;
            case "recurring":
                commandToRun = new AddRecurringTaskCommand(taskDescription);
                break;
            case "after":
                commandToRun = new AddDoAfterTaskCommand(taskDescription);
                break;
            default:
                Ui.printInvalidCommandError();
            }
        } catch (IndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Invalid command given!"
            ));
            Ui.printMsg(msg);
        }

        return commandToRun;
    }

    /**
     * This method will exit the entire program.
     */
    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        Ui.printMsg(msg);
        //duke.Storage.save(tasks); // Don't need to save since any previous commands are already saved
    }
}
