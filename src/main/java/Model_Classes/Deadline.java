package Model_Classes;

import CustomExceptions.DukeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An object class representing types of tasks with deadlines.
 * Stores the description and when the task should be done by.
 */
public class Deadline extends Task {
    private Date by;

    /**
     * Constructor for the Deadline object.
     * Takes in inputs for description and date/time the tasks should be done by.
     * @param description Description of the task
     * @param by The time the tasks should be done by.
     */
    public Deadline (String description, Date by) {
        super(description);
        this.by = by;
    }

    @Override
    public void snoozeYear(int amount) {
        this.by.setYear(this.by.getYear() + amount);;
    }
    @Override
    public void snoozeMonth(int amount) {
        this.by.setMonth(this.by.getMonth() + amount);;
    }
    @Override
    public void snoozeDay(int amount) {
        this.by.setDate(this.by.getDate() + amount);;
    }
    @Override
    public void snoozeHour(int amount){
        this.by.setHours(this.by.getHours() + amount);
    }
    @Override
    public void snoozeMinute(int amount){
        this.by.setMinutes(this.by.getMinutes() + amount);
    }


    /**
     * Returns the full description including the deadline of the task.
     * @return A string indicating the task type, description, and when it should be done by.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
