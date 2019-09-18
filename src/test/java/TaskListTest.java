import duke.exception.DukeException;
import duke.task.DeadlineTask;
import duke.task.EventTask;
import duke.task.Reminder;
import duke.task.TaskList;
import duke.task.TimedTask;
import duke.task.ToDoTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskListTest {
    private TaskList taskList;

    /**
     * Adds tasks to an empty TaskList. This is executed before each JUnit test.
     */
    @BeforeEach
    public void addTasks_validTasks_successMessageReturned() {
        taskList = new TaskList();
        ToDoTask todo = new ToDoTask("JUnit tests");
        LocalDateTime t = LocalDateTime.parse("12/09/2019 1400", TimedTask.getPatDatetime());
        EventTask event = new EventTask("tutorial", t, t);
        DeadlineTask deadline = new DeadlineTask("submission", t);

        try {
            assertTrue(taskList.getAddReport(taskList.addTask(todo), 1).contains("1 task"));
            assertTrue(taskList.getAddReport(taskList.addTask(event), 1).contains("2 tasks"));
            assertTrue(taskList.getAddReport(taskList.addTask(deadline), 1).contains("3 tasks"));
        } catch (AssertionError excp) {
            fail("Total number of tasks added is not 3!");
        }

        try {
            String expectedTaskListStr = System.lineSeparator() + "1.[T][N] JUnit tests"
                    + System.lineSeparator() + "2.[E][N] tutorial (at: "
                    + "Thu, 12 Sep 2019 2:00 PM - Thu, 12 Sep 2019 2:00 PM)"
                    + System.lineSeparator() + "3.[D][N] submission (by: Thu, 12 Sep 2019 2:00 PM)";
            String listStr = taskList.listTasks();
            assertEquals(expectedTaskListStr, taskList.listTasks());
        } catch (DukeException excp) {
            fail("No tasks in the list after adding!");
        } catch (AssertionError excp) {
            fail("Tasks not listed correctly!");
        }
    }

    @Test
    public void deleteTasks_validIdx_successMessageReturned() {
        try {
            taskList.deleteTask("1");
            taskList.deleteTask("2");
            assertEquals(System.lineSeparator() + "1.[E][N] tutorial (at: "
                            + "Thu, 12 Sep 2019 2:00 PM - Thu, 12 Sep 2019 2:00 PM)",
                    taskList.listTasks());
        } catch (DukeException excp) {
            fail("Unable to find added tasks!");
        } catch (AssertionError excp) {
            fail("Tasks not deleted correctly!");
        }
    }

    @Test //also tests for failure of other "getIdx" tasks
    public void deleteTasks_invalidIdx_exceptionThrownAndListNotChanged() {
        assertThrows(DukeException.class, () -> {
            taskList.deleteTask("100");
        });
        String expectedTaskListStr = System.lineSeparator() + "1.[T][N] JUnit tests"
                + System.lineSeparator() + "2.[E][N] tutorial (at: "
                + "Thu, 12 Sep 2019 2:00 PM - Thu, 12 Sep 2019 2:00 PM)"
                + System.lineSeparator() + "3.[D][N] submission (by: Thu, 12 Sep 2019 2:00 PM)";
        try {
            assertEquals(expectedTaskListStr, taskList.listTasks());
        } catch (DukeException excp) {
            fail("Task list empty after invalid deletion!");
        } catch (AssertionError excp) {
            fail("Task list modified by invalid deletion!");
        }
    }

    @Test
    public void findTasks_matchingTasks_matchingTasksReturned() {
        String expectedSearchResult = System.lineSeparator() + "1.[E][N] tutorial (at: "
                + "Thu, 12 Sep 2019 2:00 PM - Thu, 12 Sep 2019 2:00 PM)"
                + System.lineSeparator() + "2.[D][N] submission (by: Thu, 12 Sep 2019 2:00 PM)";
        try {
            assertEquals(expectedSearchResult, taskList.find("u"));
        } catch (DukeException excp) {
            fail("Failed to find tasks!");
        }
    }

    @Test
    public void findTasks_noMatchingTasks_exceptionThrown() {
        assertThrows(DukeException.class, () -> {
            taskList.find("CS2113");
        });
    }

    @Test
    public void markDone_validIdx_tasksMarkedDone() {
        try {
            taskList.markDone("1");
            taskList.markDone("2");
            taskList.markDone("3");
        } catch (DukeException excp) {
            fail("Not able to find tasks to mark as done!");
        }

        try {
            String expectedTaskListStr = System.lineSeparator() + "1.[T][Y] JUnit tests"
                    + System.lineSeparator() + "2.[E][Y] tutorial (at: "
                    + "Thu, 12 Sep 2019 2:00 PM - Thu, 12 Sep 2019 2:00 PM)"
                    + System.lineSeparator() + "3.[D][Y] submission (by: Thu, 12 Sep 2019 2:00 PM)";
            assertEquals(expectedTaskListStr, taskList.listTasks());
        } catch (DukeException excp) {
            fail("Marking tasks as done somehow deleted all tasks in the list!");
        } catch (AssertionError excp) {
            fail("Tasks not correctly marked as done!");
        }
    }

    /**
     * Compares the output returned by setReminder() with the correct output.
     * Expect them to be equal if validIdx is given.
     */
    @Test
    public void setReminder_validIdx_successMessageReturned() {
        try {
            LocalDateTime datetime = LocalDateTime.parse("18/09/2019 0200", TimedTask.getPatDatetime());
            taskList.setReminder("1", new Reminder(datetime));
            assertEquals(System.lineSeparator() + "1.[T][N][R: Wed, 18 Sep 2019 2:00 AM] JUnit tests"
                            + System.lineSeparator() + "2.[E][N] tutorial (at: "
                            + "Thu, 12 Sep 2019 2:00 PM - Thu, 12 Sep 2019 2:00 PM)"
                            + System.lineSeparator() + "3.[D][N] submission (by: Thu, 12 Sep 2019 2:00 PM)",
                    taskList.listTasks());

            datetime = LocalDateTime.parse("18/09/2019 0300", TimedTask.getPatDatetime());
            taskList.setReminder("1", new Reminder(datetime));
            assertEquals(System.lineSeparator() + "1.[T][N][R: Wed, 18 Sep 2019 3:00 AM] JUnit tests"
                            + System.lineSeparator() + "2.[E][N] tutorial (at: "
                            + "Thu, 12 Sep 2019 2:00 PM - Thu, 12 Sep 2019 2:00 PM)"
                            + System.lineSeparator() + "3.[D][N] submission (by: Thu, 12 Sep 2019 2:00 PM)",
                    taskList.listTasks());

            taskList.setReminder("3", new Reminder(datetime));
            assertEquals(System.lineSeparator() + "1.[T][N][R: Wed, 18 Sep 2019 3:00 AM] JUnit tests"
                    + System.lineSeparator() + "2.[E][N] tutorial (at: "
                    + "Thu, 12 Sep 2019 2:00 PM - Thu, 12 Sep 2019 2:00 PM)"
                    + System.lineSeparator() + "3.[D][N][R: Wed, 18 Sep 2019 3:00 AM] "
                    + "submission (by: Thu, 12 Sep 2019 2:00 PM)", taskList.listTasks());
        } catch (DukeException excp) {
            fail("Unable to find added tasks!");
        } catch (AssertionError excp) {
            fail("Reminder not set correctly!");
        }
    }

    //code for debugging test outputs
    /*for (int i = 0; i < <expected>.length(); ++i) {
        if (i >= <actual>.length()) {
            break;
        }
        char achar = <actual>.charAt(i);
        char echar = <expected>.charAt(i);
        if (achar != echar) {
            System.out.println("Found " + achar + ", expected " + echar);
        }
    }*/
}
