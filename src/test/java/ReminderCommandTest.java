import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddDeadlineCommand;
import duchess.logic.commands.ReminderCommand;
import duchess.logic.parser.Parser;
import duchess.model.task.DuchessLog;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReminderCommandTest {

    @Test
    public void testExecuteWithoutDeadlines() throws DuchessException {
        String filename = "text_file";
        Store store = new Store();
        Ui ui = new Ui();
        Storage storage = new Storage(filename);

        assertTrue(store.getTaskList().size() == 0);
        ReminderCommand reminderCommand = new ReminderCommand();
        reminderCommand.execute(store, ui, storage);

        assertTrue(store.getTaskList().size() == 0);
    }

    @Test
    public void testExecuteWithDeadlines() throws DuchessException {
        String firstDeadline = "deadline homework assignment /by 23/12/2019 1800";
        String secondDeadline = "deadline school project tasks /by 01/11/2020 0900";

        String filename = "text_file";
        Store store = new Store();
        Ui ui = new Ui();
        Storage storage = new Storage(filename);
        Parser parser = new Parser();
        DuchessLog duchessLog = new DuchessLog();

        // Adding the firstDeadline
        List<String> firstWords = Arrays.asList(firstDeadline.split(" "));
        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(firstWords);
        addDeadlineCommand.execute(store, ui, storage);


        // Adding the firstDeadline
        List<String> secondWords = Arrays.asList(firstDeadline.split(" "));
        addDeadlineCommand = new AddDeadlineCommand(secondWords);
        addDeadlineCommand.execute(store, ui, storage);

        assertTrue(store.getTaskList().size() == 2);
        ReminderCommand reminderCommand = new ReminderCommand();
    }
}