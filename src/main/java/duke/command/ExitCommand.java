package duke.command;

import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;

/**
 * Represents a command to exit duke.Duke. The <code>duke.command.ExitCommand</code> class
 * extends from the <code>duke.command.Command</code> class for the user to quit the
 * program
 */
public class ExitCommand extends Command {
    /**
     * Constructs a <code>duke.command.ExitCommand</code> object.
     */
    public ExitCommand() {
        super();
    }
    /**
     * Indicates whether duke.Duke should exist
     * @return A boolean. True if the command tells duke.Duke to exit, false
     *          otherwise.
     */
    @Override
    public boolean isExit() {
        return true;
    }
    /**
     * run the command with the respect duke.core.TaskList, UI, and storage.
     * @param tasks The task list where tasks are saved.
     * @param ui The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        ui.exitInformation();
    }
}