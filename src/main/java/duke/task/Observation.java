package duke.task;

public class Observation extends Evidence {

    private String summary;
    private boolean isObjective;

    /**
     * Represents observations of a doctor about a patient.
     * An Observation object corresponds to the observations of the doctor about the symptoms of a Patient,
     * the information that led to that particular observation, as well as an integer between 1-4
     * representing the priority or significance of the evidence.
     * Attributes:
     * @param name information on the observation / symptom
     * @param impression the impression object the evidence is tagged to
     * @param summary a summary of what led to the observation
     * @param isObjective whether the observation has physical evidence or is a symptom reported by the patient
     * @param priority the priority level of the evidence
     */
    public Observation(String name, Impression impression, int priority, String summary, boolean isObjective) {
        super(name, impression, priority);
        this.summary = summary;
        this.isObjective = isObjective;
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

    public boolean isObjective() {
        return isObjective;
    }

    public void setObjective(boolean objective) {
        isObjective = objective;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
