package moomoo;

import moomoo.command.Command;
import moomoo.feature.Budget;

import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.CategoryList;
import moomoo.feature.parser.Parser;
import moomoo.feature.storage.CategoryStorage;
import moomoo.feature.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Runs MooMoo.
 */
public class MooMoo {
    private Storage storage;
    private CategoryList categoryList;
    private Budget budget;
    public ScheduleList calendar;
    private Ui ui;

    /**
     * Initializes different Category, Expenditures, Budget, Storage and Ui.
     */
    private MooMoo() {
        ui = new Ui();
        storage = new Storage("data/budget.txt","data/schedule.txt");
        try {
            categoryList = CategoryStorage.loadFromFile();
        } catch (MooMooException e) {
            ui.printException(e);
            ui.showResponse();
            categoryList = new CategoryList();
        }

        HashMap<String, Double> loadedBudget = storage.loadBudget(categoryList.getCategoryList(), ui);
        if (loadedBudget == null) {
            ui.showResponse();
            budget = new Budget();
        } else {
            budget = new Budget(loadedBudget);
        }

        HashMap<String, ArrayList<String>> scheduleList = storage.loadCalendar(ui);
        if (scheduleList == null) {
            ui.showResponse();
            calendar = new ScheduleList();
        } else {
            calendar = new ScheduleList(scheduleList);
        }
    }

    /**
     * Runs the command line interface, reads input from user and returns result accordingly.
     */
    private void run() {
        ui.showWelcome();
        String date = ui.showDate();
        String todaySchedule = calendar.showSchedule(date);
        ui.setOutput(todaySchedule);
        ui.showResponse();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                System.out.print("\u001b[2J");
                System.out.flush();
                Command c = Parser.parse(fullCommand, ui);
                c.execute(calendar, budget, categoryList, ui, storage);

                if (!ui.returnResponse().equals("")) {
                    ui.showResponse();
                }
              
                isExit = c.isExit;
            } catch (MooMooException e) {
                ui.printException(e);
                ui.showResponse();
            }
        }
    }

    /**
     * Returns the response to the GUI when given an input by a user.
     * @param input Input given by user in the GUI
     * @return String Response to display on GUI by the bot.
     */
    public String getResponse(String input) {
        boolean isExit;
        try {
            Command c = Parser.parse(input, ui);
            c.execute(calendar, budget, categoryList, ui, storage);

            isExit = c.isExit;
            if (isExit) {
                ui.showGoodbye();
                return ui.returnResponse();
            }
            return ui.returnResponse();
        } catch (MooMooException e) {
            return ui.printException(e);
        }
    }

    /**
     * Runs MooMoo.
     * @param args Argument values given when running the program
     */
    public static void main(String[] args) {
        new MooMoo().run();
    }
}
