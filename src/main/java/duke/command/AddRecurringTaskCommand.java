package duke.command;

import duke.Time;
import duke.Ui;
import duke.task.Recurring;
import duke.task.Task;
import duke.task.TaskList;

public class AddRecurringTaskCommand extends AddCommand {

    private String dayOfWeek;
    private int position;
    private Time timeObj = new Time();

    public AddRecurringTaskCommand(String taskDescription) {
        super(taskDescription);
    }

    /**
     * This method will split the string into the task description and the day of the week.
     * @param tasks duke.task.TaskList containing all the tasks stored.
     * @throws Exception e when user input is invalid.
     */
    @Override
    public void execute(TaskList tasks) {
        try {
            position = taskDescription.indexOf("/");
            dayOfWeek = taskDescription.substring(position + 7);
            if (dayOfWeek.equals("monday")
                    || dayOfWeek.equals("tuesday")
                    || dayOfWeek.equals("wednesday")
                    || dayOfWeek.equals("thursday")
                    || dayOfWeek.equals("friday")
                    || dayOfWeek.equals("saturday")
                    || dayOfWeek.equals("sunday")) {
                Task newTask = new Recurring(taskDescription.substring(0, position - 1), dayOfWeek);
                tasks.add(newTask);
                Ui.echoAdd(newTask, tasks.size());
            } else {
                Ui.printInvalidDayInput();
            }
        } catch (Exception e) {
            Ui.printRecurringTaskError();
        }
    }
}
