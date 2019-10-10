import gui.Window;
import commands.Command;
import tasks.Task;
import utils.DukeException;
import utils.Parser;
import utils.Storage;
import utils.Reminder;
import core.Ui;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * This is the main class to be executed for DUKE PRO application
 *
 * @author T14-4 team
 */
public class Duke {

    /**
     * deals with loading tasks from the file and saving tasks in the file
     */
    private Storage storage;

    /**
     * an array list contains all the tasks
     */
    private ArrayList<Task> tasks;


    /**
     * A constructor which applies the file path to load previous data
     *
     * @param filePath the file path
     */
    public Duke(String filePath) {
        storage = new Storage(filePath);
        tasks = storage.load();
    }

    /**
     * main running structure of Duke.
     */
    public void run() {
        new Window().newForm();
        Ui.welcome();
        Reminder.checkReminders(tasks);
        boolean isExit = false;
        Scanner in = new Scanner(System.in);
        while (!isExit) {
            try {
                String fullCommand = Ui.readLine(in);
                Command c = Parser.commandLine(fullCommand);
                c.execute(tasks, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                Ui.print(e.getMessage());
            }
        }
    }

    /**
     * Main method of the entire project.
     *
     * @param args command line arguments, not used here
     */
    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }
}
