package command;

import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * The ListCommand class is used when the user intends to view his entire TaskList
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class ListCommand extends Command {
    /**
     * This execute function is used to add the respective tasks to the TaskList and save to persistent storage.
     *
     * @param tasks this string holds command type determinant to decide how to process the user input.
     * @param ui this string holds the description of the task provided by the user.
     * @param storage this parameter provides the execute function the storage to allow the saving of the file.
     */
    public void execute(TaskList tasks, Storage storage) {
        if (tasks.getSize() == 0)
        {
            Ui.printOutput("You have currently no tasks in your list.");
        }
        else
        {
            Ui.printDash();
            Ui.printMessage("Here are the task(s) in your list:");
            int i = 1;
            for (Task task : tasks.getTasks()) {
                Ui.printMessage(i++ + "." + task.toString());
            }
            Ui.printDash();
        }
    }
}
