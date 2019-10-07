package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Storage;
import controlpanel.Ui;
import money.Account;

/**
 * This command deletes an income source from the Total Income List according to index.
 */
public class DeleteIncomeCommand extends MoneyCommand {

    private String inputString;
    private int serialNo;

    /**
     * Constructor of the command which initialises the delete income command
     * with the index of the item to be deleted within the user input.
     * @param command delete command inputted from user
     */
    public DeleteIncomeCommand(String command) {
        inputString = command;
        String temp = inputString.replaceAll("[^0-9]", "");
        serialNo = Integer.parseInt(temp);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the delete income command. Takes the index of the item
     * to be deleted from the Total Income List and checks for the item
     * Deletes the item from the list if the item is found
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws DukeException When the index given is out of bounds of the list
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        if (serialNo > account.getIncomeListTotal().size()) {
            throw new DukeException("The serial number of the income is Out Of Bounds!");
        }

        ui.appendToOutput(" Noted. I've removed this income source:\n");
        ui.appendToOutput("  " + account.getIncomeListTotal().get(serialNo - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getIncomeListTotal().size() - 1));
        ui.appendToOutput(" income sources in the list.\n");
        account.getIncomeListTotal().remove(serialNo - 1);
        storage.writeToFile(account);
    }
}