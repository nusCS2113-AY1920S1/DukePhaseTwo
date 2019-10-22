import CustomExceptions.RoomShareException;
import Enums.*;
import Model_Classes.*;
import Operations.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Main class of the RoomShare program.
 */
public class RoomShare {
    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;
    private RecurHandler recurHandler;
    private TempDeleteList tempDeleteList;
    private TaskCreator taskCreator;
    private Help help;
    private ProgressBar pg;


    /**
     * Constructor of a Duke class. Creates all necessary objects and collections for Duke to run
     * Also loads the ArrayList of tasks from the data.txt file
     */
    public RoomShare() throws RoomShareException {
        ui = new Ui();
        help = new Help();
        ui.startUp();
        storage = new Storage();
        parser = new Parser();
        ArrayList<Task> tempStorage = new ArrayList<>();
        tempDeleteList = new TempDeleteList(tempStorage);
        try {
            taskList = new TaskList(storage.loadFile("data.txt"));
        } catch (RoomShareException e) {
            ui.showLoadError();
            ArrayList<Task> emptyList = new ArrayList<>();
            taskList = new TaskList(emptyList);
        }
        recurHandler = new RecurHandler(taskList);
        if (recurHandler.checkRecurrence()) {
            ui.showChangeInTaskList();
            taskList.list();
        }
        pg = new ProgressBar(taskList.getSize(), taskList.getDoneSize());
        pg.showBar();
    }

    /**
     * Deals with the operation flow of Duke.
     */
    public void run() throws RoomShareException {
        boolean isExit = false;
        while (!isExit) {
            String command = parser.getCommand();
            TaskType type;
            try {
                type = TaskType.valueOf(command);
            } catch (IllegalArgumentException e) {
                type = TaskType.others;
            }
            switch (type) {
            case help:
                help.helpCommandList();
                help.showHelp(parser.getCommandLine());
                break;

            case list:
                ui.showList();
                try {
                    taskList.list();
                } catch (RoomShareException e) {
                    ui.showWriteError();
                }
                pg = new ProgressBar(taskList.getSize(), taskList.getDoneSize());
                pg.showBar();
                break;

            case bye:
                isExit = true;
                try {
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showWriteError();
                }
                parser.close();
                ui.showBye();
                break;

            case done:
                try {
                    taskList.done(parser.getIndexRange());
                    ui.showDone();
                } catch (RoomShareException e) {
                    ui.showIndexError();
                }
                break;

            case delete:
                try {
                    int[] index = parser.getIndexRange();
                    taskList.delete(index, tempDeleteList);
                    ui.showDeleted(index);
                } catch (RoomShareException e) {
                    ui.showIndexError();
                }
                break;

            case restore:
                int restoreIndex = parser.getIndex();
                tempDeleteList.restore(restoreIndex, taskList);
                break;

            case find:
                ui.showFind();
                taskList.find(parser.getKey().toLowerCase());
                break;

            case priority:
                boolean success = true;
                try {
                    taskList.list();
                    ui.priority();
                    taskList.setPriority(parser.getPriority());
                } catch (RoomShareException e) {
                    success = false;
                    ui.priority();
                } finally {
                    if (success) {
                        taskList.sortPriority();
                        ui.prioritySet();
                    }
                }
                break;

            case add:
                try {
                    String input = parser.getCommandLine();
                    taskCreator = new TaskCreator();
                    if(!(CheckAnomaly.checkTask((taskCreator.create(input))))) {
                        taskList.add(taskCreator.create(input));
                        ui.showAdd();
                    } else {
                        throw new RoomShareException(ExceptionType.timeClash);
                    }
                } catch (RoomShareException e) {
                    ui.showWriteError();
                }
                break;
                
            case snooze :
                try {
                    int index = parser.getIndex();
                    int amount = parser.getAmount();
                    TimeUnit timeUnit = parser.getTimeUnit();
                    taskList.snooze(index, amount, timeUnit);
                    ui.showSnoozeComplete(index + 1, amount, timeUnit);
                } catch (IndexOutOfBoundsException e) {
                    ui.showIndexError();
                } catch (IllegalArgumentException e) {
                    ui.showTimeError();
                }
                break;

            case reorder:
                int firstIndex = parser.getIndex();
                ui.promptSecondIndex();
                int secondIndex = parser.getIndex();
                ui.showReordering();
                taskList.reorder(firstIndex, secondIndex);
                break;

            case subtask:
                int index = parser.getIndexSubtask();
                String subtasks = parser.getCommandLine();
                if( TaskList.currentList().get(index) instanceof Assignment ) {
                    ((Assignment) TaskList.currentList().get(index)).setSubTasks(subtasks);
                } else {
                    throw new RoomShareException(ExceptionType.subTask);
                }
                break;

            default:
                ui.showCommandError();
                break;
            }
        }
    }

    /**
     * Main function of Duke.
     * Creates a new instance of Duke class
     * @param args command line arguments
     * @throws RoomShareException Custom exception class within Duke program
     */
    public static void main(String[] args) throws RoomShareException {
        new RoomShare().run();
        System.exit(0);
    }
}
