package duke;

import java.util.List;

import duke.command.Command;
import duke.command.ListCommand;
import duke.exceptions.ModBadRequestStatus;
import duke.exceptions.ModException;
import duke.exceptions.ModTimeIntervalTooCloseException;
import duke.modules.ModuleInfoSummary;
import duke.util.JsonWrapper;
import duke.util.ParserWrapper;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.util.Reminder;

public class Duke {
    /**
     * Classes used for storage of data
     * Ui output and inputs and current
     * active tasks in TaskList and reminder.
     */
    private Storage store;
    private Ui ui;
    private TaskList tasks;
    private ParserWrapper parser;
    private Reminder reminder;
    private JsonWrapper data;

    /**
     * Constructor for Duke class.
     */
    public Duke() {
        store = new Storage();
        ui = new Ui();
        tasks = new TaskList(store);
        parser = new ParserWrapper();
        data = new JsonWrapper();
    }

    /**
     * The main run loop for Duke, requesting for user input
     * and running valid commands. Invalid commands will be
     * alerted to users.
     */
    private void run() {
        ui.helloMsg();
        boolean isExit = false;
        // Starting reminder threads and pulling data from API
        // TODO: pending fix for thread bug
        try {
            // Classes to be initialized during runtime
            reminder = new Reminder(tasks.getTasks());
            //reminder.run();

            // This pulls data once and stores in the data files.
            data.runRequests(store);
        } catch (ModTimeIntervalTooCloseException e) {
            System.out.println(e.getMessage());
        } catch (ModBadRequestStatus er) {
            er.printStackTrace();
        }
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = parser.parse(fullCommand);
                c.execute(tasks, ui, store, reminder);
                // TODO: this line is to demo how to gson parser using the
                //       list command, remove this when creating additional features
                if (c instanceof ListCommand) {
                    List<ModuleInfoSummary> test = data.readJson();
                    System.out.println(test.get(10));
                }
                isExit = c.isExit();
            } catch (ModException e) {
                System.out.println(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Main entry point for Duke.
     * @param args Additional command line parameters, unused.
     */
    public static void main(String[] args) {
        //TODO: args flag could be passed into program for optional runs
        Duke duke = new Duke();
        duke.run();
    }
}
