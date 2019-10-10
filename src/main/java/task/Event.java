package task;

import wrapper.MyDate;

import java.util.Date;
import java.util.LinkedHashMap;

public class Event extends Tasks {

    private MyDate date;

    /**
     * Constructor for class.
     *
     * @param description which is the description of the task.
     * @param type        which is the type of the task.
     * @param starttime   which is the start time of the task.
     * @param endtime     which is the ebd time of the task.
     */
    public Event(String description, String type, String starttime, String endtime) {
        super(description, type);

        date = new MyDate(starttime, endtime);
    }

    public void setTime(String time) {
        this.date = new MyDate(time);
    }

    public void setTime(String time1, String time2) {
        this.date = new MyDate(time1, time2);
    }

    public MyDate getDate() {
        return date;
    }


    public String toMessage() {
        return description + "(at: " + date.toString() + ")";
    }

}