package commands;

import Storage.Storage;
import Tasks.FixedDuration;
import Tasks.Task;
import UI.Ui;
import java.io.IOException;
import Exception.DukeException;

import java.text.ParseException;
import java.util.ArrayList;

public class FixDurationCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        String duration = "";
        String[] splitstring;
        splitstring = ui.FullCommand.split("/require");
        description = splitstring[0];
        duration = splitstring[1];

        FixedDuration to = new FixedDuration(description, duration);
        list.add(to);
        System.out.println("Got it. I've added this task:");
        System.out.println(to.listFormat());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
    }

    @Override
    public boolean isExit() {
        return false;
    }

}