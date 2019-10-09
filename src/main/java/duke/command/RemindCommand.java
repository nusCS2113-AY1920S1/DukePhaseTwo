package duke.command;

import duke.dukeexception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class RemindCommand extends Command {
    protected int taskIndex;
    protected int reminder;

    public RemindCommand(int task, int remind) {
        this.taskIndex = task;
        this.reminder = remind;
    }

    /**
     * Executes a command that deletes the task from the task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is deleted successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        Task task = items.getTasks().get(taskIndex);
        task.setReminder(reminder);
        ui.showErrorMsg(String.format("You will get a reminder for this task in %d days", reminder));
        ui.showErrorMsg(" " + task.toString());
    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {

    }
}
