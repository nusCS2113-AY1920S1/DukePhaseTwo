package gazeeebo.commands.expenses;

import gazeeebo.storage.Storage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * Undo previous commands.
 *
 * @param
 * @return
 * @throws IOException
 */
public class UndoExpenseCommand {
    public static Map<LocalDate, ArrayList<String>> undoExpenses(
            Map<LocalDate, ArrayList<String>> expenses,
            final Stack<Map<LocalDate,
                    ArrayList<String>>> oldExpenses,
            final Storage storage) throws IOException {

        if (!oldExpenses.empty()) {
            expenses = oldExpenses.peek();
            String toStore = "";
            for (LocalDate key : expenses.keySet()) {
                if (expenses.get(key).size() > 1) {
                    for (int i = 0; i < expenses.get(key).size(); i++) {
                        toStore = toStore.concat(key + "|"
                                + expenses.get(key).get(i)
                                + "\n");
                    }
                } else if (expenses.get(key).size() == 1) {

                    toStore = toStore.concat(key + "|"
                            + expenses.get(key).get(0)
                            + "\n");
                }
            }
            storage.Storages_Expenses(toStore);
            oldExpenses.pop();
            System.out.println("You have undid the previous command.");
        } else {
            System.out.println("The previous command cannot be undone");
        }
        return expenses;
    }
}
