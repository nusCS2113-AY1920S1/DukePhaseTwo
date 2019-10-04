package duchess.logic.commands;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.TimeFrame;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ViewScheduleCommand extends Command {
    private List<String> words;
    private String date;
    private String view;
    private TimeFrame timeFrame;

    /**
     * Constructor for class.
     *
     * @param words User input with start and end date time
     * @throws DukeException Exception thrown for invalid or missing date time
     */
    public ViewScheduleCommand(List<String> words) throws DukeException {
        this.words = words;
        this.date = processString("/for");
        this.view = processString("view");
        if (!view.equals("day") && !view.equals("week")) {
            throw new DukeException("Invalid view. Please choose either day or week view.");
        }
        LocalDateTime start = processDate(" 0000");
        LocalDateTime end = processDate(" 2359");
        this.timeFrame = new TimeFrame(start, end);
    }

    private String processString(String keyword) throws DukeException {
        int index = words.indexOf(keyword);
        if (index == -1) {
            throw new DukeException("Format for viewing schedule: schedule /for <date> view <view>");
        }
        return words.get(index + 1);
    }

    /**
     * Process date by setting time of LocalDateTime to either 0000 or 2359.
     * Also sets LocalDateTime to nearest previous or same Monday date/ nearest
     * next or same Friday date if user desires week view.
     *
     * @param time either " 0000" or " 2359" to indicate timeframe
     * @return the LocalDateTime instance
     * @throws DukeException Thrown for invalid or missing date time and command format
     */
    private LocalDateTime processDate(String time) throws DukeException {
        try {
            boolean isWeek = view.equals("week");
            boolean isStartOfDay = time.equals(" 0000");
            boolean isStartOfWeek = isWeek && isStartOfDay;
            boolean isEndOfWeek = isWeek && !isStartOfDay;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDateTime localDateTime = LocalDateTime.parse(date + time, formatter);
            if (isStartOfWeek) {
                localDateTime = localDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            } else if (isEndOfWeek) {
                localDateTime = localDateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            }
            return localDateTime;
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date format. Please follow dd/MM/yyyy.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Format for viewing schedule: schedule /for <date> view <view>");
        }
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DukeException {
        List<Task> tasksForToday =
                store.getTaskList().stream()
                        .filter(task -> task.getTimeFrame().fallsWithin(this.timeFrame))
                        .collect(Collectors.toList());
        if (tasksForToday.size() <= 0) {
            throw new DukeException("There are no tasks in the schedule.");
        } else if (view.equals("week")) {
            date = "the week with " + date;
        }
        Collections.sort(tasksForToday);
        ui.showScheduleResult(tasksForToday, date);
    }
}