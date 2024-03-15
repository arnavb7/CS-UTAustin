import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


// class to test HangmanManager
public class EvilHangmanAutoTester {

    private static final String dataDir = ""; // "../../data/";
    private static final String DICTIONARY_FILE = dataDir + "dictionary.txt";
    private static final String TEST_FILE_NAME = dataDir + "evilTest_STUDENT_VERSION.eht";
    private static boolean DEBUG = true;

    // Runs tests on Evil Hangman Manager object.
    // Text file with dictionary and expected results must be in
    // the current working directory for this program to function correctly.
    public static void main(String[] args) {
        TestCasesData testCaseData = new TestCasesData();
        Set<String> dictionary = getDictionary();
        HangmanManager studentHangmanManager = new HangmanManager(dictionary, false);

        try {
            ObjectInputStream reader
                    = new ObjectInputStream(new FileInputStream(new File(TEST_FILE_NAME)));
            int numTests = reader.readInt();
            System.out.println("Number of rounds of game to play: " + numTests);

            // loop through and perform each test
            for (int i = 0; i < numTests; i++) {
                runTest(studentHangmanManager, reader, testCaseData);
            }
            showResults(testCaseData);
            reader.close();
        } catch (FileNotFoundException e) {
            handleFileNotFoundExceptionInfo(e);
        } catch(IOException e) {
            handleIOExceptionInfo(e);
        } catch(ClassNotFoundException e) {
            handleClassNotFoundExceptionInfo(e);
        }
    }

    // show results of test cases
    private static void showResults(TestCasesData testCaseData) {
        // print results
        System.out.println("\ntotal test cases: " + testCaseData.testCases);
        System.out.println("number of test cases failed: " + testCaseData.failedTestCases);
    }


    // Show details of FileNotFoundException.
    // Likely due to data files not being in the current working directory.,\]
    private static void handleFileNotFoundExceptionInfo(FileNotFoundException e) {
        System.out.println("\nError in trying to read from file.");
        System.out.println("A file could not be found in the current working directory.");
        System.out.println("File name: " + TEST_FILE_NAME);
        System.out.println("Error: " + e);
        System.out.println("Current directory is " + System.getProperty("user.dir"));
        System.out.println();
        System.out.println("If the file is not found, try placing it in the above directory.");
        System.out.println("Ending program.");
        e.printStackTrace(System.out);
    }

    // Show details of ClassNotFoundException that could be generated when reading in
    // Serialized version of classes. Should not be an issue because we are reading
    // in a Java Map.
    private static void handleClassNotFoundExceptionInfo(ClassNotFoundException e) {
        System.out.println("\nError in trying to read and process automatic tests");
        System.out.println("Read in a class that could not be found.");
        System.out.println();
        System.out.println("for the Evil Hangman program.");
        System.out.println("File name: " + TEST_FILE_NAME);
        System.out.println("Error: " + e);
        System.out.println("Ending program.");
        e.printStackTrace(System.out);
    }

    // Show details of IOException that could be generated. Likely due to
    // error in an early test going too long or short
    private static void handleIOExceptionInfo(IOException e) {
        System.out.println("\nError in trying to read and process automatic tests");
        System.out.println("for the Evil Hangman program.");
        System.out.println("Error: " + e);
        System.out.println("**** THIS ERROR IS LIKELY DUE TO ERRORS IN AN EARLIER ROUND ****");
        System.out.println("**** CHECK REAULTS FOR EARLIER ROUND ****");
        System.out.println("**** AND EARLY ROUNF MAY HAVE ENDED TOO SOON OR GONE TOO LONG ****");
        System.out.println();
        System.out.println("Ending program.");
        e.printStackTrace(System.out);
    }

    // Run one complete round of Evil Hangman.
    private static void runTest(HangmanManager manager,
                                ObjectInputStream reader, TestCasesData testCaseData)
            throws IOException, ClassNotFoundException {

        // read in initial conditions for the test
        // TODO for Mike - create a class to store all this data
        int roundNumber = reader.readInt();
        int wordLength = reader.readInt();
        int numGuessesAllowed = reader.readInt();
        String actualGuesses = (String) reader.readObject();
        int intDifficulty = reader.readInt(); // original tests 1 indexed, not altered yet.
        int initialNumberOfWords = reader.readInt();
        String initialPattern = (String) reader.readObject();
        HangmanDifficulty difficulty = HangmanDifficulty.HARD; // default
        if (validDifficulty(intDifficulty)) {
            difficulty = HangmanDifficulty.values()[intDifficulty];
        }

        if (DEBUG) {
            showInitialData(roundNumber, wordLength, numGuessesAllowed,
                    actualGuesses, difficulty, initialNumberOfWords,
                    initialPattern);
        }

        // get ready for the round!
        manager.prepForRound(wordLength, numGuessesAllowed, difficulty);
        checkInitialConditions(manager, roundNumber, initialNumberOfWords, initialPattern,
                testCaseData);
        // run the actual guesses
        runRound(manager, reader, roundNumber, actualGuesses, testCaseData);
    }


