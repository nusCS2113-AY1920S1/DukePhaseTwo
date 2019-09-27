package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.compal.Compal;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Executes user command "list".
 */
public class ListCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs ListCommand object.
     *
     * @param d Compal.
     */
    public ListCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Lists the tasks currently in taskList.
     * It will display the task symbol (T,E,D), the status (done or not done) and the description string.
     *
     * @param userIn Entire user input string.
     */
    @Override
    public void parseCommand(String userIn) {
        Comparator<Task> compareByDateTime = Comparator.comparing(Task::getDate);
        ArrayList<Task> toList = taskList.arrlist;
        Collections.sort(toList, compareByDateTime);
        int count = 1;
        compal.ui.printg("Here are the tasks in your list:");
        for (Task t : toList) {
            compal.ui.printg(count++ + "." + t.toString());
        }
    }
}
