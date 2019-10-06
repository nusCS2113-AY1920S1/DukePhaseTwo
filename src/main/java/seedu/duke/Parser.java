package seedu.duke;

import seedu.duke.command.AddCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.DoAfterCommand;
import seedu.duke.command.DoneCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.FindCommand;
import seedu.duke.command.FlipCommand;
import seedu.duke.command.InvalidCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.ReminderCommand;
import seedu.duke.command.SnoozeCommand;
import seedu.duke.email.EmailList;
import seedu.duke.email.emailcommand.ListEmailCommand;
import seedu.duke.email.emailcommand.ShowEmailCommand;
import seedu.duke.email.emailcommand.FetchEmailCommand;
import seedu.duke.task.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class that contains helper functions used to process user inputs. It also contains UserInputException
 * that is used across the project to handle the unexpected user input.
 */
public class Parser {

    /**
     * Two types of input, prefix will be displayed according to this in the userInput text field.
     */
    public enum InputType {
        TASK,
        EMAIL
    }

    private static InputType inputType;

    /**
     * Constructor that initializes the input type to TASK.
     */
    public Parser() {
        this.inputType = InputType.TASK;    // default input type when initiating the program.
    }

    /**
     * Get input prefix for userInput text field in GUI.
     *
     * @return current prefix.
     */
    public static String getInputPrefix() {
        String prefix = "";
        switch (inputType) {
        case TASK:
            prefix = "task ";
            break;
        case EMAIL:
            prefix = "email ";
            break;
        default:
            prefix = "";
        }
        return prefix;
    }

    /**
     * Set to the new input type when it is toggled by "flip" command.
     *
     * @param newInputType the input type that is going to be changed to
     */
    public static void setInputType(InputType newInputType) {
        inputType = newInputType;
    }

    /**
     * Parses the user/file input as command. It returns a command that is not yet executed. It also needs to
     * get a UI from Duke to display the messages.
     *
     * @param input the user/file input that is to be parsed to a command
     * @return the parse result, which is a command ready to be executed
     */
    public static Command parseCommand(String input) throws UserInputException {
        UI ui = Duke.getUI();
        TaskList taskList = Duke.getTaskList();
        EmailList emailList = Duke.getEmailList();
        if (inputType == InputType.TASK) {
            return parseTaskCommand(input, ui, taskList);
        } else if (inputType == InputType.EMAIL) {
            try {
                return parseEmailCommand(emailList, input);
            } catch (UserInputException e) {
                ui.showError(e.toString());
                return new InvalidCommand();
            }
        } else {
            return new InvalidCommand();
        }
    }

    private static Command parseTaskCommand(String rawInput, UI ui, TaskList taskList) throws UserInputException {
        if (rawInput.length() <= 5) {
            return new InvalidCommand();
            //return new HelpTaskCommand();
        }
        String input = rawInput.substring(5).strip();
        if (input.equals("flip")) {
            return new FlipCommand(inputType);
        }
        if (input.equals("bye")) {
            return new ExitCommand();
        } else if (input.equals("list")) {
            return new ListCommand(taskList);
        } else if (input.startsWith("done ")) {
            return parseDoneCommand(input, ui);
        } else if (input.startsWith("delete ")) {
            return parseDeleteCommand(input, ui, taskList);
        } else if (input.startsWith("find ")) {
            return parseFindCommand(input, ui, taskList);
        } else if (input.startsWith("reminder")) {
            return parseReminderCommand(input, ui, taskList);
        } else if (input.startsWith("doafter")) {
            return parseDoAfterCommand(input, ui, taskList);
        } else if (input.startsWith("snooze ")) {
            return parseSnoozeCommand(input, ui, taskList);
        }
        return parseAddTaskCommand(taskList, input);
    }

