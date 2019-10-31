package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to find a String occurring in the {@link TaskList}.
 */
public class FindIngredientCommand extends Cmd<Ingredient> {

    private String toFind;

    public FindIngredientCommand(String toFind) {
        this.toFind = toFind;
    }


    @Override
    public void execute(GenericList<Ingredient> ingList, Ui ui, Storage storage) throws DukeException {

    }
}