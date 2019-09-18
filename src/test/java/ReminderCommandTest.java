import duke.command.AddDeadlineCommand;
import duke.command.ReminderCommand;
import duke.command.Storage;
import duke.command.Ui;
import duke.dukeexception.DukeException;
import duke.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReminderCommandTest {

    @Test
    public void testExecuteWithoutDeadlines() throws DukeException {
        String filename = "text_file";
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage(filename);

        assertTrue(taskList.getTasks().size() == 0);
        ReminderCommand reminderCommand = new ReminderCommand();

        assertTrue(taskList.getTasks().size() == 0);
    }

    @Test
    public void testExecuteWithDeadlines() throws DukeException {
        String firstDeadline = "deadline homework assignment /by 23/12/2019 1800";
        String secondDeadline = "deadline school project tasks /by 01/11/2020 0900";

        String filename = "text_file";
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage(filename);
        Parser parser = new Parser();

        // Adding the firstDeadline
        List<String> firstWords = Arrays.asList(firstDeadline.split(" "));
        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(firstWords);
        addDeadlineCommand.execute(taskList, ui, storage);


        // Adding the firstDeadline
        List<String> secondWords = Arrays.asList(firstDeadline.split(" "));
        addDeadlineCommand = new AddDeadlineCommand(secondWords);
        addDeadlineCommand.execute(taskList, ui, storage);

        assertTrue(taskList.getTasks().size() == 2);
        ReminderCommand reminderCommand = new ReminderCommand();
    }
}
