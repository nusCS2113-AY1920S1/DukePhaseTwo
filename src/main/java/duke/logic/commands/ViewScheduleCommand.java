package duke.logic.commands;

import duke.logic.commands.results.CommandResultCalender;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.TaskList;
import duke.model.events.Task;
import duke.model.events.TaskWithDates;

import javafx.collections.transformation.SortedList;

import java.time.LocalDateTime;

public class ViewScheduleCommand extends Command {
    private LocalDateTime date;
    private static final String MESSAGE_SHOW_CALENDAR = "Calendar is launching...";

    /**
     * Creates a new ViewScheduleCommand with the given date.
     *
     * @param date The date to find.
     */
    public ViewScheduleCommand(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultCalender execute(Model model) throws DukeException {
        CommandResultCalender commandResult = new CommandResultCalender(MESSAGE_SHOW_CALENDAR);
        commandResult.setTasks(model.getTasks());
        return commandResult;
    }
}
