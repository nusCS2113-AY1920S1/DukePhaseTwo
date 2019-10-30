package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.schedule.Scheduler;
import seedu.hustler.logic.parser.anomaly.DoneAnomaly;

import java.io.IOException;

/**
 * Command to do task in list.
 */
public class DoneCommand extends Command {
    /**
     * User input that contains index of task to do.
     */
    private String[] userInput;

    /**
     * Detects anomalies for input.
     **/
    private DoneAnomaly anomaly = new DoneAnomaly();

    /**
    * Initializes userInput.
    *
    * @param userInput input that contains task id to do
    */
    public DoneCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Does task at index taskIndex inside.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            int oldLevel = Hustler.avatar.getLevelInt();
            int taskIndex = Integer.parseInt(userInput[1]) - 1;
            Hustler.list.doTask(taskIndex);
            Scheduler.remove(Hustler.list.get(taskIndex));
            if (Hustler.avatar.gainXp().getLevel() > oldLevel) {
                ui.showCongrats(Hustler.avatar);
            }
            AvatarStorage.save(Hustler.avatar);
        } catch (CommandLineException | IOException e) {
            ui.showMessage(e.getMessage());
        }
    }
}