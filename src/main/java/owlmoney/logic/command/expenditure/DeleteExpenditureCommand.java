package owlmoney.logic.command.expenditure;

import owlmoney.logic.command.OwlMoneyCommand;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class DeleteExpenditureCommand extends OwlMoneyCommand {
    private final int expNumber;
    private final String from;
    public DeleteExpenditureCommand(int number, String bankName) {
        this.expNumber = number;
        this.from = bankName;
    }

    public void execute(Profile profile, Ui ui) {
        profile.deleteExpenditure(this.expNumber, this.from, ui);
    }
}
