package moneycommands;

import controlpanel.MoneyStorage;
import money.*;
import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;

import java.text.ParseException;


public abstract class MoneyCommand {
    public MoneyCommand(){

    }

    public abstract boolean isExit();
    public abstract void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException;
}
