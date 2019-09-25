import Events.EventTypes.Deadline;
import Events.EventTypes.Event;
import Events.EventTypes.Task;
import Events.EventTypes.ToDo;
import Events.Formatting.DateObj;
import Events.Formatting.Predicate;
import Events.Storage.TaskList;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {
	
	private static final int EQUAL = 0;
	private static final int GREATER_THAN = 1;
	private static final int SMALLER_THAN = 2;
	
    private static final int DATE = 0;
    private static final int TYPE = 1;
	
    @Test
    public void clashTest(){
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "[T][✘] hello";
        readFromFile.add(fileContent);
        fileContent = "[T][✓] gpaweijgpweijg";
        readFromFile.add(fileContent);
        fileContent = "[E][✘] apfiejpawi (at: 2/12/2019 1800)";
        readFromFile.add(fileContent);

        TaskList tasksTest = new TaskList(readFromFile);
        Task testTask = new Event("hello", "2/12/2019 1800");
        assertEquals(false, tasksTest.addTask(testTask));
    }

    @Test
    public void viewScheduleTest() {
        ArrayList<String> testListString = new ArrayList<>();
        TaskList testList = new TaskList(testListString);
        Task toDoTest = new ToDo("cheese");
        testList.addTask(toDoTest);
        Task deadlineTest1 = new Deadline("eat cheese", "19/09/2019 1900");
        testList.addTask(deadlineTest1);
        Task deadlineTest2 = new Deadline("buy cheese", "19/09/2019 2000");
        testList.addTask(deadlineTest2);
        Task deadlineTest3 = new Deadline("throw cheese", "19/09/2020 1000");
        testList.addTask(deadlineTest3);
        Task eventTest = new Event("cheese party", "20/09/2019 2100");
        testList.addTask(eventTest);
        String dateToView = "19/09/2019";
        String foundTask = "";
        int viewIndex = 1;
        DateObj findDate = new DateObj(dateToView);
        for (Task testViewTask : testList.getTaskArrayList()) {
            if (testViewTask.toString().contains(findDate.toOutputString())) {
                foundTask += viewIndex + ". " + testViewTask.toString() + "\n";
                viewIndex++;
            }
        }
        boolean isTasksFound = !foundTask.isEmpty();
        assertEquals(true, isTasksFound);
    }

    @Test
    public void addRecurringEventTest() {
        ArrayList<String> taskListString = new ArrayList<>();
        TaskList testList = new TaskList(taskListString);
        testList.addRecurringEvent(new Event("recurring event", "12/08/2019"), 100);
        testList.addRecurringEvent(new Event("Recurring event", "12/09/2019 2359"), 80);
        Event expectedEvent1 = new Event("recurring event", "12/08/2019");
        Event expectedEvent2 = new Event("recurring event", "20/11/2019");
        Event expectedEvent3 = new Event("Recurring event", "12/09/2019 2359");
        Event expectedEvent4 = new Event("Recurring event", "01/12/2019 2359");
        int taskFound = 0;
        for (Task testViewTask : testList.getTaskArrayList()) {
            if (testViewTask.toString().equals(expectedEvent1.toString()) ||
                    testViewTask.toString().equals(expectedEvent2.toString()) ||
                    testViewTask.toString().equals(expectedEvent3.toString()) ||
                    testViewTask.toString().equals(expectedEvent4.toString())) {
                taskFound++;
            }
        }
        assertEquals(4, taskFound);
    }

    @Test
    public void checkFreeDaysTest() {
        ArrayList<String> taskListString = new ArrayList<>();
        TaskList testList = new TaskList(taskListString);
        Task toDoTest = new ToDo("B-extensions");
        testList.addTask(toDoTest);
        Task deadlineTest1 = new Deadline("finish extension", "21/09/2019 1900");
        testList.addTask(deadlineTest1);
        Task deadlineTest2 = new Deadline("submit report", "22/09/2019 2000");
        testList.addTask(deadlineTest2);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        DateObj today = new DateObj(f.format(new Date()));
        Queue<String> daysFree = new LinkedList<String>();
        int nextDays = 1;
        while (daysFree.size() <= 3) {
            boolean flagFree = true;
            for (Task viewTask : testList.getTaskArrayList()) {
                if (viewTask.toString().contains(today.toOutputString())) {
                    flagFree = false;
                    break;
                }
            }
            if (flagFree) {
                daysFree.add(today.toOutputString());
            }
            today.addDays(nextDays);
        }
        boolean checkFreeFlag = false;
        if (daysFree.poll().equals("19 SEP 2019")) {
            checkFreeFlag = true;
        }
        assertEquals(true, checkFreeFlag);
    }
    
    @Test
    public void reminderTest () {
    	
    	ArrayList<String> testcase = new ArrayList<String>();
    	ArrayList<String> all = new ArrayList<String>();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
    	
    	// case 1: task due long ago (printed)
    	Task dueLongAgo = new Deadline("longAgo", "09/08/1965 0000");
    	all.add(dueLongAgo.toString());
    	testcase.add(dueLongAgo.toString());
    	
    	// case 2: task due now (printed)
    	Date now = new Date();
    	Calendar c = Calendar.getInstance();
    	c.setTime(now);
    	String nowStr = formatter.format(now);
    	Task dueNow = new Deadline("now", nowStr);
    	all.add(dueNow.toString());
    	testcase.add(dueNow.toString());
    	
    	// case 3: task due 2 days later (printed)
    	c.add(Calendar.DATE, 2);
    	Date twoDays = c.getTime();
    	String twoDaysStr = formatter.format(twoDays);
    	Task dueTwoDays = new Deadline("twoDays", twoDaysStr);
    	all.add(dueTwoDays.toString());
    	testcase.add(dueTwoDays.toString());
    	
    	// case 4: task due 3 days later (printed)
    	c.add(Calendar.DATE, 1);
    	Date threeDays = c.getTime();
    	String threeDaysStr = formatter.format(threeDays);
    	Task dueThreeDays = new Deadline("threeDays", threeDaysStr);
    	all.add(dueThreeDays.toString());
    	testcase.add(dueThreeDays.toString());
    	
    	// case 5: task due 4 days later (not printed)
    	c.add(Calendar.DATE, 1);
    	Date fourDays = c.getTime();
    	String fourDaysStr = formatter.format(fourDays);
    	Task dueFourDays = new Deadline("fourDays", fourDaysStr);
    	all.add(dueFourDays.toString());
    	
    	// case 6: task due 10 days later (not printed)
    	c.add(Calendar.DATE, 6);
    	Date tenDays = c.getTime();
    	String tenDaysStr = formatter.format(tenDays);
    	Task dueTenDays = new Deadline("tenDays", tenDaysStr);
    	all.add(dueTenDays.toString());
    	
    	TaskList expected = new TaskList(testcase);
    	TaskList allitms = new TaskList(all);
    	
    	DateObj limit = new DateObj();
    	limit.addDays(4);
    	limit.setMidnight();
    	Predicate<Object> pred = new Predicate<>(limit, GREATER_THAN);
    	String cmp = expected.listOfTasks_String();
    	String result = allitms.filteredlist(pred, DATE);
    	
    	assertEquals(cmp, result);
    }
}
