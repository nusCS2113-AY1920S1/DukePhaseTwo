package moneycommands;

import controlpanel.Parser;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import controlpanel.DukeException;
import money.Account;
import money.Expenditure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This command adds an expenditure to the Total Expenditure List.
 */
public class AddExpenditureCommand extends MoneyCommand {

    private String inputString;
    private SimpleDateFormat simpleDateFormat;

    /**
     * Constructor of the command which initialises the add expenditure command
     * with the expenditure data within the user input.
     * @param command add command inputted from user
     */
    public AddExpenditureCommand(String command) {
        inputString = command.replaceFirst("spent ", "");
        simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the add expenditure command. Takes the input from user
     * and adds an expenditure to the Total Expenditure List.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws ParseException If invalid date is parsed
     * @throws DukeException When the command is invalid
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws ParseException, DukeException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String[] splitStr = inputString.split("/amt ", 2);
        String description = splitStr[0];
        String[] furSplit = splitStr[1].split("/cat ", 2);
        float price = Float.parseFloat(furSplit[0]);
        String[] morSplit = furSplit[1].split("/on ", 2);
        String category = morSplit[0];
        LocalDate boughtTime = Parser.shortcutTime(morSplit[1]);
        Expenditure e = new Expenditure(price, description, category, boughtTime);
        account.getExpListTotal().add(e);
        storage.writeToFile(account);

        ui.appendToOutput(" Got it. I've added this to your total spending: \n");
        ui.appendToOutput("     ");
        ui.appendToOutput(account.getExpListTotal().get(account.getExpListTotal().size() - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getExpListTotal().size() + " expenses listed\n");
    }
}