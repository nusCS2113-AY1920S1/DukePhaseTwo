package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.model.payment.*;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Wraps all memory data of Duke++
 * Implements the interface of model module.
 */

public class DukePP implements Model {

    private static final Logger logger = LogsCenter.getLogger(DukePP.class);


    Predicate<Payment> PREDICATE_SHOW_ALL_PAYMENTS = unused -> true;

    private final ExpenseList expenseList;
    private final PlanBot planBot;
    private final IncomeList incomeList;
    private final Budget budget;
    private final BudgetView budgetView;
    private final PaymentList payments;
    // todo: add other data inside the DukePP.

    public ObservableList<Expense> externalExpenseList;
    public ObservableList<Income> externalIncomeList;


    /**
     * Creates a DukePP.
     * This constructor is used for loading DukePP from storage.
     */
    // todo: pass more arguments to constructor as more data are implemented.

    public DukePP(ExpenseList expenseList, Map<String, String> planAttributes, IncomeList incomeList, Budget budget, BudgetView budgetView, Optional<PaymentList> optionalPayments) throws DukeException {

        this.expenseList = expenseList;
        this.planBot = new PlanBot(planAttributes);
        this.incomeList = incomeList;
        this.budget = budget;
        this.budgetView = budgetView;

        if(!optionalPayments.isPresent()) {
            logger.warning("PaymentList is not loaded. It be starting with a empty PaymentList");
            this.payments = new PaymentList();
        } else {
            this.payments = optionalPayments.get();
        }
    }

    //******************************** ExpenseList operations

    public void addExpense(Expense expense) {
        expenseList.add(expense);
        logger.info("Model's expense externalList length now is "
                + externalExpenseList.size());
    }

    public void deleteExpense(int index) throws DukeException {
        expenseList.remove(index);
    }

    public void clearExpense() {
        expenseList.clear();
    }

    public void filterExpense(String filterCriteria) throws DukeException {
        expenseList.setFilterCriteria(filterCriteria);
    }

    public void sortExpense(String sortCriteria) throws DukeException {
        expenseList.setSortCriteria(sortCriteria);
    }

    public void viewExpense(String viewScope, int previous) throws DukeException {
        expenseList.setViewScope(viewScope, previous);
    }

    public ObservableList<Expense> getExpenseExternalList() {
        logger.info("Model sends external expense list length "
                + expenseList.getExternalList().size());
        externalExpenseList = FXCollections.unmodifiableObservableList(expenseList.getExternalList());
        return externalExpenseList;
    }

    /**
     * Returns the expenseList for storage.
     */
    public ExpenseList getExpenseList() {
        return expenseList;
    }

    public BigDecimal getTotalAmount() {
        return expenseList.getTotalAmount();
    }

    //******************************** Budget and budgetView operations

    @Override
    public StringProperty getExpenseListTotalString() {
        return expenseList.getTotalString();
    }

    @Override
    public StringProperty getSortCriteriaString() {
        return expenseList.getSortString();
    }

    @Override
    public StringProperty getViewCriteriaString() {
        return expenseList.getViewString();
    }

    @Override
    public StringProperty getFilterCriteriaString() {
        return expenseList.getFilterString();
    }

    @Override
    public String getMonthlyBudgetString() {
        return budget.getMonthlyBudgetString();
    }

    @Override
    public BigDecimal getMonthlyBudget() {
        return budget.getMonthlyBudget();
    }

    @Override
    public void setMonthlyBudget(BigDecimal monthlyBudget) {
        budget.setMonthlyBudget(monthlyBudget);
    }

    @Override
    public void setCategoryBudget(String category, BigDecimal budgetBD) {
        budget.setCategoryBudget(category, budgetBD);
    }

    @Override
    public BigDecimal getRemaining(BigDecimal total) {
        return budget.getRemaining(total);
    }

    @Override
    public Map<String, BigDecimal> getBudgetCategory() {
        return budget.getBudgetCategory();
    }

    @Override
    public Budget getBudget() {
        return budget;
    }

    @Override
    public BigDecimal getBudgetTag(String category) {
        return budget.getBudgetTag(category);
    }

