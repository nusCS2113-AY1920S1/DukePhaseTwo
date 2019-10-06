package seedu.duke;

import seedu.duke.task.Task;

import java.util.ArrayList;

/**
 * TaskList class is the special type of ArrayList that contains Task and has some special functions used to
 * manipulate the tasks in this list.
 */
public class TaskList extends ArrayList<Task> {
    /**
     * Converts the task list to a string of the pre-determined format that is ready to be displayed by the
     * UI.
     *
     * @return
     */
    @Override
    public String toString() {
        if (this.size() == 0) {
            return "There is nothing in your task list.";
        }
        String msg = "Here are the tasks in your task list:";
        for (int i = 0; i < this.size(); i++) {
            msg += "\n" + (i + 1);
            msg += ". " + this.get(i);
        }
        return msg;
    }

    /**
     * Searches through all the tasks in the list to look for the keyword specified. The result will be the
     * string containing all the task string that is ready to be displayed.
     *
     * @param keyword the target keyword for searching
     * @return a string containing all the tasks found and is ready to be displayed by UI
     */
    public String findKeyword(String keyword) {
        ArrayList<Task> searchResult = new ArrayList<>();
        for (Task task : this) {
            if (task.matchKeyword(keyword)) {
                searchResult.add(task);
            }
        }
        String msg = "";
        if (searchResult.size() > 0) {
            msg += "Here are the matching tasks in your list:";
            for (int i = 0; i < searchResult.size(); i++) {
                msg += "\n" + (i + 1) + ". " + searchResult.get(i);
            }
        } else {
            msg += "There is no matching task in your list.";
        }
        return msg;
    }

    /**
     * Marks the task at the specified index in the task list as done.
     *
     * @param index the target index of which the task is to be marked as done
     * @return a message that is ready to be displayed by UI
     * @throws Parser.UserInputException an exception thrown when index parsing failed or out of range
     */
    public String markDone(int index) throws Parser.UserInputException {
        if (index < 0 || index >= this.size()) {
            throw new Parser.UserInputException("Invalid Index");
        }
        this.get(index).markDone();
        String msg = "Nice! I've marked this task as done:\n";
        msg += this.get(index).toString() + "\n";
        msg += "Now you have " + Integer.toString(this.size())
                + " tasks in the list.";
        return msg;
    }

    /**
     * Deletes the task at the specified index in the task list.
     *
     * @param index the target index of which the task is to be deleted
     * @return a message that is ready to be displayed by UI
     * @throws Parser.UserInputException an exception thrown when index parsing failed or out of range
     */
    public String delete(int index) throws Parser.UserInputException {
        if (index < 0 || index >= this.size()) {
            throw new Parser.UserInputException("Invalid index");
        }
        Task deleted = this.remove(index);
        String msg = "Noted. I've removed this task: \n";
        msg += deleted + "\n";
        msg += "Now you have " + this.size() + " tasks in the list.";
        return msg;
    }

    /**
     * Finds all the tasks that are considered as near.
     *
     * @param dayLimit the maximum number of days from now for a task to be considered as near
     * @return whether the task is near
     */
    public TaskList findNear(int dayLimit) {
        TaskList nearTasks = new TaskList();
        for (Task t : this) {
            if (t.isNear(dayLimit)) {
                nearTasks.add(t);
            }
        }
        return nearTasks;
    }

    /**
     * Snooze or delay the tasks.
     *
     * @param index of the task in taskList.
     * @return string msg.
     */
    public String snoozed(int index) throws Parser.UserInputException {
        if (index < 0 || index >= this.size()) {
            throw new Parser.UserInputException("Invalid index");
        }
        Task task = this.get(index);
        String msg = "";
        if (task.getTaskType() != Task.TaskType.ToDo) {
            task.snooze();
            msg = "Noted. I've snoozed this task: \n";
            msg += task;
        } else {
            Duke.getUI().showError("This task cannot be snoozed");
        }
        return msg;
    }

    /**
     * Detect if a task being added clashes with another task in the list.
     *
     * @param task task to be added.
     * @return TaskList containing tasks that clashes with the new task being added.
     */
    public TaskList findClash(Task task) {
        TaskList clashTasks = new TaskList();
        for (Task t : this) {
            if (t.isClash(task)) {
                clashTasks.add(t);
            }
        }
        return clashTasks;
    }
}