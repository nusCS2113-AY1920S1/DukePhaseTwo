package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void testStringConversion() {
        assertEquals("[T][✘] eat later", (new ToDo("eat later", "T")).toMessage());
    }
}
