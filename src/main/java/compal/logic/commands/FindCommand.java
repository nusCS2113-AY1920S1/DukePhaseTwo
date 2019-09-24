package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.util.Scanner;

/**
 * Executes user command "find".
 */
public class FindCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs FindCommand object.
     *
     * @param d Duke.
     */
    public FindCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Displays search result of keyword input by user.
     *
     * @param userIn Entire user input string.
     * @throws Duke.DukeException If user input after "find" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Duke.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        if (!scanner.hasNext()) {
            duke.ui.printg("FindError: Find field cannot be empty. Please enter a valid search term.");
            throw new Duke.DukeException("FindError: Find field cannot be empty. Please enter a search time.");
        }
        String searchTerm = scanner.next();

        if (taskList.arrlist.isEmpty()) {
            duke.ui.printg("No task to find.");
        }
        Boolean isEmpty = true;
        for (Task task : taskList.arrlist) {
            if (task.getDescription().contains(searchTerm)) {
                if (isEmpty == true) {
                    duke.ui.printg("Your search result for the keyword " + searchTerm + ": \n");
                }
                duke.ui.printg(task.toString());
                isEmpty = false;
            }
        }

        if (isEmpty) {
            duke.ui.printg("No result found for " + searchTerm);
        }
    }
}
