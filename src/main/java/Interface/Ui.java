package Interface;
import Tasks.*;

import java.util.ArrayList;

/**
 * Represents the user interface which displays the messages to
 * respond to the user based on the user's input.
 */
public class Ui {
    private static final String NO_FIELD = "void";
    private final String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private final String welcomeMessage = "Hello! I'm Duke\n"
            + "What can I do for you?\n";
    private final String byeMessage = "Bye. Hope to see you again soon!\n";
    protected String line = "____________________________________________________________\n";

    /**
     * Displays the welcome message when Duke program starts.
     */
    public String showWelcome(){
        return "Hello from\n" + logo + welcomeMessage;
    }

    /**
     * Displays the exit message when Duke Program ends.
     */
    public String showBye(){
        return byeMessage;
    }

    /**
     * Displays the list message when user inputs list.
     */
    public String showList(TaskList list){
        String listMessage = "Here are the tasks in your list:\n";
        for (int i = 1; i <= list.taskListSize(); i++) {
            listMessage = listMessage + i + "." + list.taskToString(i-1) + "\n";
        }
        return listMessage;
    }

    /**
     * Displays add task message when user wants to add a task.
     */
    public String showAdd(Task task, int listSize){
        return "Got it. I've added this task:\n" + task.toString() + "\n"
                + "Now you have " + listSize + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays done task message when user marks a task as done.
     */
    public String showDone(Task task){
        return "Nice! I've marked this task as done:\n" + task.toString() + "\n";
    }

    /**
     * Displays the delete task message when user wants to delete a task.
     */
    public String showDelete(Task task, int listSize){
        return "Noted. I've removed this task:\n" + task.toString() + "\n" + "Now you have "
                + listSize + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays the find message when a user wants to find tasks with a specific keyword.
     */
    public String showFind(TaskList list){
        if(list.taskListSize() == 0) {
            return "There are no matching tasks in your list.\n";
        } else {
            String findMessage = "Here are the matching tasks in your list:\n";
            for (int i = 1; i <= list.taskListSize(); i++) {
                findMessage = findMessage + i + "." + list.taskToString(i - 1) + "\n";
            }
            return findMessage;
        }
    }

    public String showFreeTimes(String message){
        return ("You are available at: \n" + message);
    }

    /**
     * Displays the chosen free time after the user select the best free time slot available.
     * @param message The chosen free time
     * @return The chosen free time. Otherwise, operation cancelled.
     */
    public String showFixedDurationTask(String message){
        return message.equals(NO_FIELD) ? "Operation cancelled!\nPlease enter another command.": "Your selected task is been added.\n" + message;
    }

    /**
     * Displays the reminder message when user asks for a reminder.
     */
    public String showReminder(TaskList list){
        if(list.taskListSize() == 0) {
            return "There are no upcoming tasks this week.\n";
        } else {
            String remindMessage = "Here are your tasks for this week:\n";
            for (int i = 1; i <= list.taskListSize(); i++) {
                remindMessage = remindMessage + i + "." + list.taskToString(i - 1) + "\n";
            }
            return remindMessage;
        }
    }

    /**
     * Displays the error message if a file is not found.
     */
    public String showLoadingError(Exception e){
        return "File not found" + e.getMessage() + "\n";
    }

    /**
     * Displays any of the DukeException error message caught throughout the program.
     */
    public String showError(DukeException e){
        return e.getMessage() + "\n";
    }


    public String showUserSchedule(String finalSchedule) {
        return "Here is your schedule which have been categorised into TODO, DEADLINE and EVENTS\n" +
                finalSchedule;
    }

    /**
     * Displays the snooze message when a user wants to snooze a task.
     */
    public String showSnooze(int index, int listSize, ArrayList<Task> list) {
        return "Noted. I've snoozed task number " + (index+1) + " to: " + "\n" + list.get(listSize-1) + "\n" +
                "Now you have " + listSize + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays the show reminder message when user enter a task with a period to do within
     * @param TaskDescription The description of the task entered
     * @param startDate The start date for task
     * @param endDate The end date for task
     * @param isValid determine if user's input date is entered correctly
     * @return This returns the reminder message which contain the task description and the start
     * and end date
     */
    public String showReminder(String TaskDescription, String startDate, String endDate, boolean isValid) {
        if (!isValid) {
            return "Please enter another valid date in format of DD/MM/yyyy";
        } else {
            return "Reminder have been set for: " + TaskDescription + "." + " Start Date: " + startDate +
                    " End Date: " + endDate + "\n";
        }
    }
    public String showTentativeSchedule(TaskList tentativeDates) {
        String out = "Here is your tentative schedule. \n" ;
        for (int i = 0; i< tentativeDates.taskListSize(); i++){
            out += i+1 + "." + tentativeDates.taskToString(i ) + "\n";
        }
        return  out;
    }
}
