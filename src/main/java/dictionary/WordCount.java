package dictionary;

import exception.NoWordFoundException;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an object that keeps track of operations related to the number of searches on the words in the wordBank.
 */
public class WordCount {

    /**
     * Maps the search count (KEY) to an ordered list of words (VALUE) with that search count.
     */
    protected TreeMap<Integer, TreeMap<String, Word>> wordCount = new TreeMap<Integer, TreeMap<String, Word>>();

    public WordCount(WordBank wordBank) {
        makeWordCount(wordBank);
    }

    public TreeMap<Integer, TreeMap<String, Word>> getWordCount() {
        return wordCount;
    }

    /**
     * Instantiates the wordCount with words from the wordBank.
     * @param wordBank contains the list of words to add to wordCount
     */
    protected void makeWordCount(WordBank wordBank) {
        for (Map.Entry<String, Word> entry : wordBank.getWordBank().entrySet()) {
            //find key. if exists append to treemap, else create new hashmap entry
            int numberOfSearches = entry.getValue().getNumberOfSearches();
            String wordText = entry.getValue().getWord();
            Word wordWord = entry.getValue();
            if (!wordCount.isEmpty()) {
                if (wordCount.containsKey(numberOfSearches)) {
                    wordCount.get(numberOfSearches).put(wordText, wordWord);
                }
            } else {
                wordCount.put(numberOfSearches, new TreeMap<>());
                wordCount.get(numberOfSearches).put(wordText, wordWord);
            }
        }
    }

    /**
     * Increases the search count for a word.
     * @param searchTerm word that is being searched for by the user
     * @throws NoWordFoundException if word does not exist in the word bank
     */
    public void increaseSearchCount(String searchTerm, WordBank wordBank) throws NoWordFoundException {
        if (wordBank.getWordBank().containsKey(searchTerm)) {
            Word searchedWord = wordBank.getWordBank().get(searchTerm);
            deleteWordFromSearchCount(searchedWord); //delete the word from original search count treemap
            searchedWord.incrementNumberOfSearches(); //increase the search count for the word
            addWordToSearchCount(searchedWord); //add the word to the treemap with key to be the new search count
        } else {
            throw new NoWordFoundException(searchTerm);
        }
    }

    /**
     * Deletes a word from wordCount.
     * @param word the word to be deleted
     */
    public void deleteWordFromSearchCount(Word word) {
        int wordSearchCount = word.getNumberOfSearches();
        wordCount.get(wordSearchCount).remove(word.getWord());
        if (wordCount.get(wordSearchCount).isEmpty()) { //treemap is empty, delete key
            wordCount.remove(wordSearchCount);
        }
    }

    public void addWordToSearchCount(Word word) {
        int wordSearchCount = word.getNumberOfSearches();
        if (wordCount.containsKey(wordSearchCount)) {
            wordCount.get(wordSearchCount).put(word.getWord(), word); //add directly to existing treemap
        } else {
            wordCount.put(wordSearchCount, new TreeMap<>());
            wordCount.get(wordSearchCount).put(word.getWord(), word); //create new entry and add word to treemap
        }
    }

}