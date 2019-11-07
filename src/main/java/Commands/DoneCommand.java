package Commands;

import DukeExceptions.DukeException;
import Commons.Storage;
import Commons.UserInteraction;
import Tasks.Assignment;
import Tasks.TaskList;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Represents the command to done a Task object from a TaskList object.
 */
public class DoneCommand extends Command {

    private Assignment task;
    private final String list;

    /**
     * Creates a DoneCommand object.
     * @param task The task to be mask as done
     * @param list The name of the TaskList that requires changing
     */
    public DoneCommand(String list, Assignment task){
        this.task = task;
        this.list = list;
    }

    private boolean isInsideMapRemove (HashMap<String, HashMap<String, ArrayList<Assignment>>> map, Assignment task) throws DukeException {
        String modCode = task.getModCode();
        String dateOfTask = task.getDate();
        if (!map.containsKey(modCode)) {
            throw new DukeException("Sorry, you have no such mod task to be mark done");
        } else if (!map.get(modCode).containsKey(dateOfTask)) {
            throw new DukeException("Sorry, you have no such date of the mod task to be mark done");
        } else {
            for (Assignment taskInList : map.get(modCode).get(dateOfTask)) {
                if (taskInList.getDateTime().equals(task.getDateTime())) {
                    return true;
                }
            }
            throw new DukeException("Sorry, you have no timing of the mod task to be mark done");
        }
    }

    /**
     * Executes the mark as done of a task inside the TaskList object with the given index.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the mark as done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display delete task message
     * @throws DukeException On ArrayList out of bound error
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws DukeException {
        HashMap<String, HashMap<String, ArrayList<Assignment>>> eventMap = events.getMap();
        HashMap<String, HashMap<String, ArrayList<Assignment>>> deadlineMap = deadlines.getMap();

        if (list.equals("event")) {
            isInsideMapRemove(eventMap, task);
            events.updateTask(task);
            storage.updateEventList(events);
        } else if (list.equals("deadline")) {
            isInsideMapRemove(deadlineMap, task);
            deadlines.updateTask(task);
            storage.updateDeadlineList(deadlines);
        }
        task.setDone(true);
        return ui.showDone(task);
    }
}