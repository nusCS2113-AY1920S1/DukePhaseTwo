package duke.logic.sort;

import duke.model.meal.Meal;

import java.util.Comparator;

public class SortMealByDefault implements Comparator<Meal> {
    @Override
    public int compare(Meal meal, Meal mealCompared) {
        return meal.getMealType().compareTo(mealCompared.getMealType());
    }
}