    /**
     * Parses the specific part of a user/file input that is relevant to email. A successful parsing always
     * returns an email-relevant Command.
     *
     * @param emailList target email list from Duke.
     * @param rawInput  user/file input ready to be parsed.
     * @return an email-relevant Command.
     * @throws UserInputException an exception when the parsing is failed, probably due to the wrong format of
     *                            input
     */
    public static Command parseEmailCommand(EmailList emailList, String rawInput) throws UserInputException {
        if (rawInput.length() <= 6) {
            throw new Parser.UserInputException("☹ OOPS!!! Enter \'email -help\' to get list of methods for "
                    + "email.");
            // return new InvalidCommand();
            //return new HelpTaskCommand();
        }
        String input = rawInput.substring(6).strip();
        String emailCommand = input.split(" ")[0];
        switch (emailCommand) {
        case "flip":
            return new FlipCommand(inputType);
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListEmailCommand(emailList);
        case "show":
            return parseShowEmailCommand(emailList, input);
        case "fetch":
            return new FetchEmailCommand(emailList);
        default:
            throw new Parser.UserInputException("☹ OOPS!!! Enter \'email help\' to get list of methods for "
                    + "email.");
        }
    }

    private static Command parseShowEmailCommand(EmailList emailList, String input) throws UserInputException {
        if (input.length() <= 4) {
            throw new UserInputException("Please enter index of email to be shown after \'email "
                    + "show\'");
        }
        try {
            String parsedInput = input.substring(4).strip();
            int index = Integer.parseInt(parsedInput) - 1;
            return new ShowEmailCommand(emailList, index);
        } catch (NumberFormatException e) {
            throw new UserInputException(e.toString());
        } catch (Exception e) {
            throw new UserInputException(e.toString());
        }
    }


    private static Command parseSnoozeCommand(String input, UI ui, TaskList taskList) {
        if (input.length() <= 7) {
            ui.showError("Please enter index of task after \'snooze\'");
            return new InvalidCommand();
        } else {
            try {
                int index = parseIndex(input);
                return new SnoozeCommand(taskList, index);
            } catch (NumberFormatException e) {
                ui.showError(e.toString());
            } catch (UserInputException e) {
                ui.showError("Please enter correct task index");
            }
        }
        return new InvalidCommand();
    }

    private static Command parseDoAfterCommand(String input, UI ui, TaskList taskList) {
        if (input.length() < 8) {
            ui.showError("Please enter index of task after \'doafter\'");
        } else if (input.length() < 11) {
            ui.showError("Please enter description for do-after task");
        } else {
            String[] splitInput = input.split(" /");
            try {
                int itemNumber = Integer.parseInt(splitInput[1].trim());
                return new DoAfterCommand(taskList, itemNumber, splitInput[2]);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                ui.showError(e.toString());
                return new InvalidCommand();
            }
        }
        return new InvalidCommand();
    }

    private static Command parseReminderCommand(String input, UI ui, TaskList taskList) {
        int dayLimit = -1;
        if (input.length() > 9 && input.charAt(8) == ' ') {
            try {
                dayLimit = Integer.parseInt(input.substring(9));
            } catch (NumberFormatException e) {
                ui.showError("Reminder day limit in wrong format. Default is used.");
            }
        }
        if (dayLimit < 0) {
            return new ReminderCommand(taskList);
        } else {
            return new ReminderCommand(taskList, dayLimit);
        }
    }

    private static Command parseFindCommand(String input, UI ui, TaskList taskList) {
        if (input.length() <= 5) {
            ui.showError("Please enter keyword for searching after \'find\'");
        } else {
            String keyword = input.split(" ", 2)[1];
            return new FindCommand(taskList, keyword);
        }
        return new InvalidCommand();
    }

    private static Command parseDeleteCommand(String input, UI ui, TaskList taskList) {
        if (input.length() <= 7) {
            ui.showError("Please enter index of task after \'delete\'");
            return new InvalidCommand();
        } else {
            try {
                int index = parseIndex(input);
                return new DeleteCommand(taskList, index);
            } catch (NumberFormatException e) {
                ui.showError(e.toString());
            } catch (UserInputException e) {
                ui.showError("Please enter correct task index");
            }
        }
        return new InvalidCommand();
    }

    private static Command parseDoneCommand(String input, UI ui) {
        if (input.length() <= 5) {
            ui.showError("Please enter index of task after \'done\'");
            return new InvalidCommand();
        } else {
            try {
                int index = parseIndex(input);
                return new DoneCommand(index);
            } catch (NumberFormatException e) {
                ui.showError(e.toString());
            } catch (UserInputException e) {
                ui.showError("Please enter correct task index");
            }
        }
        return new InvalidCommand();
    }

