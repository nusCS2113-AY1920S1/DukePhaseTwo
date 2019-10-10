package command;

import exception.DukeException;
import storage.Storage;
import ui.UI;
import task.TaskList;
import list.DegreeList;

/**
 * ModCommand Class extends the abstract Command class.
 * Called when modifying a task in tasks.
 *
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class ModCommand extends Command {
    private String command;
    private String input;

    public ModCommand(String command, String input) {
        this.command = command;
        this.input = input;
    }

    /**
     * overwrites default execute to modify task in tasks.
     *
     * @param tasks   TasksList has tasks
     * @param ui      UI prints messages
     * @param storage Storage loads and saves files
     * @param lists DegreeList has the array for the user to maintain a list of their degree choices.
     * @throws DukeException DukeException throws exception
     */
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists) throws DukeException {
        switch (this.command) {
            case "remove":
                lists.delete(this.input);
                break;
        case "done":
            tasks.markDone(this.input);
            break;
        case "delete":
            tasks.banishDelete(this.input);
            break;
        case "select":
            tasks.select(this.input);
            break;
        case "snooze":
            tasks.snoozeTask(this.input);
            break;
        default:
            throw new DukeException("Invalid ModCommand");
        }
    }
}