    @Override
    public ObservableList<String> getBudgetObservableList() {
        return budget.getBudgetObservableList();
    }

    @Override
    public BudgetView getBudgetView() {
        return budgetView;
    }

    @Override
    public void setBudgetView (Integer view, String category) {
        budgetView.setBudgetView(view,category);
    }

    @Override
    public Map<Integer , String> getBudgetViewCategory() {
        return budgetView.getBudgetViewCategory();
    }


    //************************************************************
    // PlanBot operations

    public ObservableList<PlanBot.PlanDialog> getDialogObservableList() {
        return planBot.getDialogObservableList();
    }

    public void processPlanInput(String input) throws DukeException {
        planBot.processInput(input);
    }

    @Override
    public Map<String, String> getKnownPlanAttributes() {
        return planBot.getPlanAttributes();
    }
  
    @Override
    public PlanQuestionBank.PlanRecommendation getRecommendedBudgetPlan() {
        return planBot.getPlanBudgetRecommendation();
    }

    //************************************************************ IncomeList operations
    public void addIncome(Income income) {
        incomeList.add(income);
        logger.info("Model's income externalList length now is "
                + externalIncomeList.size());
    }

    public void deleteIncome(int index) throws DukeException {
        incomeList.remove(index);
    }

    public void clearIncome() {
        incomeList.clear();
    }

    public void filterIncome(String filterCriteria) throws DukeException {
        expenseList.setFilterCriteria(filterCriteria);
    }

    public void sortIncome(String sortCriteria) throws DukeException {
        expenseList.setSortCriteria(sortCriteria);
    }

    public void viewIncome(String viewScope, int previous) throws DukeException {
        expenseList.setViewScope(viewScope, previous);
    }

    public ObservableList<Income> getIncomeExternalList() {
        logger.info("Model sends external income list length "
                + incomeList.getExternalList().size());
        externalIncomeList = FXCollections.unmodifiableObservableList(incomeList.getExternalList());
        return externalIncomeList;
    }

    public IncomeList getIncomeList() {
        return incomeList;
    }


    //************************************************************
    // Pending Payments operations

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void setPayment(int index, Payment editedPayment) throws DukeException {
        payments.setPayment(index, editedPayment);
    }

    public void removePayment(int index) throws DukeException {
        payments.remove(index);
    }

    public void setPaymentSortCriteria(String sortCriteria) throws DukeException {
        payments.setSortCriteria(sortCriteria);
    }

    public void setAllPredicate() {
        payments.setPredicate(PREDICATE_SHOW_ALL_PAYMENTS);
    }

    public void setMonthPredicate() {
        PaymentInMonthPredicate monthPredicate = new PaymentInMonthPredicate();
        payments.setPredicate(monthPredicate);
    }

    public void setWeekPredicate() {
        PaymentInWeekPredicate weekPredicate = new PaymentInWeekPredicate();
        payments.setPredicate(weekPredicate);
    }

    public void setOverduePredicate() {
        PaymentOverduePredicate overduePredicate = new PaymentOverduePredicate();
        payments.setPredicate(overduePredicate);
    }

    public void setSearchKeyword(String keyword) {
        payments.setSearchPredicate(keyword);
    }

    public Payment getPayment(int index) throws DukeException {
        return payments.getPayment(index);
    }

    public FilteredList<Payment> getFilteredPaymentList() {
        return payments.getFilteredList();
    }

    /*
    public FilteredList<Payment> getSearchResult() {
        return payments.getSearchResult();
    }
     */

    /**
     * Returns the paymentList itself for storage update ONLY.
     *
     * @return the paymentList
     */
    public PaymentList getPaymentList() {
        return payments;
    }

    @Override
    public ObservableList<String> getSortIndicator() {
        return payments.getSortIndicator();
    }

    @Override
    public ObservableList<Predicate<Payment>> getPredicateIndicator() {
        return payments.getPredicateIndicator();
    }

    /*
    @Override
    public ObservableList<String> getSearchKeywordIndicator() {
        return payments.getSearchKeywordIndicator();
    }
     */


    //    todo: add other data operations

}