package factorytests;

import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;
import util.date.DateTimeHelper;
import util.factories.TaskFactory;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskFactoryTests {
    private TaskFactory taskFactory = new TaskFactory();
    private DateTimeHelper dateTimeHelper = new DateTimeHelper();
    private String simulatedFactoryInput;

    /**
     * Always true test just to test if Junit is working.
     */
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void taskCreation_fullCorrectInputs_creationSuccess() {
        simulatedFactoryInput = "t/Killing Thanos p/1 d/28/09/2019 c/100 s/done r/Use Iron Man";
        try {
            Task simulatedTask = taskFactory.createTask(simulatedFactoryInput);
            ArrayList<String> expectedRequirements = new ArrayList<>();
            expectedRequirements.add("Use Iron Man");
            Task expectedTask = new Task("Killing Thanos", 1,
                    dateTimeHelper.formatDate("28/09/2019"), 100, TaskState.DONE, expectedRequirements);
            assertEquals(expectedTask.getDetails(), simulatedTask.getDetails());
            assertEquals(expectedTask.getTaskName(),simulatedTask.getTaskName());
            assertEquals(expectedTask.getTaskCredit(), simulatedTask.getTaskCredit());
            assertEquals(expectedTask.getTaskPriority(), simulatedTask.getTaskPriority());
        } catch (ParseException err) {
            fail("ParseException was detected when it shouldn't have");
        }
    }

    @Test
    void taskCreation_correctPartialInputs_creationSuccess() {
        simulatedFactoryInput = "t/Turning back time p/5 c/5";
        try {
            Task simulatedTask = taskFactory.createTask(simulatedFactoryInput);
            Task expectedTask = new Task("Turning back time", 5,
                    null, 5, TaskState.OPEN, null);
            assertEquals(expectedTask.getDetails(), simulatedTask.getDetails());
            assertEquals(expectedTask.getTaskName(),simulatedTask.getTaskName());
            assertEquals(expectedTask.getTaskCredit(), simulatedTask.getTaskCredit());
            assertEquals(expectedTask.getTaskPriority(), simulatedTask.getTaskPriority());
        } catch (ParseException err) {
            fail("ParseException was detected when it shouldn't have");
        }
    }

    @Test
    void taskCreation_wrongInputs_creationFailed() {
        simulatedFactoryInput = "t/Missing priority c/5";
        try {
            Task simulatedTask = taskFactory.createTask(simulatedFactoryInput);
            System.out.println(simulatedTask);
        } catch (ParseException e) {
            fail("ParseException was detected when it shouldn't have");
        }
    }
}
