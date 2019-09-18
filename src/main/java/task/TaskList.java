package task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The TaskList class handles all operations performed on the TaskList as well as stores the TaskList.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class TaskList {

    private ArrayList<Task> listOfTasks;

    public TaskList(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    /**
     * This custom comparator allows the sorting of both deadlines and events.
     *
     * @param task contains the task that needs to be added.
     */
    public static final Comparator<Task> DateComparator = (firstDate, secondDate) -> {
        LocalDateTime localDateTime = LocalDateTime.of(1,1,1,1,1,
                1,1);
        if (firstDate.fromDate == localDateTime && secondDate.fromDate == localDateTime)
        {
            if (firstDate.atDate.isBefore(secondDate.atDate)){ return -1; }
            else{ return 1; }
        }
        else if (firstDate.fromDate == localDateTime)
        {
            if (firstDate.atDate.isBefore(secondDate.fromDate)){ return -1; }
            else{ return 1; }
        }
        else if (secondDate.fromDate == localDateTime) {
            if (firstDate.fromDate.isBefore(secondDate.atDate)){ return -1; }
            else{ return 1; }
        }
        else {
            if (firstDate.fromDate.isBefore(secondDate.fromDate)){ return -1; }
            else{ return 1; }
        }
    };

    /**
     * This function allows the use to delete a particular task.
     *
     * @param task contains the task that needs to be added.
     */
    public void add(Task task) {
        listOfTasks.add(task);
    }

    /**
     * This function allows the use to delete a particular task.
     *
     * @param indexOfTask this is the index of the task which needs to be deleted.
     */
    public Task delete(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        listOfTasks.remove(task);
        return task;
    }

    /**
     * This function allows the user to find tasks with a particular keyword.
     *
     * @param keyWord this string contains the keyword the user is searching for.
     */
    public ArrayList<Task> find(String keyWord) {
        ArrayList<Task> holdFoundTasks = new ArrayList<>();
        for (int i = 0; i < listOfTasks.size(); i++) {
            String find_match = listOfTasks.get(i).toString();
            if (find_match.contains(keyWord)) {
                holdFoundTasks.add(listOfTasks.get(i));
            }
        }
        return holdFoundTasks;
    }
    /**
     * Performs a check as to determine if the task being added has a clash with another task already scheduled.
     *
     * @param taskToCheck the task trying to be added by the user.
     * @param command contains the command type of the task to determine the action to perform.
     * @return boolean true if there is a clash, false if there is not clash.
     */
    public boolean isClash(Task taskToCheck, String command) {
        if (command.contains("event")) {
            for (Task task : listOfTasks) {
                if ((task.toString()).contains("[E]")) {
                    if (task.fromDate.isBefore(taskToCheck.toDate) && task.toDate.isAfter(taskToCheck.fromDate)) {
                        return true;
                    }
                }
                else if ((task.toString()).contains("[D]")){
                    if (taskToCheck.fromDate.isBefore(task.atDate) && taskToCheck.toDate.isAfter(task.atDate)) {
                        return true;
                    }
                }
            }
        }
        else{
            for (Task task : listOfTasks) {
                if ((task.toString()).contains("[E]")) {
                    if (task.fromDate.isBefore(taskToCheck.atDate) && task.toDate.isAfter(taskToCheck.atDate)) {
                        return true;
                    }
                }
                else if ((task.toString()).contains("[D]")){
                    if (task.atDate == taskToCheck.atDate) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * This function allows the user to mark a particular task as done.
     *
     * @param indexOfTask this is the index of the task which needs to be marked as done.
     */
    public Task markAsDone(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        task.markAsDone();
        return task;
    }

    public void updateDate(Task taskToBeChanged, String command,LocalDateTime atDate, LocalDateTime fromDate, LocalDateTime toDate){

        if("event".equals(command)){
            taskToBeChanged.fromDate = fromDate;
            taskToBeChanged.toDate = toDate;
        }
        else {
            taskToBeChanged.atDate = atDate;
        }

    }
    /**
     * This function allows the user to obtain the tasks on a particular date.
     *
     * @param dayToFind is of String type which contains the desired date of schedule.
     * @return sortDateList the sorted schedule of all the tasks on a particular date.
     */
    public ArrayList<Task> schedule(String dayToFind){
        ArrayList<Task> sortedDateList = new ArrayList<Task>();
        for (int i = 0; i < listOfTasks.size(); i++) {
            if(!(listOfTasks.get(i).getClass() == task.Todo.class) && listOfTasks.get(i).toString().contains(dayToFind)) {
                sortedDateList.add(listOfTasks.get(i));
            }
        }
        Collections.sort(sortedDateList,DateComparator);
        return sortedDateList;
    }

    public ArrayList<Task> getTasks() {
        return listOfTasks;
    }

    public int getSize() {
        return listOfTasks.size();
    }
}