package duke.command;

import duke.exceptions.DukeEmptyCommandException;
import duke.exceptions.DukeEmptyListException;
import duke.exceptions.DukeInvalidTimeException;
import duke.tasks.Deadline;
import duke.tasks.Events;
import duke.tasks.Task;

import duke.util.DateTimeParser;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ScheduleCommand extends Command {

    private String input;
    private LocalDate currentDate;

    /**
     * Constructor for the ScheduleCommand class that takes in the user input.
     * @param input User's input in the command line.
     * @throws DukeEmptyCommandException If the user inputs and empty command.
     * @throws DukeInvalidTimeException If the user does not input a date/command after "schedule ".
     */
    public ScheduleCommand(String input) throws DukeInvalidTimeException, DukeEmptyCommandException {
        this.input = input;
        if (input.length() <= 9) {
            throw new DukeEmptyCommandException();
        }
        currentDate = DateTimeParser.getStringToDate(input.substring(9)).toLocalDate();

    }

    /**
     * This method finds all the tasks scheduled on the date that the user specifies, and adds them
     * to an ArrayList of Tasks if the dates match.
     * It then sorts the new ArrayList printArray according to the time the task is scheduled.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param storage Storage object for storing the taskList.
     * @throws DukeEmptyListException When no tasks are found to match that date.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Reminder reminder) throws DukeEmptyListException {
        ArrayList<Task> printArray = new ArrayList<>();
        for (int i = 0; i < tasks.getSize(); i++) {
            if (tasks.access(i) instanceof Deadline) {
                Deadline d = (Deadline) tasks.access(i);
                if (currentDate.equals(d.getDate())) {
                    printArray.add(d);
                }
            } else if (tasks.access(i) instanceof Events) {
                Events e = (Events) tasks.access(i);
                if (currentDate.equals(e.getDate())) {
                    printArray.add(e);
                }
            }
        }
        printArray.sort(this::compare);
        boolean isEmpty = printArray.isEmpty();
        if (isEmpty) {
            throw new DukeEmptyListException();
        } else {
            System.out.println("Here is your schedule for today:");
            ui.printTaskList(printArray);
        }
    }

    /**
     * Custom comparator function for sorting the schedule according to time.
     * @param t1 Task 1 to be compared
     * @param t2 Task 2 to be compared
     * @return true when Task t1 has an earlier time than Task t2
     */
    public int compare(Task t1, Task t2) {
        LocalTime time1 = t1.getTime();
        LocalTime time2 = t2.getTime();
        //ascending order
        return time1.compareTo(time2);
    }

    public boolean isExit() {
        return false;
    }
}

