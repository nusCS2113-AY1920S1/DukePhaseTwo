package duke.task;

public class FixedDuration extends Task {

    protected int duration;
    protected String unit;

    /**
     * Creates a Fixed Duration Task with the specific description and fixed duration.
     *
     * @param description Description of the Task.
     * @param duration The duration needed to complete the task.
     * @param unit The time unit.
     */
    public FixedDuration(String description, int duration, String unit) {
        super(description);
        this.duration = duration;
        this.unit = unit;
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String to be displayed.
     */
    @Override
    public String toString() {
        return "[F]" + super.toString() + " (needs " + duration + " " + unit + ")";
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String to be written into text file.
     */
    @Override
    public String toFile() {
        return "F|" + super.toFile() + "|" + duration + " " + unit;
    }
}
