import duke.Parser;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.exceptions.InputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TimeConflictTest {

    @Test
    void testIfTwoEventsOverlap_TestOne() throws DukeException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("data/dukeTest.txt");

        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        Command addEventOne = new Parser().parse("event eventOne /at 09/19/2019 14:00 to 09/19/2019 17:00");
        addEventOne.execute(tasks, storage, ui);
        Command addEventTwo = new Parser().parse("event eventTwo /at 09/19/2019 15:00 to 09/19/2019 17:00");
        try {
            addEventTwo.execute(tasks, storage, ui);
        } catch (InputException e) {
            assertEquals(e.getMessage(),
                    "Invalid Input\n\n"
                    + "Time conflicting with:\n"
                    + "    " + "1.[E][✗] eventOne (at: 09/19/2019 14:00 to 09/19/2019 17:00)\n"
                    + "Please choose another time interval.");
        }
    }

    @Test
    void testIfTwoEventsOverlap_TestTwo() throws DukeException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("data/dukeTest.txt");

        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        Command addEventOne = new Parser().parse("event eventOne /at 09/19/2019 14:00 to 09/19/2019 17:00");
        addEventOne.execute(tasks, storage, ui);
        Command addEventTwo = new Parser().parse("event eventTwo /at 09/19/2019 13:00 to 09/19/2019 18:00");
        try {
            addEventTwo.execute(tasks, storage, ui);
        } catch (InputException e) {
            assertEquals(e.getMessage(),
                    "Invalid Input\n\n"
                            + "Time conflicting with:\n"
                            + "    " + "1.[E][✗] eventOne (at: 09/19/2019 14:00 to 09/19/2019 17:00)\n"
                            + "Please choose another time interval.");
        }
    }

    @Test
    void testIfTwoEventsOverlap_TestThree() throws DukeException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("data/dukeTest.txt");

        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        Command addEventOne = new Parser().parse("event eventOne /at 09/19/2019 14:00 to 09/19/2019 17:00");
        addEventOne.execute(tasks, storage, ui);
        Command addEventTwo = new Parser().parse("event eventTwo /at 09/19/2019 14:00 to 09/19/2019 15:00");
        try {
            addEventTwo.execute(tasks, storage, ui);
        } catch (InputException e) {
            assertEquals(e.getMessage(),
                    "Invalid Input\n\n"
                            + "Time conflicting with:\n"
                            + "    " + "1.[E][✗] eventOne (at: 09/19/2019 14:00 to 09/19/2019 17:00)\n"
                            + "Please choose another time interval.");
        }
    }

    @Test
    void testIfTwoEventsOverlap_TestFour() throws DukeException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("data/dukeTest.txt");

        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        Command addEventOne = new Parser().parse("event eventOne /at 09/19/2019 14:00 to 09/19/2019 17:00");
        addEventOne.execute(tasks, storage, ui);
        Command addEventTwo = new Parser().parse("event eventTwo /at 09/19/2019 18:00 to 09/19/2019 19:00");
        assertEquals(addEventTwo.execute(tasks, storage, ui),
                "____________________________________________________________\n"
                + "Got it. I've added this event:\n"
                + "[E][✗] eventTwo (at: 09/19/2019 18:00 to 09/19/2019 19:00)\n"
                + "You currently have 2 tasks in the list.\n"
                + "____________________________________________________________");
    }

}
