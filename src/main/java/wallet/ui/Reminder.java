package wallet.ui;

import wallet.model.Wallet;
import wallet.model.record.LoanList;
import wallet.model.record.RecordList;

public class Reminder {

    private LoanList loanList;
    private Ui ui;
    private int number;
    private boolean autoRemind;
    private int timeInSeconds;
    private MyThread thread;

    /**
     * The constructor for the Reminder object.
     *
     * @param wallet The Wallet object.
     */
    public Reminder(Wallet wallet) {
        ui = new Ui();
        this.loanList = loanList;
        number = 1;
        autoRemind = true;
        timeInSeconds = 5; //set default time interval of auto remind to be 30 minutes
        thread = new MyThread(true, wallet.getLoanList(), timeInSeconds);
    }


    /**
     * autoReminds the user of undone tasks.
     * this method will run as a background process.
     * users can also set the reminder interval timings.
     * and also turn it off and on as they wish.
     */
    public void autoRemindStart() {
        thread.run();
    }

    /**
     * Kills the auto remind thread.
     */
    public void autoRemindStop() {
        thread.stop();
    }

    /**
     * Sets whether there is a automatic reminder.
     *
     * @param autoRemind Whether automatic reminder is on/off.
     */
    public void setAutoRemind(boolean autoRemind) {
        this.autoRemind = autoRemind;
    }

    /**
     * Checks if automatic reminder is turned on/off.
     *
     * @return whether automatic reminder is turned on/off.
     */
    public boolean getAutoRemind() {
        return this.autoRemind;
    }

    /**
     * Sets the time interval for automatic reminder.
     *
     * @param timeInSeconds THe time interval for the automatic reminder.
     */
    public void setTimeInSeconds(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    /**
     * Shows the time interval in seconds.
     *
     * @return The time interval in seconds.
     */
    public int getTimeInSeconds() {
        return this.timeInSeconds;
    }
}
