package Tasks;

public class Task {
    public String description;
    public boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    public String getStatusIcon() {
        return (isDone ? "D" : "ND");
    }

    public String listFormat() {
        return "";
    }

}
