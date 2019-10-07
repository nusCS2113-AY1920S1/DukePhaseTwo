package money;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class represents a bank account tracker which can be used to
 * store the related information of a bank account
 * (account description, balance, the latest update date and the interest rate)
 */
public class BankTracker {

    private String description;
    private double amt;
    private LocalDate latestDate;
    private double rate;

    public BankTracker(String accountDescription, int initialAmt, LocalDate initialDate, double interestRate) {
        description = accountDescription;
        amt = initialAmt;
        latestDate =  initialDate;
        rate = interestRate;
    }

    /**
     * The method is a getter to get a formatted string which contains
     * all the information of a bank account tracker
     * @return A string which contains all the information of a bank account tracker
     */
    public String getBankAccountInfo() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return "  Name: " + description + "\n  Balance: " + amt + "\n  Initial Date: "
                + dateTimeFormatter.format(latestDate) + "\n  Interest Rate: " + rate;
    }

    /**
     * The method is a getter to get the description of the bank account
     * @return The description of the bank account
     */
    public String getDescription() {
        return description;
    }

    /**
     * The method is a getter to get the latest update date for the account tracker
     * @return The latest update date for the account tracker
     */
    public LocalDate getLatestDate() {
        return latestDate;
    }

    /**
     * The method is a getter to get the balance at the latest update date
     * @return The balance at the latest update date
     */
    public double getAmt() {
        return amt;
    }

    /**
     * The method is a getter to get the interest rate
     * @return The interest rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * This method adds the given value to the current balance
     * @param value The given value (can be negative)
     */
    public void addAmt(double value) {
        amt += value;
    }

    public void updateDate(LocalDate date) {
        latestDate = date;
    }
}
