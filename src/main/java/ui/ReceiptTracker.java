package ui;

import duke.exception.DukeException;

import java.util.ArrayList;
import java.util.HashMap;

public class ReceiptTracker extends ArrayList<Receipt> {
    private HashMap<String, ReceiptTracker> folders;
    private Double totalCashSpent;

    /**
     * Overloaded Constructor for ReceiptTracker.
     * @param receiptList List of receipts to be loaded into the ReceiptTracker.
     */
    public ReceiptTracker(ArrayList<Receipt> receiptList) {
        this.addAll(receiptList);
        this.updateTotalCashSpent();
        this.setFolders(new HashMap<>());
    }

    /**
     * Default Constructor for ReceiptTracker.
     */
    public ReceiptTracker() {
        this.setFolders(new HashMap<>());
        this.updateTotalCashSpent();
        this.setFolders(new HashMap<>());
    }

    /**
     * Wrapper to add a new Receipt Object to the Receipt Tracker.
     * @param receipt Receipt Object to be added.
     */
    public void addReceipt(Receipt receipt) {
        this.add(receipt);
        for (String tag : receipt.getTags()) {
            if (isRegisteredTag(tag)) {
                Double currTotalCashSpent = folders.get(tag).getTotalCashSpent();
                folders.get(tag).addReceipt(receipt);
                folders.get(tag).setTotalCashSpent(currTotalCashSpent + receipt.getCashSpent());
            }
        }
        this.updateTotalCashSpent();
    }

    /**
     * Updates totalCashSpent property of this ReceiptTracker Object.
     */
    public void updateTotalCashSpent() {
        this.setTotalCashSpent(this.sumReceipts());
    }

    /**
     * Sums the cashSpent of all the Receipts, subtracting any cashGained from IncomeReceipts.
     * @return Double representing the totalCashSpent
     */
    public Double sumReceipts() {
        Double sum = 0.0;
        for (Receipt receipt : this) {
            sum += receipt.getCashSpent();
        }
        return sum;
    }

    /**
     * Registers a tag to be tracked.
     * @param tag String to be registered into the folders property of ReceiptTracker Object
     */
    public void addFolder(String tag) throws DukeException {
        if (this.getFolders().containsKey(tag)) {
            throw new DukeException("Category already exists!");
        }
        ArrayList<Receipt> taggedReceipts = getReceiptsByTag(tag);
        this.getFolders().put(tag, new ReceiptTracker(taggedReceipts));
    }

    /**
     * Find all receipts that are tagged with a specific String.
     * @param tag Specific String to be filtered with.
     * @return ArrayList containing all the Receipts with the specific tag
     */
    public ReceiptTracker getReceiptsByTag(String tag) {
        ReceiptTracker taggedReceipts = new ReceiptTracker();
        for (Receipt receipt : this) {
            if (receipt.containsTag(tag)) {
                taggedReceipts.addReceipt(receipt);
            }
        }
        return taggedReceipts;
    }

    /**
     * Find all receipts that corresponds to the specific date.
     * @param date Specific String to be filtered with
     * @return ArrayList containing all the Receipts with the specific date
     */
    public ReceiptTracker getReceiptsByDate(String date) {
        ReceiptTracker dateReceipts = new ReceiptTracker();
        for (Receipt receipt : this) {
            if (receipt.equalsDate(date)) {
                dateReceipts.addReceipt(receipt);
            }
        }
        return dateReceipts;
    }


    /**
     * Finds all the receipts that corresponds to that month and year.
     * @param month is the month given by the user
     * @param year is the year given by the user
     * @return ArrayList containing all the receipts which corresponds to year and month
     */
    public ReceiptTracker getReceiptsByMonthYear(int month, int year) {
        ReceiptTracker receiptByMonthYear = new ReceiptTracker();
        for (Receipt receipt : this) {
            if ((receipt.getDate().getMonthValue() == month) && (receipt.getDate().getYear() == year)) {
                receiptByMonthYear.addReceipt(receipt);
            }
        }
        return receiptByMonthYear;
    }

    /**
     * Finds all the receipts that corresponds to that year.
     * @param year is the year given by the user
     * @return ReceiptTracker containing all the receipts which corresponds to the year given by user
     */
    public ReceiptTracker getReceiptsByYear(int year) {
        ReceiptTracker receiptByYear = new ReceiptTracker();
        for (Receipt receipt : this) {
            if (receipt.getDate().getYear() == year) {
                receiptByYear.addReceipt(receipt);
            }
        }
        return receiptByYear;
    }


    // -- Boolean Functions
    /**
     * Checks if a tag has been registered previously.
     * @param tag String representing folder to be checked
     * @return true if a folder in the folders property has the name tag, false otherwise
     */
    public boolean isRegisteredTag(String tag) {
        return this.getFolders().containsKey(tag);
    }

    // -- Setters & Getters

    /**
     * Retrieves the totalCashSpent by a specific tag.
     * @param tag String representing the tag to filter by
     * @return Double, the total amount spent on a given tag
     */
    public double getCashSpentByTag(String tag) {
        if (isRegisteredTag(tag)) {
            return this.getFolders().get(tag).getTotalCashSpent();
        } else {
            ReceiptTracker temp = new ReceiptTracker(this.getReceiptsByTag(tag));
            return temp.getTotalCashSpent();
        }
    }

    /**
     * Setter for the folders property of the ReceiptTracker Object.
     * @param folders HashMap to be set as the folders property of ReceiptTracker Object
     */
    public void setFolders(HashMap<String, ReceiptTracker> folders) {
        this.folders = folders;
    }

    /**
     * Getter for the folders property of the ReceiptTracker Object.
     * @return HashMap representing the folders property of ReceiptTracker Object
     */
    public HashMap<String, ReceiptTracker> getFolders() {
        return folders;
    }

    /**
     * Setter for the totalCashSpent property of the ReceiptTracker Object.
     * @param totalCashSpent Double amount to be set as the totalCashSpent property of ReceiptTracker Object
     */
    public void setTotalCashSpent(Double totalCashSpent) {
        this.totalCashSpent = totalCashSpent;
    }

    /**
     * Getter for totalCashSpent property of ReceiptTracker Object.
     * @return Double representing the totalCashSpent property of ReceiptTracker Object
     */
    public Double getTotalCashSpent() {
        return totalCashSpent;
    }

    /**
     * Deletes a receipt via its index.
     *
     * @param index Index of the receipt to be deleted
     */
    public void deleteReceiptsByIndex(int index) {
        this.remove(index);
    }

    /**
     * Prints all the receipts stored in the ReceiptTracker Object.
     * @return String containing all the receipts to be printed to the User
     */
    public String getPrintableReceipts() {
        StringBuilder outputStr = new StringBuilder();
        for (int index = 0; index < this.size(); ++index) {
            try {
                outputStr.append(index + 1)
                        .append(". ")
                        .append(this.get(index).getTags())
                        .append(" ")
                        .append(this.get(index).getCashSpent())
                        .append(" ")
                        .append(this.get(index).getDate())
                        .append("\n")
                ;
            } catch (Exception e) {
                outputStr.append("Unable to print Receipt ")
                        .append(String.valueOf(index + 1))
                        .append("\n")
                ;
            }
        }
        return outputStr.toString();
    }
}