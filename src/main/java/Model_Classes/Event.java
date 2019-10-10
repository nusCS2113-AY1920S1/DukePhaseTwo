package Model_Classes;

import CustomExceptions.DukeException;
import Enums.ExceptionType;
import Operations.CheckAnomaly;
import Operations.TaskList;

import java.util.Date;
/**
 * An object class representing types of tasks that are events.
 * Stores the description and when the event happens.
 */
public class Event extends Task {
    private Date at;

    /**
     * Constructor for Event object
     * Takes in inputs for description of the event and the time the event occurs
     * @param description Description of the event
     * @param at Time the event happens
     */
    public Event(String description, Date at) {
        super(description);
        this.at = at;
    }

    /**
     * Constructor for Event object
     * @param description name and date of Event
     */
    public Event(String description) {
        super(description);
    }

    /**
     * Returns date of Event
     * @return date of Event
     */
    public Date checkDate() { return this.at; }

    /**
     * Snoozes the Event by set amount of years
     * @param amount number of years to snooze
     */
    @Override
    public void snoozeYear(int amount) {
        this.at.setYear(this.at.getYear() + amount);;
    }

    /**
     * Snoozes the Event by set amount of months
     * @param amount number of months to snooze
     */
    @Override
    public void snoozeMonth(int amount) {
        this.at.setMonth(this.at.getMonth() + amount);;
    }

    /**
     * Snoozes the Event by set amount of days
     * @param amount number of days to snooze
     */
    @Override
    public void snoozeDay(int amount) {
        this.at.setDate(this.at.getDate() + amount);;
    }

    /**
     * Snoozes the Event by set amount of hours
     * @param amount number of hours to snooze
     */
    @Override
    public void snoozeHour(int amount){
        this.at.setHours(this.at.getHours() + amount);
    }

    /**
     * Snoozes the Event by set amount of hours
     * @param amount number of minutes to snooze
     */
    @Override
    public void snoozeMinute(int amount){
        this.at.setMinutes(this.at.getMinutes() + amount);
    }

    /**
     * Returns a string that has the full description of the vent including the occurrence time
     * @return A string indicating the task type, description and the occurrence of the task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + at + ")";
    }
}
