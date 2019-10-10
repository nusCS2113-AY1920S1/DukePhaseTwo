package duke.task;

public class Result extends Evidence {

    private String summary;

    /**
     * Represents results of an investigation based on the treatment prescribed for a patient.
     * A Result object corresponds to the result of an investigation into the symptoms of a Patient,
     * the particular impression, as well as an integer between 1-4 representing the priority
     * or significance of the result.
     * Attributes:
     * - name: the result
     * - impression: the impression object the result is tagged to
     * - summary: a summary of the result
     * - priority: the priority level of the evidence
    */
    public Result(String name, Impression impression, int priority, String summary) {
        super(name, impression, priority);
        this.summary = summary;
    }

    @Override
    public String toString() {
        // todo
        return null;
    }

    @Override
    public String toDisplayString() {
        // todo
        return null;
    }

    @Override
    public String toReportString() {
        // todo
        return null;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
