package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import duchess.exceptions.DuchessException;
import duchess.model.TimeFrame;

import java.util.List;
import java.util.Optional;

public class Todo extends Task {
    /**
     * Creates a todo task from given user input.
     *
     * @param input tokenized user input
     * @throws DuchessException the error if user input is invalid
     */
    public Todo(List<String> input) throws DuchessException {
        if (input.size() == 0) {
            throw new DuchessException("Format for todo: todo <task>");
        }

        this.description = String.join(" ", input);
    }

    @Override
    public String toString() {
        return String.format("[T]%s %s", super.toString(), this.description);
    }

    @Override
    public TimeFrame getTimeFrame() {
        return TimeFrame.ofTimelessTask();
    }

    @Override
    public void snooze() throws DuchessException {
        throw new DuchessException("You can't snooze that task.");
    }

    @Override
    public Optional<Task> getReminder() {
        return Optional.empty();
    }

    @JsonCreator
    public Todo() {
    }
}