package task;

import org.junit.jupiter.api.Test;
import ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

public class EventTest {

    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void testStringConversion() {
        Event testEvent = new Event("do the homework",
                "02/05/2019 1800",
                "02/05/2019 1900",
                new TaskList());
        assertEquals("[E][\u2718] do the homework (at: 02/05/2019 1800 - 02/05/2019 1900)",
                testEvent.toString());

        testEvent.setDoAfterDate("01/05/2019 1800");
        assertEquals("[E][\u2718] do the homework "
                        + "(after: 01/05/2019 1800) "
                        + "(at: 02/05/2019 1800 - 02/05/2019 1900)",
                testEvent.toString());

        testEvent.markAsDone();
        assertEquals("[E][\u2713] do the homework "
                        + "(after: 01/05/2019 1800) "
                        + "(at: 02/05/2019 1800 - 02/05/2019 1900)",
                testEvent.toString());
    }

    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void testStorageStringConversion() {

        assertEquals("E | 0 | do the homework | null | 02/05/2019 1800 | 02/05/2019 1900",
                new Event("do the homework",
                        "02/05/2019 1800",
                        "02/05/2019 1900",
                        new TaskList())
                        .toStorageString());

        Event testEvent = new Event("do the homework",
                "02/05/2019 1800",
                "02/05/2019 1900",
                new TaskList());
        assertEquals("E | 0 | do the homework | null | 02/05/2019 1800 | 02/05/2019 1900",
                testEvent.toStorageString());

        testEvent.setDoAfterDate("01/05/2019 1800");
        assertEquals("E | 0 | do the homework | 01/05/2019 1800 | 02/05/2019 1800 | 02/05/2019 1900",
                testEvent.toStorageString());

        testEvent.markAsDone();
        assertEquals("E | 1 | do the homework | 01/05/2019 1800 | 02/05/2019 1800 | 02/05/2019 1900",
                testEvent.toStorageString());

    }

    @Test
    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:LineLength"})
    public void toString_noHourInfo_exceptionThrown() {
        try {
            assertEquals("[E][\u2718] do the homework (at: Thu May 02 18:00:00 SGT 2019 - Thu May 02 19:00:00 SGT 2019)",
                    new Event("do the homework",
                            "02/05/2019",
                            "02/05/2019",
                            new TaskList())
                            .toString());
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! Incorrect time format.", e.getMessage());
        }
    }

    @Test
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public void testOverlappingTime() {
        TaskList taskList = new TaskList();
        Event testEvent = new Event("do the homework",
                "02/05/2019 1800",
                "02/05/2019 1900",
                taskList);
        taskList.addTask(testEvent);
        Event conflictingEvent = new Event("do something else",
                "02/05/2019 1830",
                "02/05/2019 1930",
                taskList);
        TaskList overlappedTasks = conflictingEvent.overlappingWithOtherEvents(taskList);
        assertEquals(taskList.getTasks(), overlappedTasks.getTasks());
    }
}
