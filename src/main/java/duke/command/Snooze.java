package duke.command;

import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.Date;

public class Snooze extends Command {

    private int taskNb;
    private String until;
    private Date date;

    public Snooze(int taskNb, String until) {
        this.taskNb = taskNb;
        this.until = until;
        this.date = Parser.stringToDate(until);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (taskNb < taskList.size() && taskNb >= 0) {
            if (taskList.getTask(taskNb).isDone()) {
                throw new DukeException("Seems like you've already finished that task, no need to snooze it now");
            }
            taskList.changeTaskDate(taskNb, until);
            ui.showChangedDate(Parser.getDateString(date, until),taskList.getTask(taskNb).toString());
            storage.changeContent(taskNb);
        } else {
            throw new DukeException("Enter a valid task number after snooze, between 1 and " + taskList.size());
        }
    }
}

