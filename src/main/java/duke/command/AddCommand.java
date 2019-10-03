package duke.command;

import duke.extensions.AbnormalityChecker;
import duke.storage.Storage;
import duke.task.*;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * duke.command.AddCommand that deals with the adding of new duke.task.Task objects to the duke.tasklist.TaskList
 */
public class AddCommand extends Command {
    String description;
    String taskType;
    boolean isRecurring = false;
    boolean hasDuration = false;
    String recurrencePeriod;


    public AddCommand(String description, String taskType) {
        this.taskType = taskType;
        this.description = description;
        checkForFlag();
    }

    private void checkForFlag() {
        String[] flagArray = description.split("-");
        if (flagArray.length != 1) {
            switch (flagArray[1].charAt(0)) {
                case 'r':
                    isRecurring = true;
                    description = flagArray[0];
                    recurrencePeriod = flagArray[1].substring(2);
                    break;
                case 'd':
                    hasDuration = true;
            }
        }
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException, IOException {
        switch (taskType) {
            case "todo":
                if (hasDuration) {
                    String[] flagArray = description.split(" -", 2);
                    int duration = Integer.parseInt(flagArray[1].substring(2));
                    tasks.add(new FixedDurationTask(flagArray[0], duration));
                } else if (isRecurring) {
                    tasks.add(new ToDo(description, recurrencePeriod));
                } else {
                    tasks.add(new ToDo(description));
                }
                break;
            case "deadline":
                String[] dInfo = description.split(" /by ");
                SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyyy HHmm");
                Date by = dFormat.parse(dInfo[1]);
                if (isRecurring) {
                    tasks.add(new Deadline(dInfo[0], by, recurrencePeriod));
                } else {
                    tasks.add(new Deadline(dInfo[0], by));
                }

                break;
            case "event":
                String[] eInfo = description.split(" /at ");
                SimpleDateFormat eFormat = new SimpleDateFormat("ddMMyyyy HHmm");
                Date at = eFormat.parse(eInfo[1]);
                Event newEvent;
                if (isRecurring) {
                    newEvent = new Event(eInfo[0], at, recurrencePeriod);
                } else {
                    newEvent = new Event(eInfo[0], at);
                }
                AbnormalityChecker abnormalityChecker = new AbnormalityChecker(tasks);
                if (abnormalityChecker.checkEventClash(newEvent)) {
                    System.out.println("There is a clash with another event at the same time");
                } else {
                    tasks.add(newEvent);
                }
                break;
        }
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}