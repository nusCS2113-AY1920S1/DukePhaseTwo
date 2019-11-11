package compal.logic.command;

import compal.commons.CompalUtils;
import compal.commons.LogUtils;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import compal.ui.CalendarUtil;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

//@@author SholihinK

/**
 * View the task in day,week or month format.
 */
public class ViewCommand extends Command {

    public static final String MESSAGE_USAGE = "view\n\t"
        + "Format: view day|week|month [/date DATE] "
        + "[/type TASK_TYPE]\n\n\t"
        + "Note: content in \\\"[]\\\": optional\n\t"
        + "You can switch the order of any two blocks (a block starts with \"/\" and ends by the next block)\n\t"
        + "content separated by \"|\": must choose exactly one from them\n\t"
        + "TASK_TYPE should be either \"deadline\" or \"event\"\n\t"
        + "DATE: dd/mm/yyyy is the date format. e.g. 01/01/2000\n\n"
        + "This command will view the timetable in a daily/weekly/monthly view\n"
        + "Examples:\n\t"
        + "view day|week|month\n\t\t"
        + "show the timetable of today and the list containing all tasks today|this week|this month\n\t"
        + "view week /date 01/01/2019\n\t\t"
        + "show the list containing all tasks on the week of 01/01/2019\n\t"
        + "view day /date 01/01/2019 /type deadline:\n\t\t"
        + "show the list containing all deadline type tasks on 01/01/2019";

    private CalendarUtil calenderUtil;
    private String viewType;
    private String dateInput;
    private String type;
    private static final Logger logger = LogUtils.getLogger(ViewCommand.class);

    /**
     * Generate constructor for viewCommand.
     *
     * @param viewType  the view Type
     * @param dateInput the date of input
     */
    public ViewCommand(String viewType, String dateInput) {
        this.viewType = viewType;
        this.dateInput = dateInput;
        this.type = "";
        calenderUtil = new CalendarUtil();
    }

    /**
     * override.
     *
     * @param typeToShow the type to be display only
     */
    public ViewCommand(String viewType, String dateInput, String typeToShow) {
        this.viewType = viewType;
        this.dateInput = dateInput;


        if ("deadline".equals(typeToShow)) {
            this.type = "D";
        } else if ("event".equals(typeToShow)) {
            this.type = "E";
        }


        calenderUtil = new CalendarUtil();
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) {
        logger.info("Executing view command");
        ArrayList<Task> currList = taskList.getArrList();


        String[] dateParts = dateInput.split("/");

        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        String finalList = "";


        switch (viewType) {
        case "month":
            finalList = displayMonthView(month, year, currList);
            break;
        case "week":
            finalList = displayWeekView(dateInput, currList);
            break;
        case "day":
            finalList = finalList + ("Your daily schedule for " + dateInput + " :\n");
            finalList = finalList + displayDayView(dateInput, currList);
            calenderUtil.dateViewRefresh(dateInput, type);
            break;
        default:
            break;
        }
        return new CommandResult(finalList, false);
    }