    // check that initial number of words and pattern match
    // TODO Note for Mike. This method is too long, need to break up.
    private static void checkInitialConditions(HangmanManager manager,
                                               int testNumber, int initialNumberOfWords,
                                               String initialPattern, TestCasesData testCaseData) {

        testCaseData.testCases++;
        if (initialNumberOfWords == manager.numWordsCurrent()) {
            if (DEBUG) {
                System.out.println("\nRound number " + testNumber +
                        " - passed test - initial number of words");
            }
        } else {
            if (DEBUG) {
                System.out.println("\nRound number " + testNumber +
                        " - FAILED TEST - initial number of words");
                System.out.println("Expected: " + initialNumberOfWords);
                System.out.println("Actual: " + manager.numWordsCurrent());
            }
            testCaseData.failedTestCases++;
        }

        testCaseData.testCases++;
        if (initialPattern.trim().equals(manager.getPattern().trim())) {
            if (DEBUG)
                System.out.println("Round number " + testNumber +
                        " - passed test - initial pattern");
        } else {
            if (DEBUG) {
                System.out.println("Round number " + testNumber +
                        " - FAILED TEST - initial pattern");
                System.out.println("Expected: " + initialNumberOfWords);
                System.out.println("Actual: " + manager.numWordsCurrent());
            }
            testCaseData.failedTestCases++;
        }
    }


    @SuppressWarnings("unchecked")
    private static void runRound(HangmanManager manager, ObjectInputStream reader,
                                 int roundNumber, String actualGuesses, TestCasesData testCaseData)
            throws IOException, ClassNotFoundException {

        // run a complete round / game
        // number of guesses = actualGuesses.length()
        // make a guess, compare expected map to actual map,

        int guessNum = 0;
        while (guessNum < actualGuesses.length() && manager.getPattern().contains("-")) {
            char ch = actualGuesses.charAt(guessNum);
            if (DEBUG)
                System.out.println("\nRound Number: " + roundNumber
                        + ", guess number: " + (guessNum + 1) + ", guessed char: " + ch);

            // read in expected results
            Map<String, Integer> expectedMap = (Map<String, Integer>) reader.readObject();

            expectedMap = buildMapNoSpaces(expectedMap);
            int expectedWordsLeft = reader.readInt();
            String expectedPattern = removeSpaces((String) reader.readObject());

            // make guess and get actual results;
            Map<String, Integer> actualMap = manager.makeGuess(ch);
            actualMap = buildMapNoSpaces(actualMap);
            int actualWordsLeft = manager.numWordsCurrent();
            String actualPattern = removeSpaces(manager.getPattern());

            if (DEBUG) {
                showResults(ch, expectedMap, actualMap,
                        expectedPattern, actualPattern, expectedWordsLeft, actualWordsLeft);
            }

            // compare expected and actual results for correctness
            checkMapOfPatternsFromGuess(expectedMap, actualMap, testCaseData);

            checkNumberOfWordsLeftAfterGuess(manager, expectedWordsLeft,
                    actualWordsLeft, testCaseData);

            checkNewPatternAfterGuess(manager, expectedPattern, actualPattern, testCaseData);
            testCaseData.testCases += 3; // for map of patterns after guess, number of words left after guess, and new pattern
            guessNum++;
        }
    }

    // Check if the new pattern matches the expected pattern after a guess is made.
    private static void checkNewPatternAfterGuess(HangmanManager manager,
                                                  String expectedPattern, String actualPattern, TestCasesData testCaseData) {

        if (!expectedPattern.equals(actualPattern)) {
            testCaseData.failedTestCases++;
            System.out.println("FAILED TEST CASE - NEW PATTERN AFTER GUESS INCORRECT");
            System.out.println("EXPECTED: " + expectedPattern);
            System.out.println("ACTUAL: " + manager.getPattern());
        }
    }

