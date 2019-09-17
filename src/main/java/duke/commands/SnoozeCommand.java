package duke.commands;

import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.tasks.Task;
import duke.ui.Ui;
import java.time.LocalDateTime;

/**
 * Class representing a command to snooze/reschedule/postpone a task.
 */
public class SnoozeCommand extends Command {
    private int index;
    private LocalDateTime newDate;

    /**
     * Creates a new SnoozeCommand with the given index and newDate of the task.
     *
     * @param index The index of the task.
     */
    public SnoozeCommand(int index, LocalDateTime newDate){
        this.index = index;
        this.newDate = newDate;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Parser parser, Ui ui, Storage storage) throws DukeException {
        try {
            Task task = storage.getTasks().get(index).updateDate(newDate);
            parser.setParserResponse(ui.getUpdateDate(task));
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(MessageUtil.OUT_OF_BOUNDS);
        }
        storage.write();
    }
}
