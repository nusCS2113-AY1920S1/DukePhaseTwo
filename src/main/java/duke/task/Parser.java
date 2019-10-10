package duke.task;

import duke.command.AddDeadLineCommand;
import duke.command.AddEventCommand;
import duke.command.AddToDoCommand;
import duke.command.Command;
import duke.command.MarkTaskAsDoneCommand;
import duke.command.ListTaskCommand;
import duke.command.FindTaskCommand;
import duke.command.DeleteTaskCommand;
import duke.command.ViewSchedule;
import duke.command.AddFixedDurationCommand;
import duke.command.AddDoAfterCommand;
import duke.command.AddDoWithinPeriodCommand;
import duke.command.SnoozeCommand;
import duke.command.ExitCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Takes in a string and parses it to return a valid command to be ran.
 */
public class Parser {
    /**
     * Takes in input from user and returns a command based on the input given.
     * @param input String given by the user
     * @return The command object corresponding to the user input
     * @throws DukeException Thrown when an invalid input is given
     */
    public static Command parse(String input) throws DukeException {
        if (input.startsWith("todo ")) {
            return new AddToDoCommand(false, input);
        } else if (input.startsWith("event ")) {
            return new AddEventCommand(false, input);
        } else if (input.startsWith("deadline ")) {
            return new AddDeadLineCommand(false, input);
        } else if (input.startsWith("done ")) {
            return new MarkTaskAsDoneCommand(false, input);
        } else if (input.equals("list")) {
            return new ListTaskCommand(false, "");
        }  else if (input.startsWith("find ")) {
            return new FindTaskCommand(false, input);
        } else if (input.startsWith("delete ")) {
            return new DeleteTaskCommand(false, input);
        } else if (input.startsWith("view")) {
            return new ViewSchedule(false, input);
        } else if (input.startsWith("snooze")) {
            return parseSnooze(input);
        } else if (input.startsWith("fixed ")) {
            return new AddFixedDurationCommand(false, input);
        } else if (input.startsWith("dowithin ")) {
            return new AddDoWithinPeriodCommand(false, input);
        } else if (input.startsWith("doafter ")) {
            return new AddDoAfterCommand(false, input);
        } else if (input.equals("bye")) {
            return new ExitCommand(true, "");
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static Command parseSnooze(String input) throws DukeException {
        Scanner scanner = new Scanner(input);
        scanner.next();
        try {
            int taskNumber = scanner.nextInt() - 1;
            String till = scanner.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime tillValue = LocalDateTime.parse(till, formatter);
            return new SnoozeCommand(false, input, taskNumber, tillValue);
        } catch (DateTimeParseException e) {
            throw new DukeException("OOPS!! Please format your date and time in \n this format dd/mm/yyyy hhmm");
        } catch (NoSuchElementException e) {
            throw new DukeException("OOPS!! Please enter command in this format\n"
                    + "snooze <task number> <dd/mm/yyyy hhmm");
        }
    }
}
