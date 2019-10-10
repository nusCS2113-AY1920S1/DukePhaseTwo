package duke.command;

import duke.tasks.Task;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.exceptions.DukeEmptyListException;

import java.util.List;
import java.util.Objects;

public class FindCommand extends Command {

    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    private String getKeyword() {
        return keyword;
    }

    /**
     * Takes in TaskList, Ui and Storage objects which then displays
     * the active TaskList which contains the task names the user inputs.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws DukeEmptyListException {
        boolean isEmpty = tasks.getTasks().isEmpty();
        if (isEmpty) {
            throw new DukeEmptyListException();
        }
        List<Task> temp = tasks.find(keyword);
        ui.findMsg(temp);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FindCommand)) {
            return false;
        }
        FindCommand otherCommand = (FindCommand) obj;
        return otherCommand.getKeyword() == otherCommand.getKeyword();
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword);
    }
}
