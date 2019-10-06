package seedu.duke.task;

import seedu.duke.Duke;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Event class is a typ of task with a date/time when the event is going to happen.
 */
public class Event extends Task {
    private LocalDateTime time;

    /**
     * Instantiates the Event class with name and time. Time must be passed in during the instantiation as it
     * cannot be changed later.
     *
     * @param name name of the Event
     * @param time time of the Event that is going to happen
     */
    public Event(String name, LocalDateTime time) {
        super(name);
        this.time = time;
        this.taskType = TaskType.Event;
    }

    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Instantiates the Event class with name and time. Time must be passed in during the instantiation as it
     * cannot be changed later. Supports adding a task to be done after the first main task.
     *
     * @param name    name of the Event
     * @param time    time of the Event that is going to happen
     * @param doAfter task to be done after the main task
     * @param tags     tag associated with the task
     */
    public Event(String name, LocalDateTime time, String doAfter, ArrayList<String> tags) {
        super(name);
        this.time = time;
        setDoAfterDescription(doAfter);
        this.taskType = TaskType.Event;
        setTags(tags);
    }

    /**
     * Converts the Event to a human readable string containing important information about the Event,
     * including the type and time of this Event.
     *
     * @return a human readable string containing the important information
     */
    @Override
    public String toString() {
        String output = "";
        output = "[E]" + this.getStatus() + " (by: " + formatDate() + ")";
        if (this.doAfterDescription != null) {
            output += "\n\tAfter which: " + doAfterDescription;
        }
        for (String tagName : tags) {
            output += " #" + tagName + "#";
        }
        return output;
    }

    /**
     * Outputs a string with all the information of this Event to be stored in a file for future usage.
     *
     * @return a string containing all information of this Event
     */
    @Override
    public String toFileString() {
        String output = "";
        output = (this.isDone ? "1" : "0") + " event " + this.name + " /at "
                + formatDate();
        if (this.doAfterDescription != null) {
            output += " /doafter " + doAfterDescription;
        }
        for (String tagName : tags) {
            output += " #" + tagName + "#";
        }
        return output;
    }

    /**
     * Outputs a formatted string of the time of this Event. The format is the same as input format and is
     * shared by all tasks.
     *
     * @return a formatted string of the time of this Event
     */
    protected String formatDate() {
        return format.format(this.time);
    }

    /**
     * Calculates whether the time set for the event is near enough.
     *
     * @param dayLimit maximum number of days from now for the event to be considered as near
     * @return the flag whether the event is near enough
     */
    @Override
    public boolean isNear(int dayLimit) {
        LocalDateTime now = LocalDateTime.now();
        if (this.time.compareTo(now) >= 0) {
            if (now.compareTo(this.time.minusDays(dayLimit)) >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void snooze() {
        time = time.plusDays(3);
    }

    /**
     * Check if this task clashes with the new task being added.
     *
     * @param task the new task being added into the list.
     * @return true if this task clashes with the new task being added, false if not.
     */
    @Override
    public boolean isClash(Task task) {
        try {
            if (task.taskType.equals(TaskType.Deadline)) {
                Deadline deadlineTask = (Deadline) task;  // downcasting task to Deadline in order to use
                // getTime().
                if (this.time.compareTo(deadlineTask.getTime()) == 0) {
                    return true;
                }
            }
            if (task.taskType.equals(TaskType.Event)) {
                Event eventTask = (Event) task;  // downcasting task to Event in order to use getTime().
                if (this.time.compareTo(eventTask.getTime()) == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            Duke.getUI().showError("Error when finding clashes of tasks.");
        }
        return false;
    }
}
