package commands;

import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FindCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if (ui.FullCommand.length() == 5) {
                throw new DukeException("OOPS!!! The description of a search cannot be empty.");
            } else {
                ArrayList<Task> searchedlist = new ArrayList<Task>();
                for (Task it : list) {
                    if (it.description.contains(ui.FullCommand.substring(6).trim())) {
                        searchedlist.add(it);
                    }
                }
                System.out.println("Here are the matching tasks in your list:");
                for (int i = 0; i < searchedlist.size(); i++) {
                    System.out.println(i + 1 + "." + searchedlist.get(i).listFormat());
                }
            }

        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }

}