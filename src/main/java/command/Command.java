package command;

import parser.CommandParams;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.util.Map;

/**
 * Acts as the parent class of all commands in the command package, with fields meant to be
 * populated by the individual commands.
 */
public abstract class Command {
    private String name;
    private String description;
    private String usage;
    private Map<String, String> secondaryParams;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public Map<String, String> getSecondaryParams() {
        return secondaryParams;
    }

    /**
     * Creates a new command object, with its name, description, usage and secondary parameters.
     *
     * @param name the name of the command to create.
     * @param description the description of the command to create.
     * @param usage the usage of the command to create.
     * @param secondaryParams the secondary parameters of the command to create.
     */
    public Command(String name, String description, String usage, Map<String, String> secondaryParams) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.secondaryParams = secondaryParams;
    }

    /**
     * Executes the command with parameters given by the user.
     *
     * @param commandParams the parameters given by the user, parsed into a {@code CommandParams} object.
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     */
    public abstract void execute(CommandParams commandParams, TaskList tasks, Ui ui, Storage storage);
}