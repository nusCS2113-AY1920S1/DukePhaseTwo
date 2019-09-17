package leduc.exception;

import leduc.Ui;
import leduc.exception.DukeException;

/**
 * Represent a exception when the deadline date of the deadline task is not given by the user.
 */
public class EmptyDeadlineDateException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyDeadlineDateException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public EmptyDeadlineDateException(Ui ui){
        super();
    }

    /**
     * Ask for a deadline date for the deadline task to the user.
     */
    public String print(){
        return "\t emptyDeadlineDateException:\n\t\t ☹ OOPS!!! Please enter a deadline for the task";
    }
}
