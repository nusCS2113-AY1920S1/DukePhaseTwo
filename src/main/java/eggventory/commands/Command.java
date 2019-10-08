package eggventory.commands;

import eggventory.StockType;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.exceptions.BadInputException;
import eggventory.enums.CommandType;

/**
 * This is an abstract class.
 * Command objects are sent from the Parser and executed with StockType or Ui.
 * Commands include: add, delete, find, list.
 */

public class Command {


    protected CommandType type;

    //Currently the default constructor is a bad command
    public Command() {
        this.type = CommandType.BAD;
    }

    public Command(CommandType type) {
        this.type = type;
    }

    public CommandType getType() {
        return type;
    }

    /**
     * Executes the command.
     * Prints the list or saves the list and sends exit message.
     * Might need to separate into bye and list commands.
     */
    public void execute(StockType list, Ui ui, Storage storage) throws BadInputException {
        //if (type == CommandType.REMINDER) {
        //    list.printReminders();

        if (type == CommandType.BYE) {
            storage.save(list.getStockList());
            ui.printExitMessage();
        }
    }
}
