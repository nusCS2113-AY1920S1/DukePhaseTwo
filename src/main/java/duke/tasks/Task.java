package duke.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Task {

    /**
     * task is the string value of the task name.
     * done is the active status of the task.
     * dateTime is the date and time information if the task requires.
     */
    private String task;
    private Boolean done;
    protected LocalDateTime dateTime;


    /**
     * Constructor to Task class.
     * @param task User's input of the desired task.
     */
    public Task(String task) {
        this.task = task.trim();
        done = false;
        this.dateTime = null;
    }

    public void setTaskDone() {
        done = true;
    }

    public String getTask() {
        return task;
    }

    private boolean getDone() {
        return done;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    /**
     * Function to be used to when writing to the file.
     * @return Returns a string containing task name and done status.
     */
    public String writingFile() {
        return task
                + "|"
                + (getDone() ? "1" : "0");
    }

    @Override
    public String toString() {
        String completed = (done) ? "[✓] " : "[✗] ";
        return completed + task;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Task)) {
            return false;
        }
        Task otherTask = (Task) other;
        return otherTask.getTask().equals(this.getTask());
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, done);
    }

    public boolean isDone() {
        return this.done;
    }
}
