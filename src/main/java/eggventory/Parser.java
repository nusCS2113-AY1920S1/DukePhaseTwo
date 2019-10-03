package eggventory;

import eggventory.commands.Command;
import eggventory.commands.AddCommand;
import eggventory.commands.DeleteCommand;
import eggventory.commands.DoneCommand;
import eggventory.commands.FindCommand;
import eggventory.commands.ListCommand;
import eggventory.exceptions.InsufficientInfoException;
import eggventory.exceptions.BadInputException;
import eggventory.enums.CommandType;

/**
 * Interprets command strings by the user.
 */

public class Parser {

    private int processDoAfter(String input) throws BadInputException {
        String shortStr;
        String[] splitStr;
        int taskIndex;

        shortStr = input.substring(input.indexOf("/after"));

        try {
            splitStr = shortStr.split(" ", 3); //splits into "/after" "x" and other stuff, where "x" is an int
            taskIndex = Integer.parseInt(splitStr[1]); //check if this is an int
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new BadInputException("Please input the index number of the task that has to be done first.");
        }
        return taskIndex;
    }

    /**
     * Processes the contents of an add command (everything after the word "add").
     * Splits up the input string into an array containing the various attributes of the stock being added.
     *
     * @param input String containing the attributes of the stock.
     * @return an array consisting of StockType, StockCode, Quantity and Description.
     * @throws InsufficientInfoException If any of the required attributes is missed out.
     */
    private String[] processAdd(String input) throws InsufficientInfoException {

        String[] addInput = input.split(" ", 4); //There are 4 attributes for now.

        if (addInput.length < 4 | addInput[0].isBlank() | addInput[1].isBlank()
                | addInput[2].isBlank() | addInput[3].isBlank()) {
            throw new InsufficientInfoException("Please enter stock information after the 'add' command in"
                    + " this format:\nadd <StockType> <StockCode> <Quantity> <Description>");
        }

        return addInput;
    }

    /**
     * Checks if the command keyword (first word is valid).
     * Determines what to do with the remaining string depending on the command.
     * Also handles exceptions for bad description string inputs.
     *
     * @param listInput array containing the command and description from the user.
     * @return an array where the first item is the command word and the second item is the rest of the text.
     * @throws BadInputException If the first word is not one of the recognised commands.
     * @throws InsufficientInfoException If any of the commands had compulsory parameters missed out.
     * @throws NumberFormatException If non-integer inputs are received for numerical parameters.
     */
    private Command handleListInput(String listInput) throws BadInputException,
            InsufficientInfoException, NumberFormatException {

        /*TODO: Update parser to handle Stock requests separately to process optional commands
          TODO: also split parser up into multiple parser modules depending on the first command.
        */

        int afterIndex;
        afterIndex = -1;
        if (listInput.contains("/after")) {
            afterIndex = processDoAfter(listInput);
            listInput = listInput.replace(" /after " + Integer.toString(afterIndex), "");
        }

        //Extract the first word.
        //inputArr[0] is the main command word.
        //inputArr[1] is the subsequent command string, and may also be empty.
        String[] inputArr = listInput.split(" ", 2);
        Command command;

        switch (inputArr[0]) {
        //Commands which are single words.
        case "list":
            command = new ListCommand(CommandType.LIST);
            break;
        case "bye":
            command = new Command(CommandType.BYE);
            break;

        //Commands which require numerical input.
        case "done":
            command = new DoneCommand(CommandType.DONE, Integer.parseInt(inputArr[1]));
            break;
        case "delete":
            command = new DeleteCommand(CommandType.DELETE, Integer.parseInt(inputArr[1]));
            break;

        //Commands which require string input.
        case "add": {
            if (inputArr.length == 1) {
                throw new BadInputException("'" + inputArr[0] + "' requires 1 or more arguments.");
            }

            String[] addInput = processAdd(inputArr[1]);

            command = new AddCommand(CommandType.ADD, addInput[0], addInput[1], Integer.parseInt(addInput[2]),
                    addInput[3]);
            break;
        }
        case "find": {
            if (inputArr.length == 1) {
                throw new BadInputException("'" + inputArr[0] + "' requires 1 or more arguments.");
            }

            String description = inputArr[1].trim(); //Might need to catch empty string exceptions?
            if (!description.isBlank()) {
                command = new FindCommand(CommandType.FIND, description);
            } else {
                command = new Command();
                throw new BadInputException("Please enter the search description.");
            }
            break;
        }

        default:
            command = new Command(); //Bad Command
            throw new BadInputException("Sorry, I don't recognise that input keyword!");
        }
        return command;
    }

    /**
     * Reads in the command string from the user and looks at the first word.
     * The first word and any remaining characters are separated and passed to the handler.
     *
     * @return an array where the first item is the command word and the second item is the rest of the text.
     */
    public Command parse(String userInput) throws Exception {
        Command userCommand;

        //TODO: Make this a do-while that waits for a good input?
        try {
            userCommand = handleListInput(userInput);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please input only an integer after the command.");
        } catch (Exception e) { // Redundant code, but demonstrates throwing all other exceptions higher
            throw e;
        }

        return userCommand;
    }
}
