package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.task.Task;
import wallet.storage.Storage;

/**
 * The AddCommand Class which extends Command.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS_ADD_TASK = "Got it. I've added this task:";
    public static final String MESSAGE_SUCCESS_ADD_CONTACT = "Got it. I've added this contact:";
    public static final String MESSAGE_SUCCESS_ADD_EXPENSE = "Got it. I've added this expense:";
    public static final String MESSAGE_SUCCESS_ADD_LOAN = "Got it. I've added this loan:";

    private Expense expense = null;
    private Task task = null;
    private Contact contact = null;
    private Loan loan = null;

    /**
     * Constructs the AddCommand object with Expense object.
     *
     * @param expense The Expense Object.
     */
    public AddCommand(Expense expense) {
        this.expense = expense;
    }

    /**
     * Constructs the AddCommand object with Task object.
     *
     * @param task The Task object.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Constructs the AddCommand object with Contract object.
     *
     * @param contact The Contract object.
     */
    public AddCommand(Contact contact) {
        this.contact = contact;
    }

    /**
     * Constructs the AddCommand object with Loan object.
     *
     * @param loan The Loan object.
     */
    public AddCommand(Loan loan) {
        this.loan = loan;
    }

    /**
     * Add the Record objects into their respective lists and returns false.
     *
     * @param wallet  The Wallet object.
     * @param storage The Storage object.
     * @return a boolean variable which indicates
     */
    @Override
    public boolean execute(Wallet wallet, Storage storage) {
        if (expense != null) {
            wallet.getExpenseList().addExpense(expense);
            wallet.getRecordList().addRecord(expense);
            System.out.println(MESSAGE_SUCCESS_ADD_EXPENSE);
            System.out.println(expense.toString());
        }
        if (task != null) {
            wallet.getTaskList().addTask(task);
            System.out.println(MESSAGE_SUCCESS_ADD_TASK);
            System.out.println(task.toString());
            storage.writeFile(task);
        }
        if (contact != null) {
            wallet.getContactList().addContact(contact);
            System.out.println(MESSAGE_SUCCESS_ADD_CONTACT);
            System.out.println(contact.toString());
        }
        if (loan != null) {
            wallet.getLoanList().addLoan(loan);
            wallet.getRecordList().addRecord(loan);
            System.out.println(MESSAGE_SUCCESS_ADD_LOAN);
            System.out.println(loan.toString());
        }

        return false;
    }
}
