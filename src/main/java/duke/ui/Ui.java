package duke.ui;

import duke.tasks.Task;
import duke.tasks.UniqueTaskList;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Class that handles user input and messages shown to user of this application.
 */
public class Ui {
    private static final String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private static final String MESSAGE_WELCOME = "Hello! I'm duke.Duke\nWhat can I do for you?\n";
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";
    private static final String MESSAGE_MARK_DONE = "Nice! I've marked this task as done:\n  ";
    private static final String MESSAGE_ADDITION = "Got it. I've added this task:\n  ";
    private static final String MESSAGE_DELETE = "Alright! I've removed this task:\n  ";

    private VBox dialogContainer;

    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/duke.png"));

    public Ui(VBox dialogContainer) {
        this.dialogContainer = dialogContainer;
    }

    /**
     * Prints a welcome message to the user, which happens at startup.
     */
    public void showWelcome() {
        show("Hello from\n" + logo);
        show(MESSAGE_WELCOME);
    }

    /**
     * Prints an error message to the user.
     */
    public void showError(String errorMessage) {
        show(errorMessage);
    }

    /**
     * Prints a bye message to the user, which happens upon exit.
     */
    public void showBye() {
        show(MESSAGE_BYE);
    }

    /**
     * Prints the list of duke.tasks.
     */
    public void showList(UniqueTaskList tasks) {
        String result = "Here are the list of tasks:\n";
        int i = 1;
        for (Task t : tasks) {
            result += (i + ". " + t + "\n");
            i += 1;
        }
        show(result);
    }

    /**
     * Prints the description of a task.
     */
    public void showAdd(Task task) {
        show(MESSAGE_ADDITION + task);
    }

    /**
     * Prints the task that is mark done.
     */
    public void showMarkDone(Task task) {
        show(MESSAGE_MARK_DONE + task);
    }

    /**
     * Prints the task that is deleted.
     */
    public void showDelete(Task task) {
        show(MESSAGE_DELETE + task);
    }

    /** Shows message(s) to the user.
     */
    public void show(String msg) {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(msg, dukeImage)
        );
    }
}
