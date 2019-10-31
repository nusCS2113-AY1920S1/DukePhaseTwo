package dolla.parser;

import dolla.Tag;
import dolla.Time;

import dolla.command.InitialModifyCommand;
import dolla.task.Debt;
import dolla.ui.DebtUi;
import dolla.action.Repeat;
import dolla.command.Command;
import dolla.command.AddActionCommand;
import dolla.command.ShowListCommand;
import dolla.command.ErrorCommand;
import dolla.command.AddDebtsCommand;
import dolla.command.SortCommand;
import dolla.command.SearchCommand;
import dolla.command.RemoveCommand;

//@@author tatayu
/**
 * DebtsParser is a class that handles the input command and
 * execute the command according to the command under the debt mode.
 */
public class DebtsParser extends Parser {
    private static final String DEBT_COMMAND_REDO = "redo";
    private static final String DEBT_COMMAND_UNDO = "undo";
    private static final String DEBT_COMMAND_REPEAT = "repeat";

    public DebtsParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_DEBT;
    }

    @Override
    public Command parseInput() {
        if (commandToRun.equals("debts")) { //show debt list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals("owe") || commandToRun.equals("borrow")) {
            String type = commandToRun;
            String name;
            double amount;
            Tag t = new Tag();
            try {
                name = inputArray[1];
                amount = stringToDouble(inputArray[2]);
                String[] desc = inputLine.split(inputArray[2] + " ");
                String[] dateString = desc[1].split(" /due ");
                description = dateString[0];

                if (inputLine.contains(t.getPrefixTag())) {
                    String[] dateAndTag = dateString[1].split(t.getPrefixTag());
                    date = Time.readDate(dateAndTag[0].trim());
                } else {
                    date = Time.readDate(dateString[1].trim());
                }
            } catch (IndexOutOfBoundsException e) {
                DebtUi.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            Debt debt = new Debt(type, name, amount, description, date);
            t.handleTag(inputLine, inputArray, debt);
            return processAdd(type, name, amount);
        } else if (commandToRun.equals(COMMAND_MODIFY)) {
            if (verifyFullModifyCommand()) {
                return new InitialModifyCommand(inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_SEARCH)) {
            String component = inputArray[1];
            String content = inputArray[2];
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals(COMMAND_SORT)) {
            return new SortCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("remove")) {
            return new RemoveCommand(mode, inputArray[1]);
        } else if (commandToRun.equals(DEBT_COMMAND_REDO)
                || commandToRun.equals(DEBT_COMMAND_UNDO)
                || commandToRun.equals(DEBT_COMMAND_REPEAT)) {
            return new AddActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
    }

    //@@author yetong1895
    /**
     * This method will process and return a "add" command for debt.
     * @param type the type of input. i.e. owe or borrow.
     * @param name the name of the borrower/lender
     * @param amount the amount borrowed/lent
     * @return an AddDebtsCommand with respect to the nature of the input.
     */
    private Command processAdd(String type, String name, double amount) {
        Command addDebt;
        Repeat.setRepeatInput("debt", inputLine); //setup repeat
        if (undoFlag == 1) { //undo input
            addDebt = new AddDebtsCommand(type, name, amount, description, date, prevPosition);
            resetUndoFlag();
        } else if (redoFlag == 1) {
            addDebt = new AddDebtsCommand(type, name, amount, description, date, -2);
            resetRedoFlag();
        } else { //normal input, prePosition is -1
            addDebt = new AddDebtsCommand(type, name, amount, description, date, -1);
        }
        return addDebt;
    }
}