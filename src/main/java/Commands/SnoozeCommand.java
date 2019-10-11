package Commands;
import Tasks.*;
import Interface.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents the command to snooze a Task object to a TaskList object.
 */
public class SnoozeCommand extends Command{

    private final int index;
    private final String dateString;
    private final String start;
    private final String end;
    private String modCode;

    /**
     * Creates an SnoozeCommand object.
     * @param index The index representing the task number in the TaskList object
     */
    public SnoozeCommand(int index, String dateString, String start, String end, String modCode){
        this.index = index;
        this.dateString = dateString;
        this.start = start;
        this.end = end;
        this.modCode = modCode;
    }

    /**
     * Executes the snoozing a task inside the TaskList object with the given number.
     * @param todos The TaskList object for todos
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the find message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display snooze message
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException, FileNotFoundException {
        TaskList list = new TaskList();
        ArrayList<Task> todosList = todos.getList();
        ArrayList<Task> eventsList = events.getList();
        ArrayList<Task> deadlinesList = deadlines.getList();
        for (Task task : todosList) {
                list.addTask(task);
        }
        for (Task task : eventsList) {
                list.addTask(task);
        }
        for (Task task : deadlinesList) {
                list.addTask(task);
        }
        if (end == dateString) {
            list.snoozeTask(deadlinesList, index, dateString, dateString, dateString, modCode);

            storage.updateDeadlineList(deadlines);
            return ui.showSnooze(index, deadlinesList.size(), deadlinesList);
        } else {
            list.snoozeTask(eventsList, index, dateString, start, end, modCode);
            storage.updateEventList(events);
            return ui.showSnooze(index, eventsList.size(), eventsList);
        }
    }
}
