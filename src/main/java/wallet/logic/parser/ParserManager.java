package wallet.logic.parser;

import wallet.logic.command.AddCommand;
import wallet.logic.command.Command;
import wallet.logic.command.DeleteCommand;
import wallet.logic.command.EditCommand;
import wallet.logic.command.ExitCommand;
import wallet.logic.command.HelpCommand;
import wallet.logic.command.ListCommand;
import wallet.logic.command.ViewCommand;

import java.text.ParseException;

/**
 * The ParserManager Class that handles all CommandParser classes.
 */
public class ParserManager {
    /**
     * Parses the user input command and returns the corresponding Command object.
     * @param fullCommand The input of user.
     * @return The corresponding Command object.
     */
    public Command parseCommand(String fullCommand) throws ParseException {
        String[] arguments = fullCommand.split(" ", 2);

        switch (arguments[0]) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments[1]);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments[1]);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments[1]);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments[1]);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments[1]);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            //fallthrough

        default:
            return new HelpCommand();
        }
    }
}
