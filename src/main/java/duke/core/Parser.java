package duke.core;

import duke.command.*;
import duke.task.Deadline;
import duke.task.PeriodTask;
import duke.task.Event;
import duke.task.Todo;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class Parser {
    private static String[] substring;

    /**
     * Parses a Task from a string array.
     *
     * @param ss The string array to be parsed.
     * @return The Command received from user.
     */
    public static Command parse(String ss) throws DukeException {
        ss = ss.trim();
        String[] command = ss.split(" ", 2);

        switch (command[0]) {
            case "list":
                return new ListCommand();
            case "done":
                try {
                    int i = Integer.parseInt(command[1]);
                    return new DoneCommand(i);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "delete":
                try {
                    int x = Integer.parseInt(command[1]);
                    return new DeleteCommand(x);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "todo":
                try {
                    Todo t = new Todo(command[1]);
                    return new AddCommand(t);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "deadline":
                try {
                    String[] temp = command[1].split(" /by ");
                    Deadline deadline = new Deadline(temp[0], temp[1]);
                    return new AddCommand(deadline);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "event":
                try {
                    String[] temp1 = command[1].split(" /at ");
                    Event event = new Event(temp1[0], temp1[1]);
                    return new AddCommand(event);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "period":
                try {
                    String[] strArray = command[1].split(" /from ", 2);
                    String[] strArray2 = strArray[1].split(" /to ",2);
                    PeriodTask periodTask = new PeriodTask(strArray[0], strArray2[0], strArray2[1]);
                    return new AddCommand(periodTask);
                }catch (Exception e) {
                    throw new DukeException("Fail to create a period task. Please enter command in the format of 'period <description> /from dd/MM/yyyy HHmm /to dd/MM/yyyy HHmm'.");

                }
            case "find":
                return new FindCommand(command[1]);
            case "reschedule":
                try {
                    String[] tempCommand = command[1].split(" ", 2);
                    return new RescheduleCommand(Integer.parseInt(tempCommand[0]), tempCommand[1]);
                } catch (Exception e) {
                    throw new DukeException("Fail to reschedule task. Please enter command in the format of 'reschedule <task number> <dd/MM/yyyy HHmm>'.");
                }
            case "bye":
                return new ExitCommand();
            default:
                throw new DukeException("Unrecognized user input!");
        }
    }

}
