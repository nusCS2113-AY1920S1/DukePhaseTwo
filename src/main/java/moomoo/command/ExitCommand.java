package moomoo.command;

import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.category.CategoryList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.category.Category;

/**
 * Represents the command to exit the program.
 */
public class ExitCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise.
     *               Value should be true in this class.
     */
    public ExitCommand(boolean isExit) {
        super(isExit, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) {
        ui.showGoodbye();
    }
}