package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Abstract class from which all Commands are the extended from.
 */
public abstract class Command {
    protected boolean isExit;

    /**
     * Execute command logic.
     * @param tasks task list
     * @param ui user interface
     * @param storage handles read write of text file
     * @throws DukeException if control.Duke specific exception found
     * @throws IOException if IO exception found
     */

    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException, ParseException {
    }

    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Check if date-time input is in valid format.
     * @param dateTime date and time for some tasks
     * @return true is input is valid
     */
    protected static boolean isValidDateTime(String dateTime) {
        SimpleDateFormat dateTimeFormat =  new SimpleDateFormat("d/M/yyyy HHmm");
        try {
            dateTimeFormat.parse(dateTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    protected static boolean isAdd (String dateTime, TaskList tasks) {
        SimpleDateFormat dateTimeFormat =  new SimpleDateFormat("d/M/yyyy HHmm");
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).toString().contains(dateTime)) {
                found = true;
                break;
            }
        }
        return found;
    }
}
