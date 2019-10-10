package leduc.task;

import leduc.task.Task;

/**
 * Represents a Todo leduc.task.Task.
 */
public class TodoTask extends Task {
    private String tag; // [T]

    /**
     * Constructor of leduc.task.TodoTask. The task is not done by default.
     * @param task String representing the description of the todo task.
     */
    public TodoTask(String task){
        super(task);
        this.tag ="[T]";
    }

    /**
     * Constructor of leduc.task.TodoTask. The task could be done or not depending on the parameter given.
     * @param task String representing the description of the todo task.
     * @param mark represent if the task is done or not.
     */
    public TodoTask(String task, String mark){
        super(task);
        super.setMark(mark);
        this.tag ="[T]";
    }

    /**
     * Getter of the tag ([T]).
     * @return a String ([T]).
     */
    public String getTag(){ return this.tag;}
    /**
     * to know if whether is a todo task of not
     * @return true
     */
    @Override
    public boolean isTodo(){
        return true;
    }
    /**
     * visualize a event task
     * @return the string format to see a event task
     */
    public String toString(){
        return super.toString();
    }
}
