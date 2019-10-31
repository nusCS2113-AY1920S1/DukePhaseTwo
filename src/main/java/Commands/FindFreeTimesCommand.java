package Commands;
import Commons.LookupTable;
import Commons.Storage;
import Commons.Ui;
import Tasks.Assignment;
import Tasks.TaskList;
import javafx.util.Pair;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FindFreeTimesCommand extends Command {
    private static final int HALF_HOUR_MARK = 30;
    private static final int HOUR_MARK = 60;
    private final SimpleDateFormat timeFormat12 = new SimpleDateFormat("hh:mm aa");
    private final SimpleDateFormat timeFormat24 = new SimpleDateFormat("HHmm");
    private final SimpleDateFormat dateDayFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private final SimpleDateFormat dateTimeFormat12 = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
    private final SimpleDateFormat dateTimeFormat24 = new SimpleDateFormat("E dd/MM/yyyy HHmm");

    private final Integer options = 5;
    private final Integer duration;
    private final ArrayList<Pair<Date, Date>> freeTimeData = new ArrayList<>();
    private final NavigableMap<String, ArrayList<Pair<String, String>>> dataMap = new TreeMap<>();
    private String message = new String();

    public FindFreeTimesCommand(Integer duration) {
        this.duration = duration;
    }

    /**
     * This method generates a increased a dateTime given by days or hours based on given duration and returns the new dateTime.
     * @param inDate The dateTime given
     * @param duration The duration given
     * @return The new dateTime after increasing the inDate
     */
    private Date increaseDateTime(Date inDate, int duration){
        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.HOUR, duration);
        return c.getTime();
    }

    /**
     * This method updates the dateTime to same date with time 2359 as upper Boundary.
     * @param inDate The date given
     * @return The updated date
     */
    private Date increaseToTwoThreeFiveNine(Date inDate){
        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.HOUR, 23);
        c.add(Calendar.MINUTE, 59);
        return c.getTime();
    }

    /**
     * This method updates the dateTime to same date with time 0700 as lower Boundary.
     * @param inDate The date given
     * @return The updated date
     */
    private Date increaseZeroSevenZeroZero(Date inDate){
        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.HOUR, 7);
        return c.getTime();
    }

    /**
     * This function rounds ups the time to the nearest half hour or hour mark.
     * @param date The date given to round up
     * @return The round up date
     */
    private Date roundByHalfHourMark(Date date) {
        long minuteToIncrease = 0;
        long diff = date.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;

        if(diffMinutes > HALF_HOUR_MARK) minuteToIncrease = HOUR_MARK - diffMinutes;
        else if(diffMinutes < HALF_HOUR_MARK) minuteToIncrease = HALF_HOUR_MARK - diffMinutes;
        Calendar c = Calendar.getInstance();
        c.setTime((date));
        c.add(Calendar.MINUTE, (int)minuteToIncrease);
        return c.getTime();
    }

    /**
     * This method generates a comparator to compare the key of two pairs of date based on 12 clock format.
     */
    private final Comparator<Pair<String, String>> compareByTime = (o1, o2) -> {
        String left = o1.getKey();
        String right = o2.getKey();

        String[] leftTimeSplit = left.split(" ");
        String[] rightTimeSplit = right.split(" ");

        if(leftTimeSplit[1].equals("AM") && rightTimeSplit[1].equals("AM")){
            String[]leftTimeSplitHourMinute = leftTimeSplit[0].split(":");
            String[]rightTimeSplitHourMinute = rightTimeSplit[0].split(":");
            if(leftTimeSplitHourMinute[0].equals("12") && rightTimeSplitHourMinute[0].equals("12")) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if(leftTimeSplitHourMinute[0].equals("12")) {
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals("12")) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        } else if (leftTimeSplit[1].equals("AM")) {
            return -1;
        } else if (rightTimeSplit[1].equals("AM")) {
            return 1;
        } else {
            return left.compareTo(right);
        }
    };

    /**
     * Executes the finding of earliest available block period inside the given TaskList objects with the given duration.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display freeTimes message
     * @throws Exception On date parsing error
     */
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        if(duration < 1 || duration > 16) return ui.showFreeTimesInvalidDuration(duration.toString());
        mapDataMap(events);
        findFindTime();
        setOutput();
        return ui.showFreeTimes(message);
    }

    /**
     * This method maps the list of events that is after current date and time into dataMap.
     * @param events The list of event tasks in storage
     * @throws ParseException The error when the data provided in invalid
     */
    private void mapDataMap(TaskList events) throws ParseException {
        Date currDate = new Date();
        String strCurrDateDay = dateDayFormat.format(currDate);
        String strCurrTime = timeFormat12.format(currDate);
        ArrayList<Pair<String, String>> temp = new ArrayList<>();
        temp.add(new Pair<>(strCurrTime, strCurrTime));
        dataMap.put(strCurrDateDay, temp);

        for(String module: events.getMap().keySet()) {
            HashMap<String, ArrayList<Assignment>> moduleValues = events.getMap().get(module);
            for (String strDate : moduleValues.keySet()) {
                Date date = dateDayFormat.parse(strDate);
                date = increaseToTwoThreeFiveNine(date);
                ArrayList<Pair<String, String>> timeArray = new ArrayList<>();
                if(strDate.equals(strCurrDateDay)) timeArray.add(new Pair<>(strCurrTime, strCurrTime));
                if(date.after(currDate)) {
                    ArrayList<Assignment> data = moduleValues.get(strDate);
                    for (Assignment task : data) {
                        String startAndEnd = task.getTime();
                        String[] spiltStartAndEnd = startAndEnd.split("to");
                        Date startDateTime = dateTimeFormat12.parse(strDate + " " + spiltStartAndEnd[0]);
                        if(startDateTime.after(currDate)) timeArray.add(new Pair<>(spiltStartAndEnd[0].trim(), spiltStartAndEnd[1].trim()));
                    }
                    if(dataMap.containsKey(strDate)) timeArray = mergeTimeArray(dataMap.get(strDate), timeArray);
                    timeArray.sort(compareByTime);
                    dataMap.put(strDate, timeArray);
                }
            }
        }
    }

    /**
     * This method merges to arrayList and removes duplicated values.
     * @param left The list of start and end times
     * @param right The list of start and end times
     * @return The combines list
     */
    private ArrayList<Pair<String, String>> mergeTimeArray(ArrayList<Pair<String, String>> left, ArrayList<Pair<String, String>> right) {
        for(Pair<String, String> c: right) {
            if(!left.contains(c)) left.add(c);
        }
        return left;
    }

    /**
     * This method returns true is command completed.
     * @return True if freeTimeData has 5 options
     */
    private boolean checkFreeTimeOptions () {
        if(freeTimeData.size() == options) return true;
        else return false;
    }

    /*
    This checks if there are 5 options generated after going through the data in the event list
     */
    private void generateFreeTime() throws ParseException {
        if (!checkFreeTimeOptions()) {
            Integer size = freeTimeData.size();
            Pair<Date, Date> last;
            if(size == 0) {
                Date currDate = new Date();
                String strCurrDateDay = dateDayFormat.format(currDate) + " 12:00 AM";
                currDate = dateTimeFormat12.parse(strCurrDateDay);
                currDate = increaseZeroSevenZeroZero(currDate);
                currDate = increaseDateTime(currDate, 24);
                last = new Pair<>(currDate, increaseDateTime(currDate, duration));
                freeTimeData.add(last);
                size = 1;
            }
            else {
                last = freeTimeData.get(size-1);
            }
            generateFreeTimeUntilFiveOptions(size, last);
        }
    }

    /**
     * This method extends generateFreeTime generates free time slot by an hour difference
     * @param size The size of the freeTimeData
     * @param last The last Pair found in freeTimeData
     * @throws ParseException The error when parsing data error is found
     */
    private void generateFreeTimeUntilFiveOptions(Integer size, Pair<Date, Date> last) throws ParseException {
        for(int i = size; i < options; i++){
            Date tempStart = last.getKey();
            Date tempEnd = last.getValue();
            String currDate = dateDayFormat.format(tempStart) + " 12:00 AM";
            Date dateBoundary = dateTimeFormat12.parse(currDate);
            Date dateUpperBoundary = increaseToTwoThreeFiveNine(dateBoundary);
            Date dateLowerBoundary = increaseZeroSevenZeroZero(dateBoundary);

            Pair<Date, Date> newFreeTime = null;
            Date dateTimeStart = increaseDateTime(tempStart, 1);
            Date dateTimeEnd = increaseDateTime(tempEnd, 1);

            if(dateTimeStart.after(dateLowerBoundary) && dateTimeEnd.before(dateUpperBoundary)) {
                newFreeTime = new Pair<>(dateTimeStart, dateTimeEnd);
            } else if(dateTimeStart.before(dateLowerBoundary) && dateTimeEnd.before(dateUpperBoundary)) {
                dateTimeStart = dateLowerBoundary;
                dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                if(dateTimeEnd.before(dateUpperBoundary)) newFreeTime = new Pair<>(dateTimeStart, dateTimeEnd);
            }
            else if (dateTimeEnd.after(dateUpperBoundary)){
                dateTimeStart = increaseDateTime(dateLowerBoundary, 24);
                dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                newFreeTime = new Pair<>(dateTimeStart, dateTimeEnd);
            }
            last = newFreeTime;
            freeTimeData.add(last);
        }
    }

    /**
     * This method Finds the best time available with the list of events in dataMap
     * @throws ParseException The error when the data provided in invalid
     */
    private void findFindTime() throws ParseException {
        for (String date: dataMap.keySet()) {
            ArrayList<Pair<String, String >> startAndEndTimes = dataMap.get(date);
            for(int i = 0; i < startAndEndTimes.size(); i++){
                Date dateBoundary = dateTimeFormat12.parse(date + " 12:00 AM");
                Date dateUpperBoundary = increaseToTwoThreeFiveNine(dateBoundary);
                Date dateLowerBoundary = increaseZeroSevenZeroZero(dateBoundary);

                if(i < startAndEndTimes.size() - 1) {
                    String dateTime = date + " " + startAndEndTimes.get(i).getValue();
                    String dateTimeNextEvent = date + " " + startAndEndTimes.get(i+1).getKey();

                    Date dateTimeStart = dateTimeFormat12.parse(dateTime);
                    dateTimeStart = roundByHalfHourMark(dateTimeStart);
                    Date dateNextEvent = dateTimeFormat12.parse(dateTimeNextEvent);
                    Date dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                    if(dateTimeEnd.after(dateUpperBoundary)) {
                        i = (startAndEndTimes.size() - 1);
                    } else if(dateTimeEnd.before(dateNextEvent)) {
                        if(dateTimeStart.before(dateLowerBoundary)) dateTimeStart = dateLowerBoundary;
                        dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                        if(dateTimeEnd.before(dateUpperBoundary)) freeTimeData.add(new Pair<>(dateTimeStart, dateTimeEnd));
                        else i = (startAndEndTimes.size() - 1);
                        if(checkFreeTimeOptions()) return;
                    }
                }
                else if(i == (startAndEndTimes.size() - 1)){
                    String dateTime = date + " " + startAndEndTimes.get(i).getValue();
                    Date dateTimeStart = dateTimeFormat12.parse(dateTime);
                    dateTimeStart = roundByHalfHourMark(dateTimeStart);

                    Date dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                    if(dateTimeStart.after(dateLowerBoundary) && dateTimeEnd.before(dateUpperBoundary)) {
                        freeTimeData.add(new Pair<>(dateTimeStart, dateTimeEnd));
                        if(checkFreeTimeOptions()) return;
                    } else if(dateTimeStart.before(dateLowerBoundary) && dateTimeEnd.before(dateUpperBoundary)) {
                        dateTimeStart = dateLowerBoundary;
                        dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                        if(dateTimeEnd.before(dateUpperBoundary)) freeTimeData.add(new Pair<>(dateTimeStart, dateTimeEnd));
                        if(checkFreeTimeOptions()) return;
                    } else {//dateTimeEnd.after(dateBoundary)
                        String nextKey = dataMap.higherKey(date);
                        if(nextKey == null) {
                            Date nextDay = dateTimeFormat12.parse(date + " 12:00 AM");
                            nextDay = increaseDateTime(nextDay, 24);
                            Date nextDayStartTime = increaseDateTime(nextDay, 7);
                            Date nextDayEndTime = increaseDateTime(nextDayStartTime, duration);
                            freeTimeData.add(new Pair<>(nextDayStartTime, nextDayEndTime));
                            if(checkFreeTimeOptions()) return;
                        } else {
                            ArrayList<Pair<String, String >> nextDayStartAndEndTimes = dataMap.get(nextKey);
                            String nextDateTime = nextKey + " " + nextDayStartAndEndTimes.get(0).getKey(); //Just need to check first item of next day start time
                            Date nextDateTimeStart = dateTimeFormat12.parse(nextDateTime);
                            Date dateLowerBoundaryPlusDuration = increaseDateTime(dateLowerBoundary, duration);
                            if(dateLowerBoundary.before(nextDateTimeStart) && dateLowerBoundaryPlusDuration.before(nextDateTimeStart)) {
                                freeTimeData.add(new Pair<>(dateLowerBoundary, dateLowerBoundaryPlusDuration));
                                if(checkFreeTimeOptions()) return;
                            }
                        }
                    }
                }
            }
        }
        generateFreeTime();
    }

    private static final ArrayList<String> compiledFreeTimes = new ArrayList<>();

    /**
     * This method generates the output to be shown
     */
    private void setOutput(){
        compiledFreeTimes.clear();
        for (int i = 0; i < freeTimeData.size(); i++) {
            String compiledFreeTimeToShow;
            String compiledFreeTime;
            compiledFreeTimeToShow = dateTimeFormat12.format(freeTimeData.get(i).getKey()) + " until " + timeFormat12.format(freeTimeData.get(i).getValue());
            message += (i+1) + ". " + compiledFreeTimeToShow + "\n";
            String dateTime = dateTimeFormat24.format(freeTimeData.get(i).getKey());
            String[] spiltDateTime = dateTime.split(" ", 3);
            compiledFreeTime =  "/at " + spiltDateTime[1]+ " /from " + spiltDateTime[2] + " /to "+ timeFormat24.format(freeTimeData.get(i).getValue());
            compiledFreeTimes.add(compiledFreeTime);
        }
    }



    public static ArrayList<String> getCompiledFreeTimesList() {
        return compiledFreeTimes;
    }
}