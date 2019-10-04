package duke;

import duke.command.Command;
import duke.exceptions.DukeException;
import duke.parser.Parser;
import duke.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;

public class Duke {
    private Ui ui;
    private FileHandling storage;
    private TaskList tasks;

    /**
     * This constructor instantiates the Duke class by loading data from a file.
     * @param filename stores the file name from which the data is being loaded.
     */
    public Duke(String filename) {
        try {
            ui = new Ui();
            storage = new FileHandling(filename);
            tasks = new TaskList(storage.retrieveData());
        } catch (DukeException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * This function is responsible for executing various tasks/commands related to Duke.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printDash();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * This function is responsible for instantiating Duke with the file name "storeData.txt".
     * storeData.txt is the file from which the data is loaded for the list of tasks.
     * @param args contains the supplied command-line arguments as an array of String objects.
     */
    public static void main(String[] args) {
        new Duke("storeData.txt").run();
    }
}
