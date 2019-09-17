package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;
import duke.task.TimedTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

//TODO This is complex enough that the generic Javadoc will not do
public class NewRecurringTaskCommand extends MultiArgCommand {
    public NewRecurringTaskCommand() {
        argc = 2;
        delim = "/repeats";
        emptyArgMsg = "You didn't tell me anything about the recurring tasks!";
        invalidArgMsg = "You didn't tell me how the task is supposed to recur!";
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        super.execute(ctx);
        Parser recurParser = new Parser();
        NewTimedTaskCommand refCommand;
        try {
            refCommand = (NewTimedTaskCommand) recurParser.parse(argv[0]); //find out what kind of command to execute
        } catch (ClassCastException excp) {
            throw new DukeException("Can't have that as a recurring task!");
        }

        TemporalUnit period = null;
        int firstSpaceIdx = argv[1].indexOf(" "); //index of first space
        if (firstSpaceIdx == -1) {
            throw new DukeException("You didn't tell me how frequently this task should recur!");
        }
        switch(argv[1].substring(0, firstSpaceIdx)) { //extract period from frequency, use fallthrough to add synonyms
        case "daily":
            period = ChronoUnit.DAYS;
            break;
        case "weekly":
            period = ChronoUnit.WEEKS;
            break;
        case "monthly":
            period = ChronoUnit.MONTHS;
            break;
        default:
            throw new DukeException("I don't know how frequently that's supposed to be!");
        }

        argv[1] = argv[1].substring(firstSpaceIdx).strip(); //remove frequency descriptor

        String countDelim = "/count";
        String untilDelim = "/until";
        long count = 0;

        DukeException invalidRecurrenceExcp = new DukeException("You need to tell me how many times you want that "
                + "task to recur!" + System.lineSeparator() + "Either tell me to repeat it with e.g. '/count 5' or"
                + "with e.g. '/until " + LocalDateTime.now().plus(3, period)
                .format(TimedTask.getDataFormatter()) + "'.");
        if (argv[1].matches("^" + countDelim + "\\s+\\d+$")) {
            count = Long.parseLong(argv[1].substring(countDelim.length()).strip()); //regex checks to ensure validity
        } else if (argv[1].matches("^" + untilDelim + "\\s+[A-Za-z 0-9/]+$")) {
            LocalDateTime until = null;
            try {
                until = LocalDateTime.parse(argv[1].substring(untilDelim.length()).strip(),
                        TimedTask.getDataFormatter());
            } catch (DateTimeParseException excp) {
                throw invalidRecurrenceExcp;
            }
            count = period.between(LocalDateTime.now(), until);
        } else {
            throw invalidRecurrenceExcp;
        }

        if (count <= 0) {
           throw new DukeException("This task will not recur!");
        }

        //I'm gonna do what's called a pro gamer move
        StringBuilder addStrBuilder = new StringBuilder();
        for (long i = 0; i < count; ++i) {
            addStrBuilder.append(System.lineSeparator()).append("  ")
                    .append(refCommand.silentExecute(ctx));
            refCommand.taskDateTime = refCommand.taskDateTime.plus(1, period);
        }
        ctx.ui.print(ctx.taskList.getAddReport(addStrBuilder.toString(), count));

        //test cases:
        //recurring deadline tutorial /by 19/09/2019 1400 /repeats weekly /count 13
        //recurring deadline tutorial /by 19/09/2019 1400 /repeats weekly /until 23/11/2019 1300
    }
}
