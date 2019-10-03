package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract class duke.command.Command which dictates two necessary methods in all duke.command.Command subclasses
 * method execute which will execute whatever the user input requests
 * method isExit which checks whether the user input will result in the program shutting down
 */
public abstract class Command {
	public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException;
	public abstract boolean isExit();
}