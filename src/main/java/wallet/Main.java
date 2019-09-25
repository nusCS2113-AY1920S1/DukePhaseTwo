package wallet;

import wallet.logic.LogicManager;
import wallet.storage.Storage;
import wallet.ui.Reminder;
import wallet.ui.Ui;

public class Main {
    /**
     * The Ui object that handles input and output of the application.
     */
    private Ui ui;
    /**
     * The Storage object that handles the read and write of text file from the local computer.
     */
    private Storage storage;
    /**
     * The TaskList object that handles the list of task added by the user.
     */
    private LogicManager logicManager;
    /**
     * The Reminder object that handles the reminder of undone tasks.
     */
    private Reminder reminder;

    /**
     * Constructs a new Main object.
     */
    public Main() {
        ui = new Ui();
        storage = new Storage();
        logicManager = new LogicManager(storage);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    /**
     * Execute and run the Duke application.
     */
    public void run() {
        ui.welcomeMsg();

        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readLine();
            ui.printLine();
            isExit = logicManager.execute(fullCommand);
            //isExit = Command.parse(cmd, taskList, storage, scheduleList, contactList, recordList, expenseList);
            ui.printLine();
        }
        ui.byeMsg();
    }
}
