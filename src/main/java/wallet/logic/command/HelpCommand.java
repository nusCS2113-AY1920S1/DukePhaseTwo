package wallet.logic.command;

import wallet.model.Wallet;
import wallet.ui.HelpPrompt;
import wallet.storage.HelpStorage;

import java.util.ArrayList;

/**
 * The HelpCommand Class shows users what the valid commands are.
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";

    /**
     * Shows a list of valid commands to the user and returns false.
     *
     * @param wallet The Wallet object.
     * @return false.
     */
    @Override
    public boolean execute(Wallet wallet) {

        //@@author Xdecosee
        HelpPrompt helpPrompt = new HelpPrompt();
        HelpStorage helpData = new HelpStorage();
        ArrayList<String[]> pathList = helpData.retrievePaths();
        int selectedSection = helpPrompt.prompt(pathList);
        if (selectedSection > 0) {
            helpData.sectionData(selectedSection);
        }
        return false;
        //@@author
    }
}