/*  Student information for assignment:
 *
 *  On my honor, Arnav Bhasin, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  Name: Arnav Bhasin
 *  email address: bhasin.arnav07@gmail.com
 *  UTEID: ab78845
 *  Section 5 digit ID: 52665
 *  Grader name: Brad
 *  Number of slip days used on this assignment: 2
 */

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;

/**
 * Manages the details of EvilHangman. This class keeps
 * tracks of the possible words from a dictionary during
 * rounds of hangman, based on guesses so far.
 *
 */
public class HangmanManager {

    // dictionary of all words and phrases for this instance of HangmanManager
    // This variable does not change at all and is fixed in all games played
    private final TreeSet<String> DICTIONARY;

    // debug state determining whether debug data is displayed for this instance of HangmanManager
    // This variable does not change at all and is fixed in all games played
    private final boolean DEBUG;

    // difficulty of this game
    // This variable only changes at the beginning of a new game
    private HangmanDifficulty difficulty;

    // maximum number of incorrect guesses in this game
    // This variable only changes at the beginning of a new game
    private int maxWrongGuesses;

    // number of incorrect guesses made so far in current game
    // This variable changes throughout the course of the game
    private int currentWrongGuesses;

    // letters guessed so far in this game of evil hangman
    // This variable changes throughout the course of the game of evil hangman
    private TreeSet<Character> lettersGuessed;

    // remaining words and phrases possible depending on word length and letters guessed so far
    // This variable changes throughout the course of the game
    private ArrayList<String> currentLiveWords;

    // pattern of revealed and unknown letters so far in current game
    // This variable changes throughout the course of the game
    private StringBuilder pattern;

    /**
     * Create a new HangmanManager from the provided set of words and phrases.
     * @param words A set with the words for this instance of Hangman.
     * @param debugOn true if we should print out debugging to System.out.
     */
    // constructs a HangmanManager object, taking in set of words for a dictionary and debug state
    public HangmanManager(Set<String> words, boolean debugOn) {
        DICTIONARY = new TreeSet<String>(words);
        DEBUG = debugOn;
    }

    /**
     * Create a new HangmanManager from the provided set of words and phrases.
     * Debugging is off.
     * pre: words != null, words.size() > 0
     * @param words A set with the words for this instance of Hangman.
     */
    // constructs a HangmanManager object, taking in set of words for a dictionary
    // preconditions: parameter words is not null and its size is greater than zero
    public HangmanManager(Set<String> words) {
        if (words == null || words.size() <= 0) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DICTIONARY = new TreeSet<String>(words);
        DEBUG = false;
    }

