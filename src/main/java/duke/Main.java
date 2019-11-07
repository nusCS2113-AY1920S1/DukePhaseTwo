package duke;

import duke.commons.LogsCenter;
import duke.logic.Logic;
import duke.logic.LogicManager;
import duke.model.DukePP;
import duke.model.Model;
import duke.storage.*;
import duke.storage.payment.PaymentListStorage;
import duke.storage.payment.PaymentListStorageManager;
import duke.ui.Ui;
import duke.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * Bridge between duke and MainWindow.
 */
public class Main extends Application {

    private static final Logger logger = LogsCenter.getLogger(Main.class);

    private Ui ui;
    private Logic logic;
    private Model model;
    private Storage storage;

    @Override
    public void init() throws Exception {
        super.init();

        ExpenseListStorage expenseListStorage = new ExpenseListStorageManager();
        PlanAttributesStorage planAttributesStorage = new PlanAttributesStorageManager();
        IncomeListStorage incomeListStorage = new IncomeListStorageManager();
        BudgetStorage budgetStorage = new BudgetStorage();
        BudgetViewStorage budgetViewStorage = new BudgetViewStorage();
        PaymentListStorage paymentListStorage = new PaymentListStorageManager();

        storage = new StorageManager(expenseListStorage,
                                     planAttributesStorage, 
                                     incomeListStorage, 
                                     budgetStorage,
                                     budgetViewStorage,
                                     paymentListStorage);

        logger.info("Initialized the storage");

        if(!storage.loadPaymentList().isPresent()) logger.info("PaymentList is not loaded");
        if(storage.loadExpenseList() == null) logger.info("expenseList is not loaded");
        if(storage.loadIncomeList() == null) logger.info("incomeList is not loaded");
        if(storage.loadBudget() == null) logger.info("budgetList is not loaded");
        model = new DukePP(storage.loadExpenseList(),
                storage.loadPlanAttributes(),
                storage.loadIncomeList(),
                storage.loadBudget(),
                storage.loadBudgetView(),
                storage.loadPaymentList());

        logger.info("Initialized the model");

        logic = new LogicManager(model, storage);

        logger.info("Initialized the logic");

        ui = new UiManager(logic);
        logger.info("Initialized the app");
    }


    /**
     * Starts Duke with MainWindow.
     *
     * @param primaryStage The main GUI of Duke
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        ui.start(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}