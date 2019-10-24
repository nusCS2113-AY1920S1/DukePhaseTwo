package duke;


import duke.commands.AddBarCommand;
import duke.commands.AddOverlayCommand;
import duke.commands.AsciiCommand;
import duke.commands.Command;
import duke.commands.CopyCommand;
import duke.commands.DeleteBarCommand;
import duke.commands.DeleteCommand;
import duke.commands.EditCommand;
import duke.commands.GroupCommand;
import duke.commands.HelpCommand;
import duke.commands.ListCommand;
import duke.commands.NewCommand;
import duke.commands.RedoCommand;
import duke.commands.RemindCommand;
import duke.commands.UndoCommand;
import duke.commands.ViewCommand;
import duke.components.SongList;
import duke.components.UndoRedoStack;

import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class Duke {
    /**
     * Chat bot cum task management application that can handle events, deadlines and normal to-do tasks,
     * as well as basic exception handling.
     */
    private Storage storage;
    private TaskList tasks;
    private SongList songs;
    private Ui ui;
    private UndoRedoStack undoRedoStack;

    /**
     * Constructor for the duke.Duke object, which initializes the UI, duke.TaskList and duke.Storage in
     * order to carry out its functions.
     */
    public Duke() {
        ui = new Ui();
        tasks = new TaskList();
        songs = new SongList();
        storage = new Storage(Paths.get("data", "todo_list.txt"));
        try {
            storage.loadToList(songs);
        } catch (DukeException e) {
            System.out.println(ui.showError(e));
            songs = new SongList();
        }
        undoRedoStack = new UndoRedoStack(songs);
    }

    /**
     * Runs the program, constantly asking for and responding to user input, finally terminating
     * upon the word "Bye".
     */
    private void run() {
        System.out.println(ui.showWelcomeMessage());
        boolean isExit = false;
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                Command c = new RemindCommand();
                String output = null;
                try {
                    output = c.execute(tasks, ui, storage);
                    if (!output.equals("")) {
                        System.out.println(output);
                    }
                } catch (DukeException e) {
                    System.out.println(ui.showError(e));
                }
            }
        };
        Timer timer = new Timer("Timer");
        timer.scheduleAtFixedRate(repeatedTask, 1000, 900000);

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                //if the command uses the SongList
                String output;
                if (c instanceof AddBarCommand
                        || c instanceof ViewCommand
                        || c instanceof NewCommand
                        || c instanceof DeleteCommand
                        || c instanceof DeleteBarCommand
                        || c instanceof EditCommand
                        || c instanceof HelpCommand
                        || c instanceof GroupCommand
                        || c instanceof CopyCommand
                        || c instanceof AddOverlayCommand
                        || c instanceof ListCommand
                        || c instanceof AsciiCommand) {
                    output = c.execute(songs, ui, storage);
                    if (!(c instanceof HelpCommand
                        || c instanceof ViewCommand
                        || c instanceof ListCommand)) {
                        undoRedoStack.add(songs);
                    }
                } else if (c instanceof UndoCommand || c instanceof RedoCommand) {
                    output = c.execute(songs, ui, storage, undoRedoStack);
                    songs = undoRedoStack.getCurrentVersion();
                } else {
                    output = c.execute(tasks, ui, storage);
                }
                System.out.println(output);
                isExit = c.isExit();
            } catch (DukeException e) {
                System.out.println(ui.showError(e));
            }
        }
        timer.cancel();
    }

    /**
     * duke.Main function for duke.Duke, which creates a new duke.Duke object and runs it.
     * @param args Standard Java arguments for a main function, in this case, not used
     */
    public static void main(String[] args) {
        new Duke().run();
    }


}
