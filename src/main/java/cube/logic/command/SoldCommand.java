package cube.logic.command;

import cube.model.FoodList;
import cube.model.Food;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;

public class SoldCommand extends Command{
	String foodName;
	int quantity;

	private final String MESSAGE_SUCCESS = "%1$d of %2$s have been sold\n"
		+ "you have earn $%3$f, the total revenue is $%4$f";	

	public SoldCommand (String foodName, int quantity) {
		this.foodName = foodName;
		this.quantity = quantity;
	}

	public void checkValid(Food foodSold, FoodList list) throws CommandException {
		if (!list.existsName(foodName)) {
			throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
		}
		if (quantity < 0 || quantity > foodSold.getStock()) {
			throw new CommandException(CommandErrorMessage.INVALID_QUANTITY_SOLD);
		}
	}

	@Override
	public CommandResult execute(FoodList list, StorageManager storage) throws CommandException {
		Food foodSold = list.get(foodName);
		checkValid(foodSold, list);
		
		int originalQty = foodSold.getStock();
		foodSold.setStock(originalQty - quantity);
		double profit = quantity * foodSold.getPrice();
		Food.updateRevenue(Food.getRevenue() + profit);
		storage.storeRevenue(Food.getRevenue());
		return new CommandResult(String.format(MESSAGE_SUCCESS, quantity, foodName, profit, Food.getRevenue()));
	}
}