package oof.command;

import oof.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.task.Task;

import java.util.ArrayList;

/**
 * Represents a Command that finds and displays specific tasks in the TaskList.
 */
public class FindCommand extends Command {

    private String line;

    /**
     * Constructor for FindCommand.
     *
     * @param line Command inputted by user.
     */
    public FindCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Finds the Tasks that contain the keyword(s) specified
     * by the user after processing the Command.
     * Displays the Tasks found after collating them.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws OofException {
        String[] lineSplit = line.split(" ");
        if (!hasDescription(lineSplit)) {
            throw new OofException("OOPS!!! The find command needs a description.");
        }
        String item = lineSplit[1].trim();
        ArrayList<Task> matchedTasks = new ArrayList<>();
        for (int i = 0; i < arr.getSize(); i++) {
            if (arr.getTask(i).getLine().contains(item)) {
                matchedTasks.add(arr.getTask(i));
            }
        }
        ui.printMatchingTasks(matchedTasks);
    }

    /**
     * Checks if input has a description.
     * @param lineSplit processed user input.
     * @return true if description is more than length 0 and is not whitespace.
     */
    private boolean hasDescription(String[] lineSplit) {
        return lineSplit.length != 1;
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
