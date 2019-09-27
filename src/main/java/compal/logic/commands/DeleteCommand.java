package compal.logic.commands;

import compal.compal.Compal;
import compal.logic.parser.CommandParser;
import compal.tasks.TaskList;

import java.util.Scanner;

import static compal.compal.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.compal.Messages.MESSAGE_INVALID_RANGE;

/**
 * Executes user command "delete".
 */
public class DeleteCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs DeleteCommand object.
     *
     * @param d Compal
     */
    public DeleteCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Deletes a task based on user task number input and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user task number input is invalid.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        //Compal.ui.printg(userIn);
        Scanner scanner = new Scanner(userIn);
        String delete = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();

            int toRemove = Integer.parseInt(restOfInput.trim()) - 1;
            int maxLimit = taskList.arrlist.size();

            if (toRemove < 0 || toRemove >= maxLimit) {
                compal.ui.printg(MESSAGE_INVALID_RANGE);
                throw new Compal.DukeException(MESSAGE_INVALID_RANGE);
            }

            String removeDesc = taskList.arrlist.get(toRemove).toString();
            taskList.arrlist.remove(toRemove);
            compal.ui.printg("Noted. I've removed this task:");
            compal.ui.printg(removeDesc);
            compal.storage.saveCompal(taskList.arrlist);
            compal.ui.showSize();
            //Compal.tasklist.deleteTask(userIn);
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
