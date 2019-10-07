package moneycommands;

import controlpanel.*;
import money.Account;

public class startCommand extends MoneyCommand{

    private String message;

    public startCommand(boolean isNewUser){
        if(isNewUser){
            message = "You are a new user please type: init [existing savings] [Avg Monthly Expenditure]\n";
        }else{
            message = "You're ready to use Financial Ghosts\n";
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) {
        //System.out.println("current Goal Savings: $" + account.getGoalSavings());
        ui.appendToOutput(message);

    }
}
