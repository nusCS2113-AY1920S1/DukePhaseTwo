package duke.command.recipecommands;

import duke.command.CommandRecipe;
import duke.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.task.recipetasks.Recipe;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

public class DeleteRecipeCommand extends CommandRecipe { // need to settle: if no such recipe is found, tell user.

    public DeleteRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeList recipeList, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(12) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            Recipe value = recipeList.deleteRecipe(description);
            if (value == null) {
                arrayList.add(ERROR_MESSAGE_DELETE_RECIPE_NOT_FOUND);
                recipeStorage.saveFile(recipeList);
            } else {
                System.out.println(value.getFeedback());
                arrayList.add(MESSAGE_RECIPE_DELETED + "       " + description + "\n" + "Now you have " + recipeList.getSize() + " recipe(s) in the list.");
                recipeStorage.saveFile(recipeList);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
