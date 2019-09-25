package seedu.duke.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Display reminders list when the app first start
 * and user can type command "remind" to bring up reminders after that.
 */
public class Reminders {

    private static ArrayList<Task> overDueList = new ArrayList<>();
    private static ArrayList<Task> lastDayList = new ArrayList<>();
    private static ArrayList<Task> lastThirtyMinutesList = new ArrayList<>();
    private static final int TWENTY_FOUR_HOURS = 86400;
    private static final int THIRTY_MINUTES = 1800;

    /**
     * List of overdue tasks.
     * @param list current TaskList.
     * @return a list of overdue tasks.
     */
    public static ArrayList<Task> overdue(TaskList list) {
        overDueList.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean done = list.get(i).isDone;
            boolean checkDeadline = list.get(i).toString().contains("[D]");
            boolean checkEvent = list.get(i).toString().contains("[E]");
            boolean checkRange = list.get(i).toString().contains("[R]");
            if (!done && checkDeadline || checkEvent || checkRange) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= 0) {
                    overDueList.add(list.get(i));
                }
            }
        }
        return overDueList;
    }

    /**
     * List of tasks that are due in 24 hours or less.
     * @param list current TaskList.
     * @return a list of tasks that are due in 24 hours or less.
     */
    public static ArrayList<Task> lastDay(TaskList list) {
        lastDayList.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean done = list.get(i).isDone;
            boolean checkDeadline = list.get(i).toString().contains("[D]");
            boolean checkEvent = list.get(i).toString().contains("[E]");
            boolean checkRange = list.get(i).toString().contains("[R]");
            if (!done && checkDeadline || checkEvent || checkRange) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= TWENTY_FOUR_HOURS && duration > THIRTY_MINUTES) {
                    lastDayList.add(list.get(i));
                }
            }
        }
        return lastDayList;
    }

    /**
     * List of tasks that are due in 30 minutes or less.
     * @param list current TaskList.
     * @return a list of tasks that are due in 30 minutes or less.
     */
    public static ArrayList<Task> lastThirtyMins(TaskList list) {
        lastThirtyMinutesList.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean done = list.get(i).isDone;
            boolean checkDeadline = list.get(i).toString().contains("[D]");
            boolean checkEvent = list.get(i).toString().contains("[E]");
            boolean checkRange = list.get(i).toString().contains("[R]");
            if (!done && checkDeadline || checkEvent || checkRange) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= THIRTY_MINUTES && duration > 0) {
                    lastThirtyMinutesList.add(list.get(i));
                }
            }
        }
        return lastThirtyMinutesList;
    }

    /**
     * Runs all types of reminders list at once.
     * @param list current TaskList.
     */
    public static void runAll(TaskList list) {
        overdue(list);
        lastThirtyMins(list);
        lastDay(list);
    }

    /**
     * Display reminders list.
     */
    public static void displayReminders() {
        if (!overDueList.isEmpty()) {
            if (overDueList.size() == 1) {
                System.out.println("The task below is overdue!!!");
                System.out.print("1.");
                for (Task i : overDueList) {
                    System.out.println(i);
                }
            } else {
                System.out.println("The tasks below are overdue!!!");
                for (int i = 0; i < overDueList.size(); i++) {
                    System.out.println(i + 1 + "." + overDueList.get(i));
                }
            }
        }
        if (!lastThirtyMinutesList.isEmpty()) {
            System.out.println();
            if (lastThirtyMinutesList.size() == 1) {
                System.out.println("The task below is due in 30 minutes or less!!!");
                System.out.print("1.");
                for (Task i : lastThirtyMinutesList) {
                    System.out.println(i);
                }
            } else {
                System.out.println("The tasks below are due in 30 minutes or less!!!");
                for (int i = 0; i < lastThirtyMinutesList.size(); i++) {
                    System.out.println(i + 1 + "." + lastThirtyMinutesList.get(i));
                }
            }
        }
        if (!lastDayList.isEmpty()) {
            System.out.println();
            if (lastDayList.size() == 1) {
                System.out.println("The task below is due in 24 hours or less!!!");
                System.out.print("1.");
                for (Task i : lastDayList) {
                    System.out.println(i);
                }
            } else {
                System.out.println("The tasks below are due in 24 hours or less!!!");
                for (int i = 0; i < lastDayList.size(); i++) {
                    System.out.println(i + 1 + "." + lastDayList.get(i));
                }
            }
        }
    }

    /**
     * For testing, check if task that are overdue exist in overDueList.
     * @return true if overDueList exist.
     */
    public static boolean exist() {
        boolean check = false;
        if (!overDueList.isEmpty()) {
            check = true;
        }
        return check;
    }
}



