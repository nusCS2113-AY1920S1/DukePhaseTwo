package leduc.command;

import leduc.Date;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.EventsTask;
import leduc.task.TaskList;

/**
 * Represents a event task Command.
 * Allow to add a event task to the task list and to the data file.
 */
public class EventCommand extends Command {

    /**
     * static variable used for shortcut
     */
    public static String eventShortcut = "event";
    /**
     * Constructor of EventCommand.
     * @param user String which represent the input string of the user.
     */
    public  EventCommand(String user){
        super(user);
    }

    /**
     * Allow to add a event task to the task list and to the data file.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws EmptyEventDateException Exception caught when the period of the event task is not given by the user.
     * @throws EmptyEventException Exception caught when the description of the event task is not given by the user.
     * @throws NonExistentDateException Exception caught when one of the two date given does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify
     * @throws ConflictDateException Exception thrown when the new event is in conflict with others event
     */
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws EmptyEventDateException, EmptyEventException, NonExistentDateException, FileException, ConflictDateException {
        String[] taskDescription = user.substring(5).split("/at");
        if (taskDescription[0].isBlank()) {
            throw new EmptyEventException();
        }
        else if (taskDescription.length == 1) { // no /at in input
            throw new EmptyEventDateException();
        }
        else {
            String description = taskDescription[0].trim();
            String periodString = taskDescription[1].trim();
            //date format used: dd/MM/yyyy HH:mm - dd/MM/yyyy HH:mm
            String[] dateString = periodString.split(" - ");
            if(dateString.length == 1){
                throw new EmptyEventDateException();
            }
            else if(dateString[0].isBlank() || dateString[1].isBlank()){
                throw new EmptyEventDateException();
            }
            Date date1 = new Date(dateString[0]);
            Date date2 = new Date(dateString[1]);
            tasks.verifyConflictDate(date1, date2);
            EventsTask newTask = new EventsTask(description, date1 , date2);
            tasks.add(newTask);
            storage.save(tasks.getList());
            ui.display("\t Got it. I've added this task:\n\t   "
                    + newTask.toString() +
                    "\n\t Now you have " + tasks.size() + " tasks in the list.");
            }
        }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getEventShortcut() {
        return eventShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param eventShortcut the new shortcut
     */
    public static void setEventShortcut(String eventShortcut) {
        EventCommand.eventShortcut = eventShortcut;
    }
}
