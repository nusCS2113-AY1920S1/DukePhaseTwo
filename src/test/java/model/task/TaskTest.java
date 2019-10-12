package model.task;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.task.Deadline;
import duchess.model.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    private final Task deadline;
    private final Module module;

    /**
     * Instantiate testing class with default deadline and module.
     */
    public TaskTest() throws DuchessException {
        deadline = new Deadline(
                List.of("return book /by 12/12/2020 1200".split(" "))
        );
        module = new Module("CS2113T", "Easy game");
    }

    @Test
    public void getModule_getsModule() {
        deadline.setModule(module);
        assertEquals(
                Optional.of(module),
                deadline.getModule()
        );
        deadline.setModule(null);
        assertEquals(
                Optional.empty(),
                deadline.getModule()
        );
    }

    @Test
    public void compareTo_twoDeadlines_increasingOrder() throws DuchessException {
        Task other = new Deadline(
                List.of("return book /by 12/12/2020 1400".split(" "))
        );
        assertTrue(
                deadline.compareTo(other) < 0
        );
    }

    @Test
    public void setDone_setsDone() {
        deadline.setDone(false);
        assertFalse(deadline.isDone());
        deadline.setDone(true);
        assertTrue(deadline.isDone());
    }

    @Test
    public void setDescription_setsDescription() {
        deadline.setDescription("Test");
        assertEquals(deadline.getDescription(), "Test");
    }

    @Test
    public void toString_includesModuleName() {
        deadline.setModule(module);
        assertTrue(
                deadline.toString().contains("CS2113T")
        );
        deadline.setModule(null);
        assertFalse(
                deadline.toString().contains("CS2113T")
        );
    }

    @Test
    public void toString_includesTick() {
        deadline.setDone(true);
        assertEquals(
                deadline.toString(),
                "[D][✓] return book (by: 12/12/2020 1200)"
        );
        deadline.setDone(false);
        assertEquals(
                deadline.toString(),
                "[D][✘] return book (by: 12/12/2020 1200)"
        );
    }
}
