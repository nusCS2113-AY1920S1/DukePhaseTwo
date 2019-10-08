package eggventory.items;

import eggventory.enums.TaskType;
import eggventory.exceptions.BadInputException;
import java.util.Calendar;

/**
 * In addition to the deadline and done status (inherited from Stock),
 * the Deadline has a doByDate that is represented by a date class.
 * The save and print strings have been overridden to show more information.
 */

public class Deadline extends Task {

    private DateTime doByDate;

    /**
     * Deadline object has a "by" string as well as a Date object.
     */
    public Deadline(String description, DateTime doByDate) throws BadInputException {
        super(description, TaskType.DEADLINE); //Using the Stock constructor. isDone is set to false.
        this.doByDate =  doByDate;
    }

    public String getDoByDate() {
        return doByDate.returnFormattedDate();
    }

    public Calendar getDate() {
        return doByDate.getAt();
    }

    @Override
    public String saveDetailsString() {
        return super.saveDetailsString() + "/" + doByDate.toString();
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + doByDate.returnFormattedDate() + ")";
    }
}