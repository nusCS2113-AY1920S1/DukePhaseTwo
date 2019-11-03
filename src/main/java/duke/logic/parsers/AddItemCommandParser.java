package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddItemCommand;
import duke.model.meal.Meal;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Parser class to handle addition of Item object to model.
 */
public class AddItemCommandParser implements ParserInterface<AddItemCommand> {

    /**
     * Parses user input and returns an AddItemCommand encapsulating a Item object.
     * @param userInputStr String input by user.
     * @return <code>AddItemCommand</code> Command object encapsulating a breakfast object
     */
    @Override
    public AddItemCommand parse(String userInputStr) {
        String[] mealNameAndInfo;
        HashMap<String, String> nutritionInfoMap;
        LocalDate dateArgStr = null;

        try {
            InputValidator.validate(userInputStr);
            mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInputStr);
            nutritionInfoMap = ArgumentSplitter.splitForwardSlashArguments(mealNameAndInfo[1]);
        } catch (DukeException e) {
            return new AddItemCommand(true, e.getMessage());
        }

        for (String details : nutritionInfoMap.keySet()) {
            String intArgStr = nutritionInfoMap.get(details);
            try {
                int value = Integer.parseInt(intArgStr);
            } catch (NumberFormatException e) {
                return new AddItemCommand(true, "Unable to parse " + intArgStr
                        + " as an integer. ");
            }
        }
        return new AddItemCommand(new Meal(mealNameAndInfo[0], dateArgStr, nutritionInfoMap));
    }
}
