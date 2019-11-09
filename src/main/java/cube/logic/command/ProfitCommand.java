//@@author LL-Pengfei
/**
 * ProfitCommand.java
 * Support commands related to generating profits and revenue.
 */
package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.model.ModelManager;
import cube.model.sale.Sale;
import cube.model.sale.SalesHistory;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;

import java.util.Date;

/**
 * This class supports commands related to generating profits and revenue.
 */
public class ProfitCommand extends Command {
    /**
     * Use enums to specify the states of the object whose profits and revenue is to be generated.
     */
    public enum ProfitBy {
        INDEX, NAME, TYPE, ALL
    }

    private int profitIndex;
    private String profitDescription;
    private Date date_i; //start date (initial)
    private Date date_f; //end date (final)
    private ProfitCommand.ProfitBy param;
    private final String MESSAGE_SUCCESS_ALL = "Nice! I've generated the profits and revenue for all the stocks:\n"
            + "profit:  $ %1$s\n"
            + "revenue: $ %2$s\n"
            + "In total, you have %3$s food in the list.\n";
    private final String MESSAGE_SUCCESS_SINGLE = "Nice! I've generated the profits and revenue for this food:\n"
            + "profit:  $ %1$s\n"
            + "revenue: $ %2$s\n"
            + "In total, you have %3$s food in the list.\n";
    private final String MESSAGE_SUCCESS_MULTIPLE = "Nice! I've generated the profits and revenue for this type:\n"
            + "profit:  $ %1$s\n"
            + "revenue: $ %2$s\n"
            + "This type contains "
            + "%3$s food items\n"
            + "In total, you have %4$s food in the list.\n";

    /**
     * The default constructor, empty since parameters are required to perform generating profits and revenue command.
     */
    public ProfitCommand() {
    }

    /**
     * The constructor for generating the total profits and revenue.
     *
     * @param date_i The start date of the period where generating profits and revenue is concerned.
     * @param date_f The end date of the period where generating profits and revenue is concerned.
     * @param param The parameter is used to specify the type of generating profits and revenue.
     */
    public ProfitCommand(Date date_i, Date date_f, String param) {
        this.date_i = date_i;
        this.date_f = date_f;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }

    /**
     * The constructor for generating the total profits and revenue using index.
     *
     * @param date_i The start date of the period where generating profits and revenue is concerned.
     * @param date_f The end date of the period where generating profits and revenue is concerned.
     * @param index The index of the food whose profits and revenue are to be generated.
     * @param param The parameter is used to specify the type of generating profits and revenue.
     */
    public ProfitCommand(Date date_i, Date date_f, int index, String param) {
        this.date_i = date_i;
        this.date_f = date_f;
        this.profitIndex = index - 1;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }

    /**
     *
     * @param date_i The start date of the period where generating profits and revenue is concerned.
     * @param date_f The end date of the period where generating profits and revenue is concerned.
     * @param ProfitDescription The food name or food type whose profits and revenue are to be generated.
     * @param param The parameter is used to specify the type of generating profits and revenue.
     */
    public ProfitCommand(Date date_i, Date date_f, String ProfitDescription, String param) {
        this.date_i = date_i;
        this.date_f = date_f;
        this.profitDescription = ProfitDescription;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }


    /**
     * The class generates the profits and revenue for food whose profits and revenue the user wishes to generate.
     *
     * @param storage The storage we have.
     * @return The Feedback to User for Generate Revenue Command.
     * @throws CommandException If Generating Revenue is unsuccessful.
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        FoodList list = ModelManager.getFoodList();
        Food toGenerate1;

        SalesHistory set = ModelManager.getSalesHistory();
        Sale toGenerate;
        switch (param) {
            case ALL:

                return new CommandResult(String.format(MESSAGE_SUCCESS_ALL, Food.getRevenue(), list.size()));
            case INDEX:
                CommandUtil.requireValidIndex(list, profitIndex);
                toGenerate1 = list.get(profitIndex);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toGenerate1.getFoodRevenue(), list.size()));
            case NAME:
                CommandUtil.requireValidName(list, profitDescription);
                toGenerate1 = list.get(profitDescription);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toGenerate1.getFoodRevenue(), list.size()));
            case TYPE:
                CommandUtil.requireValidType(list, profitDescription);
                double totalRevenue = 0;
                int count = 0, listSize = list.size();
                for (int i = 0; i < listSize; ++i) {
                    if ((list.get(i).getType() != null) && (list.get(i).getType().equals(profitDescription))) {
                        totalRevenue = totalRevenue + list.get(i).getFoodRevenue();
                        ++count;
                    }
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_MULTIPLE, totalRevenue, count, listSize));
        }
        return null;
    }
}
