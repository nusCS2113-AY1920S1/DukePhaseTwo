import executor.command.CommandStatistics;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import ui.IncomeReceipt;
import ui.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CommandStatisticsTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receiptOne = new Receipt(4.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);

        Receipt receiptTwo = new Receipt(4.0);
        receiptTwo.addTag("food");
        receiptTwo.setDate(LocalDate.parse("2019-02-02"));
        storageManager.getWallet().addReceipt(receiptTwo);


        CommandStatistics s1 = new CommandStatistics("stats transport");
        s1.execute(storageManager);
        String output = s1.getInfoCapsule().getOutputStr();
        assertEquals("50.00% of your wallet expenses is spent on transport\n"
                + "You spent a total of $4.00 on transport\n\n"
                + "1. [Expenses, transport] $4.00 2019-02-01\n\n", output);

        CommandStatistics s2 = new CommandStatistics("stats");
        s2.execute(storageManager);
        String result = s2.getInfoCapsule().getOutputStr();
        assertEquals("Tag input is missing. FORMAT : stats <tag>", result);

        CommandStatistics s3 = new CommandStatistics("stats books");
        s3.execute(storageManager);
        String result2 = s3.getInfoCapsule().getOutputStr();
        assertEquals("0.00% of your wallet expenses is spent on books\n"
                + "You spent a total of $0.00 on books\n\n\n", result2);

    }

    @Test
    void testPercent() {
        StorageManager storageManager = new StorageManager();

        Receipt receiptOne = new Receipt(4.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);

        IncomeReceipt receipt1 = new IncomeReceipt(4.0);
        receipt1.addTag("food");
        receipt1.setDate(LocalDate.parse("2019-02-02"));
        storageManager.getWallet().addReceipt(receipt1);

        CommandStatistics s1 = new CommandStatistics("stats transport");
        s1.execute(storageManager);
        String output = s1.getInfoCapsule().getOutputStr();
        assertEquals("100.00% of your wallet expenses is spent on transport\n"
                + "You spent a total of $4.00 on transport\n\n"
                + "1. [Expenses, transport] $4.00 2019-02-01\n\n", output);
        CommandStatistics s2 = new CommandStatistics("stats food");
        s2.execute(storageManager);
        String output2 = s2.getInfoCapsule().getOutputStr();
        assertEquals("0.00% of your wallet expenses is spent on food\n"
                + "You spent a total of $0.00 on food\n\n"
                + "1. [Income, food] $4.00 2019-02-02\n\n", output2);
    }

    @Test
    void noReceipts() {
        StorageManager storageManager = new StorageManager();

        CommandStatistics s1 = new CommandStatistics("stats transport");
        s1.execute(storageManager);
        String output = s1.getInfoCapsule().getOutputStr();
        assertEquals("No receipts found in the list", output);
    }

}