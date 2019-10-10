package duke.command;

import duke.task.Task;
import duke.task.TaskList;
import duke.worker.Parser;
import duke.worker.Ui;
import java.util.ArrayList;

public class CommandMarkDone extends Command {
    private String userInput;

    // Constructor
    public CommandMarkDone(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList taskList) {
        try {
            int index = Integer.parseInt(Parser.removeStr("done", this.userInput)) - 1;
            Task mainTask = taskList.getList().get(index);
            mainTask.markDone();
            loadQueuedTasks(taskList, mainTask);
            Ui.dukeSays(genMarkDoneReply(index, taskList));
        } catch (Exception e) {
            Ui.dukeSays("Invalid 'done' statement. Please indicate the index of the task you wish to mark done.");
        }
    }

    /**
     * Called to release any tasks triggered by the completion of current task.
     * @param task The Task that has been completed
     * @param taskList The TaskList containing all tasks.
     */
    private void releaseQueuedTasks(Task task, TaskList taskList) {
        if (task.isQueuedTasks()) {
            generateQueuedTasks(task, taskList);
        }
    }

    /**
     * Transfers all queued tasks from the trigger Task to the TaskList.
     * @param task The triggering Task
     * @param taskList The TaskList containing all tasks
     */
    private void generateQueuedTasks(Task task, TaskList taskList) {
        TaskList newTasks = task.getQueuedTasks();
        for (Task newTask : newTasks.getList()) {
            taskList.addTask(newTask);
        }
        task.setQueuedTasks(null);
    }

    /**
     * Generates the standard duke reply to inform user that the Task is marked done.
     * @param index The index of the Task in the TaskList
     * @param taskList The TaskList containing all tasks
     * @return Standard duke reply for user
     */
    private String genMarkDoneReply(int index, TaskList taskList) {
        return "Alrighty, I've marked task '"
                + String.valueOf(index + 1)
                + ") "
                + taskList.getList().get(index).getTaskName()
                + "' as done!";
    }

    /**
     * Loads all queued Tasks from the now-done Task to the main TaskList.
     * @param taskList The Main TaskList for Tasks to be added to
     * @param mainTask The Task that has been marked done
     */
    private void loadQueuedTasks(TaskList taskList, Task mainTask) {
        TaskList queuedTasks = mainTask.getQueuedTasks();
        if (queuedTasks == null) {
            return;
        }
        for (Task newTask : queuedTasks.getList()) {
            taskList.addTask(newTask);
        }
        mainTask.setQueuedTasks(null);
    }
}
