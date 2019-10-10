package leduc.task;

import java.time.LocalDateTime;

/**
 * Abstract class representing a leduc.task.Task
 */
public abstract class Task {
    private String task;
    private String mark;

    /**
     * Constructor of leduc.task.Task
     * @param task String representing the description of the leduc.task.Task.
     */
    protected Task (String task){
        this.task = task;
        this.mark = "[✗]";
    }

    /**
     * Setter of the task description of the leduc.task.Task.
     * @param taskDescription String corresponding to the new task description;
     */
    public void setTask(String taskDescription){
        this.task= taskDescription;
    }

    /**
     * Setter of the mark of the leduc.task.Task ( done or not).
     * @param mark String ( "[✓]" or "[✗]");
     */
    protected void setMark(String mark){
        this.mark= mark;
    }

    /**
     * Set the task to done
     */
    public void taskDone(){
        this.mark = "[✓]";
    }

    /**
     * Getter of the description of the leduc.task.Task.
     * @return a string representing the description of the task.
     */
    public String getTask(){
        return this.task;
    }

    /**
     * Returns the mark of the task.
     * Allows to know if the task is done or not.
     * @return a String which allows to know if the task is done or not.
     */
    public String getMark(){
        return this.mark;
    }

    /**
     * Getter of the tag of the task.
     * Allows to know if it is a todo, deadline or event task.
     * @return a String which allows to know if it is a todo, deadline or event task.
     */
    public abstract String getTag();

    /**
     * to know if whether is a todo task of not
     * @return false
     */
    public boolean isTodo(){
        return false;
    }
    /**
     * to know if whether is a deadline task of not
     * @return false
     */
    public boolean isDeadline(){
        return false;
    }
    /**
     * to know if whether is an event task of not
     * @return false
     */
    public boolean isEvent(){
        return false;
    }

    /**
     * visualize a task
     * @return the string format to see a task
     */
    public String toString(){
        return getTag() + getMark() + " " + getTask();
    }

    /**
     * Help method which returns the date from any task Object.
     */
    public LocalDateTime getDate(){
        if (this.isDeadline()) {
            DeadlinesTask deadline = (DeadlinesTask)this;
            return (deadline.getDeadlines()).getD();
        }
        else if (this.isEvent()){
            EventsTask event = (EventsTask)this;

            return(event.getDateFirst()).getD();
        }
        else{
            return null;
        }
    }
}
