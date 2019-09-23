package duke.command;

import duke.exception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.TaskType;
import duke.worker.Parser;
import duke.worker.Ui;

public class CommandNewTask extends Command {
    protected String userInput;
    protected TaskType taskType;

    /**
     * Constructor for the CommandNewTask subCommand Class.
     * @param userInput The user input from the CLI.
     */
    public CommandNewTask(String userInput) {
        this.taskType = Parser.parseTaskType(userInput);
        this.userInput = userInput;
        this.commandType = CommandType.TASK;
    }

    @Override
    public void execute(TaskList taskList) {
        if (this.taskType == TaskType.BLANK) {
            return;
        }
        try {
            checkS(this.userInput);
        } catch (DukeException e) {
            e.printStackTrace();
            return;
        }
        Task newTask = TaskList.createTask(this.taskType, this.userInput);
        taskList.addTask(newTask);
        Ui.dukeSays("I've added "
                + newTask.genTaskDesc()
                + " to your private list("
                + String.valueOf(taskList.getSize())
                + ")."
        );
    }

    /**
     * Throws an exception when there is no '/' in the user input.
     * @param input this is the user's input
     * @throws DukeException this shows the error message and gives the format to follow
     */
    public void checkS(String input) throws DukeException {
        if (this.taskType.equals(TaskType.FDURATION)) {
            if (!Parser.checkSlash(input)) {
                throw new DukeException("Check your format!!! Correct format is: fduration <description> / <time>");
            }
        }
    }
}
