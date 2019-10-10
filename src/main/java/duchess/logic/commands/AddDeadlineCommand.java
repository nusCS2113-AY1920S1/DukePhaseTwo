package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.task.Deadline;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class AddDeadlineCommand extends Command {
    /** List containing String objects. */
    private List<String> words;

    public AddDeadlineCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Task task = new Deadline(words.subList(0, words.size()));
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}
