package duke.command;

import duke.exceptions.DukeException;
import duke.storage.FileHandling;
import duke.tasks.FixedDuration;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.List;

public class AddFixedDurationCommand extends Command {
    private List<String> splitInput;

    public AddFixedDurationCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage)throws DukeException {
        int i;
        int k = 0;
        String split1 = "";
        String split2 = "";
        if (splitInput.size() == 1) {
            throw new DukeException(" OOPS! the description for a Fixed Duration task cannot be empty");
        }
        for (i = 1; i < splitInput.size(); i++) {
            if (splitInput.get(i).equals("/needs")) {
                k = 1;
            } else if (k == 0) {
                split1 += splitInput.get(i) + " ";
            } else {
                split2 += splitInput.get(i) + " ";
            }
        }
        if (k == 0) {
            throw new DukeException(" Please make sure you have used \"/needs\" to separate"
                    + " task and time");
        } else if (split2.trim().length() == 0) {
            throw new DukeException(" Please enter the time frame");
        }
        tasks.addTask(new FixedDuration(split1.trim(), split2.trim()));
        String taskA = tasks.getTask(tasks.numTasks() - 1).toString();
        ui.printAddTask(tasks.getAllTasks(),taskA);
        storage.saveData(tasks.getAllTasks());
    }
}
