package compal.tasks;

/**
 * Represents assignment task type with a starting date.
 */
public class DoAfterTasks extends Task {

    /**
     * Constructs DoAfterTask object.
     *
     * @param description Description of do after task.
     * @param date        Starting date of do after task.
     */
    public DoAfterTasks(String description, Priority priority, String date) {
        super(description, priority);
        super.setDate(date);
        super.symbol = "DAT";
    }
}
