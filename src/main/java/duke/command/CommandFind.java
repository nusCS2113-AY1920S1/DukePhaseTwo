package duke.command;

import duke.task.TaskList;
import duke.worker.Parser;
import duke.worker.Ui;

public class CommandFind extends Command {
    protected String userInput;

    /**
     * Constructor for CommandFind subCommand Class.
     * @param userInput The User Input to be translated into a Command
     */
    public CommandFind(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList taskList) {
        try {
            String queryInput = Parser.removeStr("find", this.userInput);
            queryInput = queryInput.toLowerCase();
            Ui.dukeSays("Here are the Tasks matching your query '"
                    + queryInput
                    + "'."
            );
            findTasks(queryInput, taskList);
        } catch (Exception e) {
            Ui.dukeSays("No such task found.");
        }
        Ui.printSeparator();
    }

    /**
     * Finds and prints each task that contains the string.
     *
     * @param name     The substring to be found
     * @param taskList The TaskList containing the Tasks.
     */
    public void findTasks(String name, TaskList taskList) {
        for (int index = 0; index < taskList.getSize(); ++index) {
            try {
                if (taskList.getList().get(index).getTaskName().toLowerCase().contains(name)) {
                    taskList.printTaskByIndex(index);
                }
            } catch (Exception e) {
                System.out.println("Read invalid taskName");
            }
        }
    }
}
