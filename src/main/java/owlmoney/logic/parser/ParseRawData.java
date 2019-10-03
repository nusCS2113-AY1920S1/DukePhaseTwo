package owlmoney.logic.parser;

import java.util.ArrayList;

import owlmoney.logic.parser.exception.ParserException;

public class ParseRawData {
    private static final int NEXT_INDEX = 1;

    public String extractParameter(String data, String keyword, String[] keywordList) throws ParserException {
        String[] splitTypeSpecificArguments = data.split(" ");
        String parameter = "";
        checkDuplicateKeywords(splitTypeSpecificArguments, keywordList);

        int startIndex = findStartIndex(splitTypeSpecificArguments, keyword);
        if(startIndex >= 0) {
            int endIndex = findEndIndex(startIndex, splitTypeSpecificArguments, keywordList);
            parameter = concatenateParameter(startIndex, endIndex, splitTypeSpecificArguments);
        }
        return parameter;
    }


    private void checkDuplicateKeywords(String[] splitArray, String[] keywordList) throws ParserException{
        for (int i = 0; i < keywordList.length; i++) {
            checkEachKeyword(keywordList[i], splitArray);
        }
    }

    private void checkEachKeyword(String keyword, String[] splitArray) throws ParserException {
        int keywordCounter = 0;
        for (int i = 0; i < splitArray.length; i++) {
            if (splitArray[i].equals(keyword)) {
                keywordCounter++;
            }
            if (keywordCounter > 1) {
                throw new ParserException("Duplicate " + keyword + " found!");
            }
        }
    }

    private int findStartIndex(String[] splitTypeSpecificArguments, String keyword) {
        for (int i = 0; i < splitTypeSpecificArguments.length; i++) {
            if (splitTypeSpecificArguments[i].equals(keyword)) {
                return i;
            }
        }
        return -1;
    }

    private int findEndIndex(int startIndex, String[] splitTypeSpecificArguments, String[] keywordList) {
        for (int i = startIndex + NEXT_INDEX; i < splitTypeSpecificArguments.length; i++) {
            for (int k = 0; k < keywordList.length; k++) {
                if (splitTypeSpecificArguments[i].equals(keywordList[k])) {
                    return i;
                }
            }
        }
        return splitTypeSpecificArguments.length;
    }

    private String concatenateParameter(int startIndex, int endIndex, String[] splitTypeSpecificArguments) {
        StringBuilder individualParameter = new StringBuilder();
        for (int i = startIndex + NEXT_INDEX; i < endIndex; i++) {
            if (individualParameter.toString().equals("")) {
                individualParameter.append(splitTypeSpecificArguments[i]);
            } else {
                individualParameter.append(" ").append(splitTypeSpecificArguments[i]);
            }
        }
        return individualParameter.toString();
    }

}
