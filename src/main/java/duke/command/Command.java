package duke.command;

import duke.exceptions.DukeException;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public abstract class Command {

    /**
     * Abstract method to be implemented into specified command classes.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     * @throws DukeException template to allow specified command methods
     *                       to throw specified exceptions when errors is encountered.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws DukeException;

    /**
     * Checks if the command is the exit command.
     * @return true if the command is the exit command.
     */
    public abstract boolean isExit();

}
