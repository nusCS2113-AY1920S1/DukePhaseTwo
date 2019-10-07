package oof;

import oof.command.AddDeadlineCommand;
import oof.command.AddEventCommand;
import oof.command.AddToDoCommand;
import oof.command.CalendarCommand;
import oof.command.Command;
import oof.command.CompleteCommand;
import oof.command.DeleteCommand;
import oof.command.ExitCommand;
import oof.command.FindCommand;
import oof.command.ListCommand;
import oof.command.RecurringCommand;
import oof.command.ScheduleCommand;
import oof.command.SnoozeCommand;
import oof.exception.OofException;

import java.text.ParseException;
import java.util.InputMismatchException;

/**
 * Represents a parser to process the commands inputted by the user.
 */
public class CommandParser {

    private static Ui ui;
    private static final int LENGTH_COMMAND_ONLY = 1;
    private static final int LENGTH_COMMAND_AND_TASK = 2;
    private static final int INDEX_ARGUMENT_COMMAND = 0;
    private static final int INDEX_ARGUMENT_TASK_NUMBER = 1;
    private static final int INDEX_ARGUMENT_COUNT = 2;

    /**
     * Parses the input given by user and calls specific Commands
     * after checking the validity of the input.
     *
     * @param line Command inputted by user.
     * @return Command based on the user input.
     * @throws OofException Catches invalid commands given by user.
     */
    public static Command parse(String line) throws OofException {
        String[] argumentArray = line.split(" ");
        switch (argumentArray[INDEX_ARGUMENT_COMMAND]) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "done":
            if (argumentArray.length == LENGTH_COMMAND_ONLY) {
                throw new OofException("OOPS!!! Please enter a number!");
            }
            try {
                int completeIndex = Integer.parseInt(argumentArray[INDEX_ARGUMENT_TASK_NUMBER]) - 1;
                return new CompleteCommand(completeIndex);
            } catch (NumberFormatException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        case "todo":
            line = line.replaceFirst("todo ", "");
            return new AddToDoCommand(line);
        case "deadline":
            line = line.replaceFirst("deadline ", "");
            return new AddDeadlineCommand(line);
        case "event":
            line = line.replaceFirst("event ", "");
            return new AddEventCommand(line);
        case "delete":
            if (argumentArray.length == LENGTH_COMMAND_ONLY) {
                throw new OofException("OOPS!!! Please enter a number!");
            }
            try {
                int deleteIndex = Integer.parseInt(argumentArray[INDEX_ARGUMENT_TASK_NUMBER]) - 1;
                return new DeleteCommand(deleteIndex);
            } catch (NumberFormatException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        case "find":
            return new FindCommand(line);
        case "snooze":
            if (argumentArray.length == LENGTH_COMMAND_ONLY) {
                throw new OofException("OOPS!!! Please enter a number!");
            }
            try {
                int snoozeIndex = Integer.parseInt(argumentArray[INDEX_ARGUMENT_TASK_NUMBER]) - 1;
                return new SnoozeCommand(snoozeIndex);
            } catch (NumberFormatException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        case "schedule":
            line = line.replaceFirst("schedule ", "");
            return new ScheduleCommand(line);
        case "recurring":
            if (argumentArray.length == LENGTH_COMMAND_ONLY) {
                throw new OofException("OOPS!!! Please enter the task number and number of recurrences!");
            } else if (argumentArray.length ==  LENGTH_COMMAND_AND_TASK) {
                throw new OofException("OOPS!!! Please enter the number of recurrences!");
            }
            try {
                int recurringIndex = Integer.parseInt(argumentArray[INDEX_ARGUMENT_TASK_NUMBER]) - 1;
                int recurringCount = Integer.parseInt(argumentArray[INDEX_ARGUMENT_COUNT]);
                ui = new Ui();
                ui.printRecurringOptions();
                int recurringFrequency = ui.scanInt();
                return new RecurringCommand(recurringIndex, recurringCount, recurringFrequency);
            } catch (NumberFormatException e) {
                throw new OofException("OOPS!!! Please enter valid numbers!");
            } catch (InputMismatchException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        case "calendar":
            return new CalendarCommand(argumentArray);
        default:
            throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
