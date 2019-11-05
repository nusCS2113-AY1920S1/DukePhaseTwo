
package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;

import gazeeebo.exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class FixDurationCommand extends Command {
    /**
     * @param list    task list
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that
     *                deals with storing data to the Save.txt file.
     * @param commandStack
     * @param deletedTask
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException,
            IOException {
        String description = "";
        String duration = "";
        String[] splitstring;
        splitstring = ui.fullCommand.split("/require");
        description = splitstring[0];
        duration = splitstring[1];

        FixedDuration to = new FixedDuration(description, duration);
        list.add(to);
        System.out.println("Got it. I've added this task:");
        System.out.println(to.listFormat());
        System.out.println("Now you have "
                + list.size() + " tasks in the list.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.writeToSaveFile(sb.toString());
    }

    /**
     * Tells the main Duke class that the system
     * should not exit and continue running.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

}