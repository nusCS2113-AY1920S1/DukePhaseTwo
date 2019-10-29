package duke.logic.commands;

import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.util.ArrayList;

/**
 * The FindCommand is a public class that extends from the abstract class Command.
 * It encapsulates the String to find in the current MealList
 */
public class FindCommand extends Command {
    private String description;

    /**
     * FindCommand is a constructor that store the string to find.
     * @param description the description of the string to find
     */
    public FindCommand(String description) {
        this.description = description;
    }

    public FindCommand(String description, String date) {
        this(description);
        if (!date.isBlank()) {
            this.currentDate = date;
        }
    }

    public FindCommand(boolean flag, String message) {
        this.isFail = true;
        this.error = message;
    }

    /**
     * Executes the FindCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        ArrayList<Meal> matchingMeals = new ArrayList<>();
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        for (Meal element: currentMeals) {
            String currentTaskString = element.toString();
            if (currentTaskString.contains(description)) {
                matchingMeals.add(element);
            }
        }
        ui.showList(matchingMeals);
        ui.showLine();
    }
}
