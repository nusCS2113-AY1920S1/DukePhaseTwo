package owlmoney.logic.regex;

/**
 * Regex is used to validate user input to make sure it satisfies constraints set for user input.
 */
public final class Regex {
    /**
     * Checks whether amount input by user is within the 9 digit constraints with a max of 2 decimal places.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckMoney(String input) {
        final String MONEY_REGEX = "^\\s*(?=.*[1-9])\\d{1,9}(\\.\\d{2})?$";
        return input.matches(MONEY_REGEX);
    }

    /**
     * Checks whether the interest rate entered is within the 100% limit.
     * The first line of checks determines whether it is a 3 digit number up to 2 decimal places.
     * The second line of checks determines whether it is less than 100%.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckInterestRate(String input) {
        final String INTEREST_REGEX = "^\\s*(?=.*[1-9])\\d{1,3}(\\.\\d{2})?$";
        if (input.matches(INTEREST_REGEX)) {
            double parsedInput = Double.parseDouble(input);
            return (parsedInput <= 100.00);
        } else {
            return false;
        }
    }

    /**
     * Checks whether the credit card cashback rate entered is within the 20% limit.
     * The first line of checks determines whether it is a 2 digit number up to 2 decimal places.
     * The second line of checks determines whether it is less than 20%.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckCashbackRate(String input) {
        final String INTEREST_REGEX = "^\\s*(?=.*[1-9])\\d{1,2}(\\.\\d{2})?$";
        if (input.matches(INTEREST_REGEX)) {
            double parsedInput = Double.parseDouble(input);
            return (parsedInput <= 20.00);
        } else {
            return false;
        }
    }

    /**
     * Checks whether input entered by user is numeric and is within the 9 digit constraints with no decimals.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckListNumber(String input) {
        final String LIST_REGEX = "^[1-9]\\d{0,8}$";
        return input.matches(LIST_REGEX);
    }

    /**
     * Checks whether input entered by user is alphanumeric with a maximum of 30 characters only.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckName(String input) {
        if (input.isBlank() || input.isEmpty()) {
            return false;
        }
        final String NAME_REGEX = "^[a-zA-Z0-9 ]{1,30}$";
        return input.matches(NAME_REGEX);
    }

    /**
     * Checks whether input entered by user is alphanumeric with a maximum of 50 characters only.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckDescription(String input) {
        if (input.isBlank() || input.isEmpty()) {
            return false;
        }
        final String DESCRIPTION_REGEX = "^[a-zA-Z0-9 ]{1,50}$";
        return input.matches(DESCRIPTION_REGEX);
    }

    /**
     * Checks whether user input days is less than or equal to 365 days limit set for short term goals.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckDays(String input) {
        final String DAYS_REGEX = "^[1-9]\\d{0,2}$";
        if (input.matches(DAYS_REGEX)) {
            int parsedDay = Integer.parseInt(input);
            return (parsedDay <= 365);
        } else {
            return false;
        }
    }

}