    /**
     * Get the number of words in this HangmanManager of the given length.
     * pre: none
     * @param length The given length to check.
     * @return the number of words in the original Dictionary
     * with the given length
     */
    // takes in parameter length, returns number of words from original dictionary with given
    // length
    public int numWords(int length) {
        int count = 0;
        for (String word : DICTIONARY) {
            if (word.length() == length) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get for a new round of Hangman. Think of a round as a
     * complete game of Hangman.
     * @param wordLen the length of the word to pick this time.
     * numWords(wordLen) > 0
     * @param numGuesses the number of wrong guesses before the
     * player loses the round. numGuesses >= 1
     * @param diff The difficulty for this round.
     */
    // takes in parameters for word length, number of incorrect guesses, and game
    // difficulty, initializes all instance variables for new game
    // preconditions: number of words in dictionary with parameter wordLen is greater than zero
    // and parameter numGuesses is greater than or equal to one
    public void prepForRound(int wordLen, int numGuesses, HangmanDifficulty diff) {
        if (numWords(wordLen) <= 0 || numGuesses < 1) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        currentLiveWords = new ArrayList<String>();
        // eliminates all words that are not of given length from possibility
        for (String word : DICTIONARY) {
            if (word.length() == wordLen) {
                currentLiveWords.add(word);
            }
        }
        maxWrongGuesses = numGuesses;
        currentWrongGuesses = 0;
        difficulty = diff;
        lettersGuessed = new TreeSet<Character>();
        // creates initial pattern of dashes
        pattern = new StringBuilder();
        for (int i = 0; i < wordLen; i++) {
            pattern.append('-');
        }
    }

    /**
     * The number of words still possible (live) based on the guesses so far.
     *  Guesses will eliminate possible words.
     * @return the number of words that are still possibilities based on the
     * original dictionary and the guesses so far.
     */
    // returns number of remaining words and phrases still possible
    public int numWordsCurrent() {
        return currentLiveWords.size();
    }

    /**
     * Get the number of wrong guesses the user has left in
     * this round (game) of Hangman.
     * @return the number of wrong guesses the user has left
     * in this round (game) of Hangman.
     */
    // returns number of remaining incorrect guesses
    public int getGuessesLeft() {
        return maxWrongGuesses - currentWrongGuesses;
    }

    /**
     * Return a String that contains the letters the user has guessed
     * so far during this round.
     * The characters in the String are in alphabetical order.
     * The String is in the form [let1, let2, let3, ... letN].
     * For example [a, c, e, s, t, z]
     * @return a String that contains the letters the user
     * has guessed so far during this round.
     */
    // returns string with list of all letters guessed so far
    public String getGuessesMade() {
        if (DEBUG) {
            System.out.println("DEBUG: " + lettersGuessed.toString());
        }
        return lettersGuessed.toString();
    }

    /**
     * Check the status of a character.
     * @param guess The character to check.
     * @return true if guess has been used or guessed this round of Hangman,
     * false otherwise.
     */
    // takes in parameter guess, returns whether or not guess has already been made
    public boolean alreadyGuessed(char guess) {
        for (char letter : lettersGuessed) {
            if (letter == guess) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the current pattern. The pattern contains '-''s for
     * unrevealed (or guessed) characters and the actual character 
     * for "correctly guessed" characters.
     * @return the current pattern.
     */
    // returns string with pattern of letters revealed so far
    public String getPattern() {
        return pattern.toString();
    }

    /**
     * Update the game status (pattern, wrong guesses, word list),
     * based on the give guess.
     * @param guess pre: !alreadyGuessed(ch), the current guessed character
     * @return return a tree map with the resulting patterns and the number of
     * words in each of the new patterns.
     * The return value is for testing and debugging purposes.
     */
    // takes in parameter guess, returns TreeMap of various patterns that can be made from
    // guess and lengths of word families that each pattern points to
    // preconditions: parameter guess has not already been made
    public TreeMap<String, Integer> makeGuess(char guess) {
        if (alreadyGuessed(guess)) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        lettersGuessed.add(guess);
        // builds TreeMap of patterns and their respective word families
        TreeMap<String, ArrayList<String>> patternMap = buildMap(guess);
        //builds TreeMap of various family sizes and all patterns whose families are of that size
        TreeMap<Integer, TreeSet<String>> sizeMap = buildSizeMap(patternMap);
        // updates current pattern based on game difficulty
        checkDifficulties(sizeMap);
        // updates current possible words based on updated pattern
        currentLiveWords = patternMap.get(pattern.toString());
        // updates number of wrong guesses if updated pattern doesn't contain guess made
        if (pattern.toString().indexOf(guess) < 0) {
            currentWrongGuesses++;
        }
        TreeMap<String, Integer> resultant = new TreeMap<String, Integer>();
        for (Map.Entry<String, ArrayList<String>> entry : patternMap.entrySet()) {
            resultant.put(entry.getKey(), entry.getValue().size());
        }
        return resultant;
    }

    /**
     * Return the secret word this HangmanManager finally ended up
     * picking for this round.
     * If there are multiple possible words left one is selected at random.
     * <br> pre: numWordsCurrent() > 0
     * @return return the secret word the manager picked.
     */
    // returns the secret word for the game, picking one at random if there is more than one word
    // still possible
    // preconditions: number of words still possible is greater than zero
    public String getSecretWord() {
        if (numWordsCurrent() <= 0) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        int index = (int) (Math.random() * currentLiveWords.size());
        if (DEBUG) {
            System.out.println("DEBUG: " + currentLiveWords.get(index));
        }
        return currentLiveWords.get(index);
    }

    // -------------------------Helper Methods------------------------- \\
    // checks what difficulty game is and updates pattern based on difficulty and how many total
    // guesses have been made
    private void checkDifficulties(TreeMap<Integer, TreeSet<String>> sizeMap) {
        // magic number for which round to find second hardest family
        int everyNthRound;
        if (difficulty == HangmanDifficulty.HARD) {
            doHardest(sizeMap);
        }
        else if (difficulty == HangmanDifficulty.MEDIUM) {
            everyNthRound = 4;
            if (sizeMap.size() == 1 || lettersGuessed.size() % everyNthRound != 0) {
                doHardest(sizeMap);
            }
            else {
                doSecondHardest(sizeMap);
            }
        }
        else if (difficulty == HangmanDifficulty.EASY) {
            everyNthRound = 2;
            if (sizeMap.size() == 1 || lettersGuessed.size() % everyNthRound != 0) {
                doHardest(sizeMap);
            }
            else {
                doSecondHardest(sizeMap);
            }
        }
    }

    // looks at SizeMap (TreeMap of all family sizes and set of patterns whose word families are of
    // that size), and updates pattern instance field with that of largest family size, checking
    // tiebreakers in process
    private void doHardest(TreeMap<Integer, TreeSet<String>> sizeMap) {
        TreeSet<String> pickedPatterns = sizeMap.get(sizeMap.lastKey());
        pattern.delete(0, pattern.length());
        pattern.append(checkTiebreakers(pickedPatterns));
    }

    // looks at SizeMap (TreeMap of all family sizes and set of patterns whose word families are of
    // that size), and updates pattern instance field with that of second largest family size,
    // checking tiebreakers in process
    private void doSecondHardest(TreeMap<Integer, TreeSet<String>> sizeMap) {
        TreeSet<String> pickedPatterns = sizeMap.get(sizeMap.lowerKey(sizeMap.lastKey()));
        pattern.delete(0, pattern.length());
        pattern.append(checkTiebreakers(pickedPatterns));
    }

    // checks tiebreakers between patterns with same family size (number of revealed letters and
    // lexicographical order)
    private String checkTiebreakers(TreeSet<String> pickedPatterns) {
        String pat = pickedPatterns.first();
        for (String pick : pickedPatterns) {
            if (numRevealedLetters(pick) < numRevealedLetters(pat)) {
                pat = pick;
            }
        }
        return pat;
    }

    // counts the number of revealed letters in a pattern
    private int numRevealedLetters(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != '-') {
                count++;
            }
        }
        return count;
    }

    // builds a pattern for every word by checking where it contains guess and what characters are
    // already revealed
    private String buildPattern(String word, char guess) {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            // if word contains guess at this index, reveal it in new pattern
            if (word.charAt(i) == guess) {
                build.append(guess);
            }
            // if word doesn't contain guess at this index, keep whatever exists in current pattern
            else {
                String currentPattern = getPattern();
                build.append(currentPattern.charAt(i));
            }
        }
        return build.toString();
    }

    // builds map of all patterns and family of words that match that pattern
    private TreeMap<String, ArrayList<String>> buildMap(char guess) {
        TreeMap<String, ArrayList<String>> patternMap = new TreeMap<String, ArrayList<String>>();
        for (String word : currentLiveWords) {
            String wordPattern = buildPattern(word, guess);
            // if pattern exists, add the word to its family
            if (patternMap.containsKey(wordPattern)) {
                patternMap.get(wordPattern).add(word);
            }
            // if pattern doesn't exist, add it to the patternMap with a word family
            else {
                ArrayList<String> value = new ArrayList<String>();
                value.add(word);
                patternMap.put(wordPattern, value);
            }
        }
        return patternMap;
    }

    // builds map of all family sizes and set of patterns whose word families are of that size
    private TreeMap<Integer, TreeSet<String>> buildSizeMap(TreeMap<String,
            ArrayList<String>> patternMap) {
        TreeMap<Integer, TreeSet<String>> sizeMap = new TreeMap<Integer, TreeSet<String>>();
        for (Map.Entry<String, ArrayList<String>> entry : patternMap.entrySet()) {
            int valueSize = entry.getValue().size();
            // if size exists, add the pattern to its set
            if (sizeMap.containsKey(valueSize)) {
                sizeMap.get(valueSize).add(entry.getKey());
            }
            // if size doesn't exist, add it to the sizeMap with a pattern set
            else {
                TreeSet<String> patternsPerSize = new TreeSet<String>();
                patternsPerSize.add(entry.getKey());
                sizeMap.put(valueSize, patternsPerSize);
            }
        }
        return sizeMap;
    }
}