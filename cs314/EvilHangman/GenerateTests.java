import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/* Use this class to generate new tests.
 * Reads from file designated as TEST_FILE_NAME.
 * Format of tester file shall be
 * <NUM_TESTS>
 * <WORD_LENGTH> <NUM_GUESSES> <ORDINAL FOR DIFFICULTY> <GUESSES>
 * There are assumed to be NUM_TEST lines after the first line of the file.
 * Each designates the conditions for one round.
 * Change the data in the tester file or create a new tester file to
 * generate new tests based on your HangmanManger. Share the generated
 * test file with others to see if they agree with your results.
 * Generates an eht file which can be used by EvilHangmanAuto tester.
 */


public class GenerateTests {

    private static final String DICTIONARY_FILE = "dictionary.txt";
    private static final String OUTPUT_FILE = "evilTest_STUDENT_VERSION.eht";
    private static final String TEST_FILE_NAME = "studentTestData.txt";

    public static void main(String[] args) {
        try {
            ObjectOutputStream os
                    = new ObjectOutputStream(new FileOutputStream(new File(OUTPUT_FILE)));

            Scanner sc = new Scanner(new File(TEST_FILE_NAME));

            HangmanManager hm
                    = new HangmanManager(getDictionary());

            final int NUM_TESTS = sc.nextInt();
            os.writeInt(NUM_TESTS);
            for (int i = 0; i <NUM_TESTS; i++) {
                int wordLen = sc.nextInt();
                int numGuesses = sc.nextInt();
                HangmanDifficulty diff = HangmanDifficulty.values()[sc.nextInt()];
                String guesses = sc.next();
                runTest(i + 1, hm, os, wordLen, numGuesses, diff, guesses);
            }
            sc.close();
            os.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private static void runTest(int testNumber, HangmanManager hm,
                                ObjectOutputStream os, int wordLen, int numGuesses,
                                HangmanDifficulty diff, String guesses) throws IOException {

        // file format for one test
        // header
        // testNumber int
        // wordLength int
        // numGuesses int
        // guesses String
        // difficulty int
        // current words int
        // start pattern String

        // Then for each guess
        // map of pattern - frequency Map<String, Integer>
        // current words int
        // pattern String

        // initial data
        os.writeInt(testNumber);
        os.writeInt(wordLen);
        os.writeInt(numGuesses);
        os.writeObject(guesses);
        os.writeInt(diff.ordinal());

        // get ready to make guesses
        hm.prepForRound(wordLen, numGuesses, diff);
        os.writeInt(hm.numWordsCurrent());
        os.writeObject(hm.getPattern());

        // make guesses and write results
        // Important, we ignore guesses after test results in win.
        int guessNum = 0;
        while (guessNum < guesses.length() && hm.getPattern().contains("-")) {
            char guess = guesses.charAt(guessNum);
            Map<String, Integer> result = hm.makeGuess(guess);

            // check if ties occur
            if (tieForMax(result)) {
                System.out.println("FYI - Tie in test case. Tie break algorithm will be tested.");
                System.out.println("test num: " + testNumber);
                System.out.println("word length: " + wordLen);
                System.out.println("guesses: " + guesses);
                System.out.println("guess: " + guess);
                System.out.println("guess number: " + (guessNum + 1));
            }

            os.writeObject(result);
            os.writeInt(hm.numWordsCurrent());
            os.writeObject(hm.getPattern());
            guessNum++;
        }
    }

    private static boolean tieForMax(Map<String, Integer> result) {
        Collection<Integer> counts = result.values();
        Integer max = Collections.max(counts);
        return Collections.frequency(counts, max) > 1;
    }


    // open the dictionary file. Return a list containing 
    // the words in the dictionary file.
    // If the dictionary file is not found the program ends
    private static Set<String> getDictionary() {
        Set<String> dictionary = new TreeSet<>();
        try {
            Scanner input = new Scanner(new File(DICTIONARY_FILE));

            while (input.hasNext()) {
                dictionary.add(input.next().toLowerCase());
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
            System.out.println("Exiting");
            System.exit(-1);
        }
        return Collections.unmodifiableSet(dictionary);
    }

}