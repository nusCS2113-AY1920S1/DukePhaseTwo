//import org.testng.annotations.Test;
import Tasks.Event;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void stringConversionTest() {
        String w1 = "CS2101 [E][\u2718][NR][<R/R>] Test Event (at: Wed 04/12/2019 time: 07:00 AM to 11:00 AM)";
        String w2 = new Event("CS2101 Test Event", "Wed 04/12/2019", "07:00 AM", "11:00 AM").toString();
        assertEquals(w1,w2);
    }
}
