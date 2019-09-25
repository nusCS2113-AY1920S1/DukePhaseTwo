package wallet.model.record;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseListTest {
    ExpenseList expenseList = new ExpenseList();

    @Test
    public void addExpense_nonRecurringExpense_success() {
        Expense e = new Expense("Lunch", LocalDate.now(), 3, "Food", false, "NULL");
        expenseList.addExpense(e);
        for (Expense ex : expenseList.getExpenseList()) {
            assertEquals("Lunch", ex.getDescription());
            assertEquals(LocalDate.now(), ex.getDate());
            assertEquals(3.0, ex.getAmount());
            assertEquals("Food", ex.getCategory());
            assertEquals(false, ex.isRecurring());
            assertEquals("NULL", ex.getRecFrequency());
        }
    }

    @Test
    public void addExpense_recurringExpense_success() {
        Expense e = new Expense("Lunch", LocalDate.now(), 3, "Food", true, "DAILY");
        expenseList.addExpense(e);
        for (Expense ex : expenseList.getExpenseList()) {
            assertEquals("Lunch", ex.getDescription());
            assertEquals(LocalDate.now(), ex.getDate());
            assertEquals(3.0, ex.getAmount());
            assertEquals("Food", ex.getCategory());
            assertEquals(true, ex.isRecurring());
            assertEquals("DAILY", ex.getRecFrequency());
        }
    }

    @Test
    public void editExpense_validExpense_success() {
        Expense e = new Expense("Lunch", LocalDate.now(), 3, "Food", false, "NULL");
        expenseList.addExpense(e);
        e.setDescription("Dinner");
        e.setAmount(5);
        expenseList.editExpense(0, e);
        for (Expense ex : expenseList.getExpenseList()) {
            assertEquals("Dinner", ex.getDescription());
            assertEquals(LocalDate.now(), ex.getDate());
            assertEquals(5.0, ex.getAmount());
            assertEquals("Food", ex.getCategory());
            assertEquals(false, ex.isRecurring());
            assertEquals("NULL", ex.getRecFrequency());
        }
    }

    @Test
    public void findExpenseIndex_validExpense_success() {
        Expense e = new Expense("Lunch", LocalDate.now(), 3, "Food", false, "NULL");
        expenseList.addExpense(e);
        int index = expenseList.findExpenseIndex(e);
        assertEquals(0, index);
    }
}
