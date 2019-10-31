package duke.ui.window;

import duke.command.Command;
import duke.command.Executor;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.MessageBox;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * UI element designed for the user to interact with the application.
 * It has 3 main tasks.
 * 1. Displays and reads user's input.
 * 2. Use the Parser to parse VALID user's input into a defined command and displays the corresponding result.
 * 3. Displays the appropriate error message for INVALID user's input.
 */
public class CommandWindow extends InputHistoryWindow {
    private static final String FXML = "CommandWindow.fxml";
    private static final String MESSAGE_WELCOME_GREET = "Hello! I'm Dr. Duke.";
    private static final String MESSAGE_WELCOME_QUESTION = "What can I do for you today?";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox messageContainer;

    private Parser parser;
    private Executor executor;

    /**
     * Constructs the command window of the application.
     *
     * @param parser   Parser object responsible for parsing user commands.
     * @param executor Executor object responsible for executing user commands.
     */
    public CommandWindow(Parser parser, Executor executor) {
        super(FXML, null);

        this.parser = parser;
        this.executor = executor;

        scrollPane.vvalueProperty().bind(messageContainer.heightProperty());
        inputTextField.requestFocus();

        printWelcome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleAction() {
        String input = inputTextField.getText().strip();

        if (!input.isEmpty()) {
            storeInput(input);
            try {
                writeHistory();
            } catch (DukeException e) {
                printError(e);
            }
            messageContainer.getChildren().add(MessageBox.getUserMessage(input).getRoot());

            try {
                executor.execute(parseCommand(input));
            } catch (DukeException e) {
                printError(e);
            }
        }

        inputTextField.setText("");
    }

    /**
     * Uses the Parser to retrieve the requested command, which will be loaded with parameters
     * extracted from the user's input arguments.
     *
     * @param input Input string to be parsed.
     * @return The command specified by the user, with arguments parsed.
     * @throws DukeException If the parser fails to find a matching command or the arguments do not meet the command's
     *                       requirements.
     */
    private Command parseCommand(String input) throws DukeException {
        return parser.parse(input);
    }

    /**
     * Prints message.
     *
     * @param message Message.
     */
    public void print(String message) {
        messageContainer.getChildren().add(MessageBox.getDukeMessage(message).getRoot());
    }

    /**
     * Prints welcome message.
     */
    private void printWelcome() {
        String welcome = MESSAGE_WELCOME_GREET + System.lineSeparator() + MESSAGE_WELCOME_QUESTION;
        print(welcome);
    }

    /**
     * Prints error message from an exception.
     *
     * @param e Exception.
     */
    private void printError(DukeException e) {
        print(e.getMessage());
    }

    public TextArea getInputTextField() {
        return inputTextField;
    }
}