    // Check if the number of words left after a guess matches the expected
    // number of words left. This checks the splitting algorithm in
    // EvilHangmanManager.
    private static void checkNumberOfWordsLeftAfterGuess(
            HangmanManager manager, int expectedWordsLeft,
            int actualWordsLeft, TestCasesData testCaseData) {

        if (expectedWordsLeft != actualWordsLeft ) {
            testCaseData.failedTestCases++;
            System.out.println("FAILED TEST CASE - NEW NUMBER OF CURRENT WORDS AFTER "
                    + "GUESS INCORRECT");
            System.out.println("EXPECTED: " + expectedWordsLeft);
            System.out.println("ACTUAL: " + manager.numWordsCurrent() );
        }
    }

    // Check the map of patterns returned by EvilHangmanManager against
    // the expected map. This also checks the splitting algorithm.
    private static void checkMapOfPatternsFromGuess(
            Map<String, Integer> expectedMap, Map<String, Integer> actualMap,
            TestCasesData testCaseData) {

        // check map
        if (!expectedMap.equals(actualMap)) {
            testCaseData.failedTestCases++;
            System.out.println("FAILED TEST CASE - MAP OF WORDS PER PATTERN INCORRECT");
            System.out.println("EXPECTED: " + expectedMap);
            System.out.println("ACTUAL: " + actualMap);
        }
    }


    // For debugging. Shows expected and actual results.
    private static void showResults(char guess, Map<String, Integer> expectedMap,
                                    Map<String, Integer> actualMap, String expectedPattern,
                                    String actualPattern, int expectedWordsLeft, int actualWordsLeft) {

        System.out.println("Guessed char: " + guess);
        System.out.println("\nExpected patterns and frequencies: ");
        showMap(expectedMap);
        System.out.println("\nActual patterns and frequencies:");
        showMap(actualMap);
        System.out.println("\nExpected new pattern: " + expectedPattern);
        System.out.println("Actual new pattern: " + actualPattern);
        System.out.println("\nExpected new number of words: " + expectedWordsLeft);
        System.out.println("Actual new number of words: " + actualWordsLeft);

    }

    // Method to check if an int value is a valid Diffilty based on Enum.
    private static boolean validDifficulty(int intDifficulty) {
        return 0 <= intDifficulty && intDifficulty < HangmanDifficulty.values().length;
    }


    // print conditions for a given test
    private static void showInitialData(int roundNumber, int wordLength,
                                        int numGuessesAllowed, String actualGuesses, HangmanDifficulty difficulty,
                                        int initialNumberOfWords, String initialPattern) {

        System.out.println("\n\n******** NEW ROUND ********");
        System.out.println("Conditions for round number " + roundNumber);
        System.out.println("word length: " + wordLength);
        System.out.println("number of guesses allowed: " + numGuessesAllowed);
        System.out.print("guesses in order made: ");
        for (int i = 0; i < actualGuesses.length(); i++) {
            System.out.print(actualGuesses.charAt(i) + " ");
        }
        System.out.println("\nExpected initial number of words: " + initialNumberOfWords);
        System.out.println("Expected initial pattern(should be all dashes): " + initialPattern);
        System.out.print("Difficulty: " + difficulty);
    }

    // Build the the map and remove all spaces.
    private static Map<String, Integer> buildMapNoSpaces( Map<String, Integer> stu) {
        TreeMap<String, Integer> result = new TreeMap<>();
        for (String pattern : stu.keySet()) {
            String newKey = removeSpaces(pattern);
            result.put(newKey, stu.get(pattern));
        }
        return result;
    }

    // Display a map to standard output.
    private static void showMap(Map<String, Integer> map) {
        for(String pattern : map.keySet())
            System.out.println(pattern + " " + map.get(pattern));
    }


    // Open the dictionary file. Return a list containing
    // the words in the dictionary file.
    // If the dictionary file is not found the program ends.
    private static Set<String> getDictionary() {
        Set<String> dictionary = new TreeSet<>();
        Scanner input = null;
        try {
            input = new Scanner(new File(DICTIONARY_FILE));
            while (input.hasNext()) {
                dictionary.add(input.next().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
            System.out.println("Exiting");
            System.exit(-1);
        } finally {
            if(input != null)
                input.close();
        }

        return Collections.unmodifiableSet(dictionary);
    }

    // we use this to avoid creating too many garbage objects
    private static StringBuilder sb = new StringBuilder();

    // Remove all spaces from a String.
    private static String removeSpaces(String s) {
        sb.setLength(0);
        s = s.trim();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    // Simple pair to store total number of test cases and number of failed test cases.
    private static class TestCasesData {
        private int testCases;
        private int failedTestCases;
    }
}