    /**
     * return all task for a given month.
     *
     * @param givenMonth the month input by user.
     * @param givenYear  the year input by user.
     * @param currList   the curr taskList of task.
     * @return stringo output
     */
    private String displayMonthView(int givenMonth, int givenYear, ArrayList<Task> currList) {
        String[] months = {"", "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

        YearMonth yearMonthObject = YearMonth.of(givenYear, givenMonth);
        int days = yearMonthObject.lengthOfMonth(); //28

        StringBuilder monthlyTask = new StringBuilder("Your monthly schedule for "
            + months[givenMonth] + " " + givenYear + " :\n");

        String tempDate;
        String tempMonth;
        String tempYear;
        for (int i = 1; i <= days; i++) {
            if (i <= 9) {
                tempDate = "0" + i;
            } else {
                tempDate = Integer.toString(i);
            }

            if (givenMonth <= 9) {
                tempMonth = "0" + givenMonth;
            } else {
                tempMonth = Integer.toString(givenMonth);
            }

            tempYear = Integer.toString(givenYear);
            monthlyTask.append(displayDayView(tempDate + "/" + tempMonth + "/" + tempYear, currList));
        }
        return monthlyTask.toString();
    }

    /**
     * return all task for a given week.
     *
     * @param dateInput the date of task input.
     * @param currList  the curr taskList of task.
     * @return string output
     */
    private String displayWeekView(String dateInput, ArrayList<Task> currList) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();
        cal.setTime(CompalUtils.stringToDate(dateInput));

        int daysInWeek = 7;
        String[] dates = new String[daysInWeek];
        StringBuilder[] dailyTask = new StringBuilder[daysInWeek];

        for (int i = 0; i < daysInWeek; i++) {
            dates[i] = dateFormat.format(cal.getTime());//Date of Monday of current week
            //calenderUtil.dateViewRefresh(dates[i]);
            dailyTask[i] = new StringBuilder();
            cal.add(Calendar.DATE, 1);
        }

        StringBuilder weeklyTask = new StringBuilder("Your weekly schedule from "
            + dates[0] + " to " + dates[6] + " :\n");

        for (int i = 0; i < daysInWeek; i++) {
            dailyTask[i].append(displayDayView(dates[i], currList));
            weeklyTask.append(dailyTask[i]);

        }
        return weeklyTask.toString();
    }

    /**
     * return all task for a given day.
     *
     * @param dateInput the date of task input.
     * @param currList  the curr taskList of task.
     * @return string output
     */
    private String displayDayView(String dateInput, ArrayList<Task> currList) {

        StringBuilder allTask = new StringBuilder();

        for (Task t : currList) {
            if (!"".equals(type) && !t.getSymbol().equals(type)) {
                continue;
            }

            if (t.getStringMainDate().equals(dateInput)) {
                allTask.append(getAsStringView(t));
            } else if (!t.getStringTrailingDate().equals("-") && t.getStringTrailingDate().equals(dateInput)) {
                allTask.append(getAsStringView(t));
            }
        }

        if (allTask.length() == 0) {
            allTask.append("\n\n");
        }

        Date givenDate = CompalUtils.stringToDate(dateInput);
        String dayOfWeek = new SimpleDateFormat("EE").format(givenDate);


        String header = "\n" + "_".repeat(65) + "\n"
            + " ".repeat((92)) + dayOfWeek + "," + dateInput + "\n";
        return header + allTask.toString();

    }

    private String getAsStringView(Task t) {


        StringBuilder taskDetails = new StringBuilder();

        String rightArrow = "\u2192";

        boolean isDone = t.getisDone();
        String status;
        if (isDone) {
            status = "\u2713";
        } else {
            status = "\u274C";
        }

        String startTime = "-";
        String endTime = "-";

        if (t.getSymbol().equals("E")) {
            if (t.getStringMainDate().equals(t.getStringTrailingDate())) {
                startTime = t.getStringStartTime();
                endTime = t.getStringEndTime();
            } else {
                startTime = t.getStringStartTime();
                endTime = "2359";
            }
        } else if (t.getSymbol().equals("D")) {
            endTime = t.getStringEndTime();
        }

        if ("-".equals(startTime)) {
            taskDetails.append("  Due: ").append(endTime)
                .append("\n");

        } else {
            taskDetails.append("  Time: ").append(startTime)
                .append(" ").append(rightArrow)
                .append(" ").append(endTime)
                .append("\n");
        }

        int taskId = t.getId();
        Task.Priority priority = t.getPriority();

        taskDetails
            .append("  [Task ID:").append(taskId).append("] ")
            .append("[Priority:").append(priority).append("]\n");

        String taskSymbol = t.getSymbol();

        String taskDescription = t.getDescription();
        taskDetails.append("  [").append(taskSymbol).append("] ")
            .append("[").append(status).append("] ")
            .append(taskDescription).append("\n\n");

        return taskDetails.toString();
    }

}