    private static int parseIndex(String input) throws NumberFormatException, UserInputException {
        String[] splited = input.split(" ", 2);
        if (splited.length < 2) {
            throw new UserInputException("Please enter task index");
        }
        return Integer.parseInt(splited[1]) - 1;
    }

    /**
     * Parses the specific part of a user/file input that is relevant to a task. A successful parsing always
     * returns an AddCommand, as it is assumed that an input starting with a task name is an add command.
     *
     * @param taskList target task list to which the new task is to be added to
     * @param input    user/file input ready to be parsed
     * @return an AddCommand of the task parsed from the input
     * @throws UserInputException an exception when the parsing is failed, probably due to the wrong format of
     *                            input
     */
    public static Command parseAddTaskCommand(TaskList taskList, String input) throws UserInputException {
        Task.TaskType taskType;
        String name;
        LocalDateTime time = null;
        String doAfter = null;
        ArrayList<String> tags = new ArrayList<>();

        if (input.startsWith("todo")) {
            taskType = Task.TaskType.ToDo;
            if (input.length() <= 5) {
                throw new Parser.UserInputException("☹ OOPS!!! The description of a todo cannot be empty.");
            }
            input = input.substring(5);
            while (input.contains("#")) {
                tags.add(input.split("#", 3)[1]);
                input = input.split("#",3)[0] + input.split("#", 3)[2];
            }
            if (input.contains(" /doafter ")) {
                doAfter = input.split(" /doafter ", 2)[1];
                input = input.split(" /doafter ", 2)[0];
            }
            name = input;
        } else if (input.startsWith("deadline")) {
            taskType = Task.TaskType.Deadline;
            if (input.length() <= 9) {
                throw new Parser.UserInputException("☹ OOPS!!! The description of a deadline cannot be "
                        + "empty.");
            }
            input = input.substring(9);
            if (!input.contains(" /by ")) {
                throw new Parser.UserInputException("☹ OOPS!!! A deadline must have a time specified.");
            }
            name = input.split(" /by ", 2)[0];
            String timeString = input.split(" /by ", 2)[1];
            while (input.contains("#")) {
                tags.add(input.split("#", 3)[1]);
                input = input.split("#",3)[0] + input.split("#", 3)[2];
            }
            if (input.contains(" /doafter ")) {
                doAfter = timeString.split(" /doafter ", 2)[1];
                timeString = timeString.split(" /doafter ", 2)[0];
            }
            time = Task.parseDate(timeString);
        } else if (input.startsWith("event")) {
            taskType = Task.TaskType.Event;
            if (input.length() <= 6) {
                throw new Parser.UserInputException("☹ OOPS!!! The description of a event cannot be empty.");
            }
            input = input.substring(6);
            if (!input.contains(" /at ")) {
                throw new Parser.UserInputException("☹ OOPS!!! A event must have a time specified.");
            }
            name = input.split(" /at ", 2)[0];
            String timeString = input.split(" /at ", 2)[1];
            while (input.contains("#")) {
                tags.add(input.split("#", 3)[1]);
                input = input.split("#",3)[0] + input.split("#", 3)[2];
            }
            if (input.contains(" /doafter ")) {
                doAfter = timeString.split(" /doafter ", 2)[1];
                timeString = timeString.split(" /doafter ", 2)[0];
            }
            time = Task.parseDate(timeString);
        } else {
            throw new Parser.UserInputException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        return new AddCommand(taskList, taskType, name, time, doAfter, tags);
    }

    /**
     * An type of exception dedicated to handling the unexpected user/file input. The message contains more
     * specific information.
     */
    public static class UserInputException extends Exception {
        private String msg;

        /**
         * Instantiates the exception with a message, which is ready to be displayed by the UI.
         *
         * @param msg the message that is ready to be displayed by UI.
         */
        public UserInputException(String msg) {
            super();
            this.msg = msg;
        }

        /**
         * Converts the exception ot string by returning its message, so that it can be displayed by the UI.
         *
         * @return the message of the exception
         */
        @Override
        public String toString() {
            return msg;
        }
    }
}
