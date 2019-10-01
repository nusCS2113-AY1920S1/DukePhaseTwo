package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;
import java.util.Date;

/**
 * Represents a specific {@link Command} used to find a String occurring in the {@link TaskList}.
 */
public class ViewCommand extends Command {

    //private final Date Date = new Date();
    private Date toView;

    public ViewCommand(Date toView) {
        this.toView = toView;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        try {
            for (Task task : taskList.getAllTasks()) {
                if ((task.getCurrentDate()).equals(toView)) {
                    //TODO: needs work on this part. comparing of time use Date always takes into account time 0000
                    sb.append("\t ").append(i++).append(".").append(task.toString());
                    sb.append(System.lineSeparator());
                }
            }
            if (sb.length() == 0) {
                System.out.println("No matching date found! ");
            } else {
                System.out.println("\t Here are the tasks in the requested date:");
            }
            sb.setLength(sb.length() - 1); // to remove the last new line
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
