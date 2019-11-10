package diyeats.logic.commands;

import diyeats.commons.datatypes.Pair;
import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.suggestion.ExerciseSuggestionHandler;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

import static diyeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;


//@@author Fractalisk

/**
 * Analyze the list of SuggestExercise objects as well as the current calorie goal of the
 * user, the date provided and the user meal parameters provided to give appropriate exercise suggestion.
 * TONOTE: calories burned from exercise is directly related to BMI, eg, a 200kg man running for 10 mins will burn
 * as many calories as a 100kg man running for 20 mins
 * Exercise needed to elevate activity level = user.getDailyCalorie * user.gerActivityLevelDifference();
 * Exercise is defined in metabolic unit at rest(MET), eg, 1 hr of running which is 14.5 MET
 * is equal to 14.5 hours of static calorie loss.
 * This class adds an exercise to a date, not a suggestable exercise.
 */
public class SuggestExerciseCommand extends Command {
    private LocalDate date;
    private String keyword = null;
    private ExerciseSuggestionHandler exerciseSuggestionHandler;

    /**
     * Constructor for suggestExerciseCommand.
     * @param date the date for which an exercise suggestion is to be made
     * @param keyword any keywords the user has entered
     */
    public SuggestExerciseCommand(LocalDate date, String keyword) {
        this.date = date;
        this.keyword = keyword;
    }

    // This constructor is called if there are issues parsing user input.
    public SuggestExerciseCommand(boolean isFail, String message) {
        this.isFail = true;
        this.errorStr = message;
    }

    /**
     * Executes the suggestExercise command. Has 2 stages.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        isDone = false;
        switch (stage) {
            case 0:
                //Shows the user the list of relevant exercises as well as their duration
                exerciseSuggestionHandler = new ExerciseSuggestionHandler();
                execute_stage_0(meals, user);
                stage++;
                break;
            case 1:
                //Adds the selected exercise routine to exerciseList
                execute_stage_1(meals, storage);
                break;
            default:
                //Exits execute loop if command enters invalid state
                isDone = true;
        }
    }

    /**
     * First stage of execute.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param user the object that handles all user data
     */
    private void execute_stage_0(MealList meals, User user) {
        if (user.getGoal() == null) {
            ui.goalNotFound();
            isDone = true;
        } else if (user.getGoal().getActivityLevelTarget() == 5) {
            ui.goalNotFound();
            isDone = true;
        } else {
            int calorieToElevateActivityLevel = (int) (user.getDailyCalorie() * user.getActivityLevelDifference());
            int excessCalorie = user.getCalorieBalance() - meals.getCalorieBalanceOnDate(date);
            if (excessCalorie < 0) {
                //subtract current day's deficit of calories to the amount required for exercise
                calorieToElevateActivityLevel -= excessCalorie;
            }
            ui.showExerciseRequired(calorieToElevateActivityLevel, date);
            ArrayList<Pair> exerciseArrayList = this.exerciseSuggestionHandler.compute(meals.getExerciseList(),
                    calorieToElevateActivityLevel, user.getDailyCalorie(), keyword);

            ui.showExerciseOptions(exerciseArrayList);
            ui.showMessage("Input 0 to cancel selection");
        }
    }

    /**
     * Second stage of execute.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     */
    private void execute_stage_1(MealList meals, Storage storage) {
        int exerciseIdx;
        try {
            exerciseIdx = Integer.parseInt(this.responseStr);
        } catch (NumberFormatException e) {
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            return;
        }

        if (exerciseIdx == 0) {
            ui.showMessage("The suggest exercise command has been canceled");
            isDone = true;
            return;
        }

        if (exerciseIdx < 0 || exerciseIdx > exerciseSuggestionHandler.getSize()) {
            ui.showMessage(responseStr + " is out of bounds. Please input a valid index.");
            return;
        }

        Pair selectedExercise = exerciseSuggestionHandler.getExercise(exerciseIdx);
        meals.getExerciseList().addExerciseAtDate(date, selectedExercise);
        ui.showMessage("Got it!, I have set the chosen exercise for the date "
                + date.format(LOCAL_DATE_FORMATTER) + ".");

        try {
            storage.writeExercises(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        isDone = true;
    }
}