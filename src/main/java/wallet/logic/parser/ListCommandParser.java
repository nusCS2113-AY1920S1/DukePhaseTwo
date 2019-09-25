package wallet.logic.parser;

import wallet.logic.command.ListCommand;

/**
 * The ListCommandParser class helps to
 * change user input String into appropriate parameters.
 */
public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String input) {
        if (input != "") {
            return new ListCommand(input);
        }

        return null;
    }
}
