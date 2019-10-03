package owlmoney.logic.parser.saving;

import java.util.HashMap;

import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;

public abstract class ParseSaving {
    HashMap <String, String> savingsParameters = new HashMap<String, String>();
    ParseRawData parseRawData = new ParseRawData();
    String rawData;
    static final String[] SAVINGS_KEYWORD = new String[] {"/amount", "/income", "/name", "/newname"};
    static final String AMOUNT = "/amount";
    static final String INCOME = "/income";
    static final String NAME = "/name";
    static final String NEW_NAME = "/newname";
    ParseSaving(String data) {
        this.rawData = data;
    }

    public void fillHashTable() throws ParserException {
        savingsParameters.put(AMOUNT,
                parseRawData.extractParameter(rawData,AMOUNT, SAVINGS_KEYWORD));
        savingsParameters.put(INCOME,
                parseRawData.extractParameter(rawData,INCOME, SAVINGS_KEYWORD));
        savingsParameters.put(NAME,
                parseRawData.extractParameter(rawData,NAME, SAVINGS_KEYWORD));
        savingsParameters.put(NEW_NAME,
                parseRawData.extractParameter(rawData,NEW_NAME, SAVINGS_KEYWORD));
    }

    void checkIfDouble(String valueString) throws ParserException {
        try {
            Double value = Double.parseDouble(valueString);
        } catch (NumberFormatException e) {
            throw new ParserException("Income and amount can only be numbers with at most 2 decimal places");
        }
    }

    abstract void checkParameter() throws ParserException;
}
