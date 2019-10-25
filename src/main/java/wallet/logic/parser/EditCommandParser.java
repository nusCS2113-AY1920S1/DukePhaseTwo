package wallet.logic.parser;

import wallet.logic.LogicManager;
import wallet.logic.command.EditCommand;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.Loan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The EditCommandParser class helps to
 * change user input String into appropriate parameters.
 */
public class EditCommandParser implements Parser<EditCommand> {

    public static final String MESSAGE_ERROR_EDIT_CONTACT = "Error in input format when editing contact.";

    @Override
    public EditCommand parse(String input) {
        String[] arguments = input.split(" ", 2);
        switch (arguments[0]) {
        case "expense":
            Expense expense = parseExpense(arguments[1]);
            if (expense != null) {
                return new EditCommand(expense);
            } else {
                break;
            }

        case "loan":
            Loan loan = parseLoan(arguments[1]);
            if (loan != null) {
                return new EditCommand(loan);
            }
            break;

        case "contact":
            Contact contact = parseContact(arguments[1]);
            if (contact != null) {
                return new EditCommand(contact);
            } else {
                break;
            }

        default:
            System.out.println(EditCommand.MESSAGE_USAGE);
            return null;
        }
        return null;
    }

    /**
     * Parses the parameters of contact to be edited.
     *
     * @param input User input arguments
     */
    private Contact parseContact(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        //@@author Xdecosee
        String[] arguments = input.split(" ", 2);
        if (arguments.length == 2) {

            String[] parameters = arguments[1].split(" ");
            try {
                int id = Integer.parseInt(arguments[0].trim());
                ContactParserHelper contactHelper = new ContactParserHelper();
                Contact contact = contactHelper.updateInput(parameters);
                if (contact == null) {
                    System.out.println(MESSAGE_ERROR_EDIT_CONTACT);
                    return null;
                }
                contact.setId(id);
                return contact;
            } catch (NumberFormatException e) {
                System.out.println(MESSAGE_ERROR_EDIT_CONTACT);
                return null;
            }

        }
        System.out.println(MESSAGE_ERROR_EDIT_CONTACT);
        return null;
        //@@author

    }

    /**
     * Parses the parameters of contact to be edited.
     *
     * @param input User input arguments
     */
    Loan parseLoan(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        //@@author A0171206R
        Loan loan = new Loan();

        String[] arguments = input.split(" ", 2);
        int loanId = Integer.parseInt(arguments[0].trim());
        loan.setId(loanId);
        String parameters = arguments[1].trim();

        Wallet wallet = LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState());

        int index = wallet.getLoanList().findIndexWithId(loanId);
        Loan currentLoan = LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList()
                .getState()).getLoanList().getLoan(index);
        loan.setId(currentLoan.getId());
        loan.setDescription(currentLoan.getDescription());
        loan.setIsSettled(currentLoan.getIsSettled());
        loan.setAmount(currentLoan.getAmount());
        loan.setPerson(currentLoan.getPerson());
        loan.setIsLend(currentLoan.getIsLend());
        loan.setDate(currentLoan.getDate());

        if (parameters.contains("/c")) {
            String[] getContact = parameters.split("/c");
            int contactId = Integer.parseInt(getContact[1].trim());
            for (Contact contact : LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList()
                    .getState()).getContactList().getContactList()) {
                if (contact.getId() == contactId) {
                    System.out.println("Edit: Contact found! " + contact.toString());
                    loan.setPerson(contact);
                    break;
                }
            }
            parameters = getContact[0].trim();
        }
        if (parameters.contains("/l")) {
            loan.setIsLend(true);
            if (parameters.equals("/l")) {
                return loan;
            }
            String[] getIsLend = parameters.split("/l");
            parameters = getIsLend[0].trim();
        } else if (parameters.contains("/b")) {
            loan.setIsLend(false);
            if (parameters.equals("/b")) {
                return loan;
            }
            String[] getIsLend = parameters.split("/b");
            parameters = getIsLend[0].trim();
        }
        if (parameters.contains("/t")) {
            String[] getDate = parameters.split("/t");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate createdDate = LocalDate.parse(getDate[1].trim(), formatter);
            loan.setDate(createdDate);
            parameters = getDate[0].trim();
        }
        if (parameters.contains("/a")) {
            String[] getAmount = parameters.split("/a");
            double amount = Double.parseDouble(getAmount[1].trim());
            loan.setAmount(amount);
            parameters = getAmount[0].trim();
        }
        if (parameters.contains("/d")) {
            String[] getDescription = parameters.split("/d");
            String description = getDescription[1].trim();
            loan.setDescription(description);
        }
        return loan;
        //@@author
    }

    /**
     * Parses the parameters of expense to be edited.
     */
    public Expense parseExpense(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        //@@author kyang96
        Expense expense = new Expense();

        String[] arguments = input.split(" ", 2);
        int id = Integer.parseInt(arguments[0].trim());
        expense.setId(id);
        String parameters = arguments[1].trim();
        if (parameters.contains("/r")) {
            String[] getRecurring = parameters.split("/r");
            if (getRecurring[1].trim().equalsIgnoreCase("DAILY")
                    || getRecurring[1].trim().equalsIgnoreCase("WEEKLY")
                    || getRecurring[1].trim().equalsIgnoreCase("MONTHLY")) {
                expense.setRecurring(true);
                expense.setRecFrequency(getRecurring[1].trim().toUpperCase());
            } else if (getRecurring[1].trim().equals("no")) {
                expense.setRecurring(false);
                expense.setRecFrequency(null);
            }
            parameters = getRecurring[0].trim();
        }
        if (parameters.contains("/t")) {
            String[] getDate = parameters.split("/t");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(getDate[1].trim(), formatter);
            expense.setDate(date);
            parameters = getDate[0].trim();
        }
        if (parameters.contains("/c")) {
            String[] getCategory = parameters.split("/c");
            expense.setCategory(Category.getCategory(getCategory[1].trim()));
            parameters = getCategory[0].trim();
        }
        if (parameters.contains("/a")) {
            String[] getAmount = parameters.split("/a");
            expense.setAmount(Double.parseDouble(getAmount[1].trim()));
            parameters = getAmount[0].trim();
        }
        if (parameters.contains("/d")) {
            String[] getDescription = parameters.split("/d");
            expense.setDescription(getDescription[1].trim());
        }

        return expense;
        //@@author
    }
}
