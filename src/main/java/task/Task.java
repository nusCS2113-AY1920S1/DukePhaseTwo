package task;

import java.io.Serializable;

/**
 * The parent Task class containing the description of the task and its done status.
 */
public class Task implements Serializable {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected String by;
    protected String at;
    protected String after;
    protected String period;

    /**
     * Creates a Task instance and initialises the required attributes.
     * @param description Description of the Task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Formats the representation of the Task to be printed.
     * @return The task's status icon, description and deadline.
     */
    public String giveTask() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Gives the status icon of the Task.
     * @return The status icon of the Task based on isDone.
     */
    public String getStatusIcon() {
        return (isDone ? "✓" : "✘"); //return tick or X symbols
    }

    /**
     * Sets as done the isDone attribute.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets a new value to the by attribute.
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     * Sets a new value to the at attribute.
     */
    public void setAt(String at) {
        this.at = at;
    }

    /**
     * Sets a new value to the description attribute.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the Task.
     * @return Task Description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the type of the Task.
     * @return Task type.
     */
    public String getType(){ return this.type; }

    /**
     * Gets the deadline of the Task.
     * @return Task by.
     */
    public String getBy(){ return this.by; }

    /**
     * Gets the period of the Task.
     * @return Task at.
     */
    public String getAt(){ return this.at; }
}
