//  CodeCamp.java - CS314 Assignment 1

/*  Student information for assignment:
 *
 *  replace <NAME> with your name.
 *
 *  On my honor, Arnav Bhasin, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  Name: Arnav Bhasin
 *  email address: bhasin.arnav07@gmail.com
 *  UTEID: ab78845
 *  Section 5 digit ID: 52665
 *  Grader name: Brad
 *  Number of slip days used on this assignment: 0
 */

import java.util.Random;

public class CodeCamp<value> {

    /**
     * Determine the Hamming distance between two arrays of ints.
     * Neither the parameter <tt>aData</tt> or
     * <tt>bData</tt> are altered as a result of this method.<br>
     * @param aData != null, aData.length == bData.length
     * @param bData != null
     * @return the Hamming Distance between the two arrays of ints.
     */
    public static int hammingDistance(int[] aData, int[] bData) {
        // check preconditions
        if (aData == null || bData == null || aData.length != bData.length) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "hammingDistance. neither parameter may equal null, arrays" +
                    " must be equal length.");
        }

//      My technique here is to count how many times the elements of the arrays are
//      different at each individual index and then return that count.
        int hamCount = 0;
        for (int i = 0; i < aData.length; i++) {
            if (aData[i] != bData[i]) {
                hamCount++;
            }
        }
        return hamCount;
    }

    /**
     * Determine if one array of ints is a permutation of another.
     * Neither the parameter <tt>aData</tt> or
     * the parameter <tt>bData</tt> are altered as a result of this method.<br>
     * @param aData != null
     * @param bData != null
     * @return <tt>true</tt> if aData is a permutation of bData,
     * <tt>false</tt> otherwise
     *
     */
    public static boolean isPermutation(int[] aData, int[] bData) {
        // check preconditions
        if (aData == null || bData == null) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "isPermutation. neither parameter may equal null.");
        }

//      My technique here is to sort the data arrays by value and then check their equivalence,
//      as two permutations of each other have the same combination of values.
        if (aData.length != bData.length) {
            return false;
        }
        int[] newAData = new int[aData.length];
        int[] newBData = new int[bData.length];
        for (int i = 0; i < aData.length; i++) {
            newAData[i] = aData[i];
        }
        for (int i = 0; i < bData.length; i++) {
            newBData[i] = bData[i];
        }
        simpleSelectionSort(newAData);
        simpleSelectionSort(newBData);
        for (int i = 0; i < newAData.length; i++) {
            if (newAData[i] != newBData[i]) {
                return false;
            }
        }
        return true;
    }

//  Helper method for "isPermutation": Simple selection sort algorithm for integer arrays,
//  sorting them by numerical value
    private static void simpleSelectionSort(int[] dataArray) {
        int lowValueIndex;
        int lowestValue;
        for (int i = 0; i < dataArray.length - 1; i++) {
            lowValueIndex = i;
            lowestValue = dataArray[i];
            for (int j = i + 1; j < dataArray.length; j++) {
                if (dataArray[j] < lowestValue) {
                    lowValueIndex = j;
                    lowestValue = dataArray[j];
                }
            }
            dataArray[lowValueIndex] = dataArray[i];
            dataArray[i] = lowestValue;
        }
    }

    /**
     * Determine the index of the String that
     * has the largest number of vowels.
     * Vowels are defined as <tt>'A', 'a', 'E', 'e', 'I', 'i', 'O', 'o',
     * 'U', and 'u'</tt>.
     * The parameter <tt>arrayOfStrings</tt> is not altered as a result of this method.
     * <p>pre: <tt>arrayOfStrings != null</tt>, <tt>arrayOfStrings.length > 0</tt>,
     * there is an least 1 non null element in arrayOfStrings.
     * <p>post: return the index of the non-null element in arrayOfStrings that has the
     * largest number of characters that are vowels.
     * If there is a tie return the index closest to zero.
     * The empty String, "", has zero vowels.
     * It is possible for the maximum number of vowels to be 0.<br>
     * @param arrayOfStrings the array to check
     * @return the index of the non-null element in arrayOfStrings that has
     * the largest number of vowels.
     */
    public static int mostVowels(String[] arrayOfStrings) {
        // check preconditions
        if (arrayOfStrings == null || arrayOfStrings.length == 0
                || !atLeastOneNonNull(arrayOfStrings)) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "mostVowels. parameter may not equal null and must contain " +
                    "at least one none null value.");
        }

//      My technique here is to split each word string at the vowels into multiple substrings and
//      count how many splits there are to get the number of vowels, then finally
//      returning the index of the string with the most vowels.
        int[] numVowels = new int[arrayOfStrings.length];
        for (int i = 0; i < arrayOfStrings.length; i++) {
            if (arrayOfStrings[i] == null) {
                numVowels[i] = -1;
                continue;
            }
            if (arrayOfStrings[i].equals("")) {
                numVowels[i] = 0;
                continue;
            }
            int count = 0;
            String[] word = arrayOfStrings[i].split("[AaEeIiOoUu]");
            numVowels[i] = word.length - 1;
        }
        int highestVowels = numVowels[0];
        int index = 0;
        for (int i = 0; i < numVowels.length; i++) {
            if (numVowels[i] > highestVowels) {
                highestVowels = numVowels[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * Perform an experiment simulating the birthday problem.
     * Pick random birthdays for the given number of people.
     * Return the number of pairs of people that share the
     * same birthday.<br>
     * @param numPeople The number of people in the experiment.
     * This value must be > 0
     * @param numDaysInYear The number of days in the year for this experiement.
     * This value must be > 0
     * @return The number of pairs of people that share a birthday
     * after running the simulation.
     */
    public static int sharedBirthdays(int numPeople, int numDaysInYear) {
        // check preconditions
        if (numPeople <= 0 || numDaysInYear <= 0) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "sharedBirthdays. both parameters must be greater than 0. " +
                    "numPeople: " + numPeople +
                    ", numDaysInYear: " + numDaysInYear);
        }

//      My technique here is to first count the repeats of birthdays and store it in a separate
//      array whose length is the number of days in the year, then computing the pairs from
//      each repeat count and returning the sum of all possible pairs.
        int[] variousBirthdays = new int[numPeople];
        int[] frequenciesOfBirthdays = new int[numDaysInYear];
        for (int i = 0; i < numPeople; i++) {
            variousBirthdays[i] = ((int) (Math.random() * numDaysInYear));
            frequenciesOfBirthdays[variousBirthdays[i]]++;
        }
        int numPairs = 0;
        for (int i = 0; i < frequenciesOfBirthdays.length; i++) {
            if (frequenciesOfBirthdays[i] > 1) {
                numPairs += getPairs(frequenciesOfBirthdays[i]);
            }
        }
        return numPairs;
    }

    //  Helper method for "sharedBirthdays": Simple algorithm to return possible pairs
    //  given n distinct objects, which works by computing the number of pairs through adding the
    //  previous number of pairs and the previous number of people until
    //  reaching the number of distinct objects n
    private static int getPairs(int n) {
        int sum = 0;
        for (int i = 1; i < n; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * Determine if the chess board represented by board is a safe set up.
     * <p>pre: board != null, board.length > 0, board is a square matrix.
     * (In other words all rows in board have board.length columns.),
     * all elements of board == 'q' or '.'. 'q's represent queens, '.'s
     * represent open spaces.<br>
     * <p>post: return true if the configuration of board is safe,
     * that is no queen can attack any other queen on the board.
     * false otherwise.
     * the parameter <tt>board</tt> is not altered as a result of
     * this method.<br>
     * @param board the chessboard
     * @return true if the configuration of board is safe,
     * that is no queen can attack any other queen on the board.
     * false otherwise.
     */
    public static boolean queensAreSafe(char[][] board) {
        char[] validChars = {'q', '.'};
        // check preconditions
        if (board == null || board.length == 0 || !isSquare(board)
                || !onlyContains(board, validChars)) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "queensAreSafe. The board may not be null, must be square, " +
                    "and may only contain 'q's and '.'s");
        }

//      My technique here is to find the first queen, if there are any, then check to
//      the right, below, and bottom two diagonals of the queen for any other queens;
//      I don't need to check above because the program starts at the first queen found in the plot
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 'q') {
                    for (int k = i+1; k < board.length; k++) { //checks down rows
                        if (board[k][j] == 'q')
                            return false;
                    }
                    for (int k = j+1; k < board.length; k++) { //checks right columns
                        if (board[i][k] == 'q')
                            return false;
                    }
                    int k = i+1;
                    int l = j+1;
                    while (k < board.length && l < board.length) { //checks bottom right diagonal
                        if (board[k][l] == 'q')
                            return false;
                        k++;
                        l++;
                    }
                    k = i+1;
                    l = j-1;
                    while (k < board.length && l >= 0) { //checks bottom left diagonal
                        if (board[k][l] == 'q')
                            return false;
                        k++;
                        l--;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Given a 2D array of ints return the value of the
     * most valuable contiguous sub rectangle in the 2D array.
     * The sub rectangle must be at least 1 by 1.
     * <p>pre: <tt>mat != null, mat.length > 0, mat[0].length > 0,
     * mat</tt> is a rectangular matrix.
     * <p>post: return the value of the most valuable contiguous sub rectangle
     * in <tt>city</tt>.<br>
     * @param city The 2D array of ints representing the value of
     * each block in a portion of a city.
     * @return return the value of the most valuable contiguous sub rectangle
     * in <tt>city</tt>.
     */
    public static int getValueOfMostValuablePlot(int[][] city) {
        // check preconditions
        if (city == null || city.length == 0 || city[0].length == 0
                || !isRectangular(city) ) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "getValueOfMostValuablePlot. The parameter may not be null," +
                    " must value at least one row and at least" +
                    " one column, and must be rectangular.");
        }

//      My technique here is to find every upper left corner within the plot, then find every lower
//      right corner within the plot that can make a rectangular subplot with the upper left corner
//      given, and finally iterate across every subplot, storing and returning the highest value
        int maxValue = city[0][0];
        for (int i = 0; i < city.length; i++) {
            for (int j = 0; j < city[0].length; j++) {
                for (int k = i; k < city.length; k++) {
                    for (int l = j; l < city[0].length; l++) {
                        int sum = 0;
                        for (int t = i; t <= k; t++) {
                            for (int r = j; r <= l; r++) {
                                sum += city[t][r];
                            }
                        }
                        if (sum > maxValue) {maxValue = sum;}
                    }
                }
            }
        }
        return maxValue;
    }

//  The first experiment method returns the average number of pairs of people with shared birthdays
    public static double firstSharedBirthdaysExperiment(int numExperiments, int people, int days) {
        double sum = 0;
        for (int i = 0; i < numExperiments; i++) {
            sum += sharedBirthdays(people, days);
        }
        return sum / numExperiments;
    }

//  The second experiment method returns the number of experiments where at least one pair of
//  people shared a birthday
    public static int secondSharedBirthdaysExperiment(int numExperiments, int people, int days) {
        int count = 0;
        for (int i = 0; i < numExperiments; i++) {
            if (sharedBirthdays(people, days) > 0) {
                count++;
            }
        }
        return count;
    }



    /*
     * pre: arrayOfStrings != null
     * post: return true if at least one element in arrayOfStrings is
     * not null, otherwise return false.
     */
    private static boolean atLeastOneNonNull(String[] arrayOfStrings) {
        // check precondition
        if (arrayOfStrings == null) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "atLeastOneNonNull. parameter may not equal null.");
        }
        boolean foundNonNull = false;
        int i = 0;
        while ( !foundNonNull && i < arrayOfStrings.length ) {
            foundNonNull = arrayOfStrings[i] != null;
            i++;
        }
        return foundNonNull;
    }


    /*
     * pre: mat != null
     * post: return true if mat is a square matrix, false otherwise
     */
    private static boolean isSquare(char[][] mat) {
        if (mat == null) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "isSquare. Parameter may not be null.");
        }
        final int numRows = mat.length;
        int row = 0;
        boolean isSquare = true;
        while (isSquare && row < numRows) {
            isSquare = ( mat[row] != null) && (mat[row].length == numRows);
            row++;
        }
        return isSquare;
    }


    /*
     * pre: mat != null, valid != null
     * post: return true if all elements in mat are one of the
     * characters in valid
     */
    private static boolean onlyContains(char[][] mat, char[] valid) {
        // check preconditions
        if (mat == null || valid == null) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "onlyContains. Parameters may not be null.");
        }
        String validChars = new String(valid);
        int row = 0;
        boolean onlyContainsValidChars = true;
        while (onlyContainsValidChars && row < mat.length) {
            int col = 0;
            while (onlyContainsValidChars && col < mat[row].length) {
                int indexOfChar = validChars.indexOf(mat[row][col]);
                onlyContainsValidChars = indexOfChar != -1;
                col++;
            }
            row++;
        }
        return onlyContainsValidChars;
    }


    /*
     * pre: mat != null, mat.length > 0
     * post: return true if mat is rectangular
     */
    private static boolean isRectangular(int[][] mat) {
        // check preconditions
        if (mat == null || mat.length == 0) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "isRectangular. Parameter may not be null and must contain" +
                    " at least one row.");
        }
        boolean correct = mat[0] != null;
        int row = 0;
        while (correct && row < mat.length) {
            correct = (mat[row] != null)
                    && (mat[row].length == mat[0].length);
            row++;
        }
        return correct;
    }

    // make constructor private so no instances of CodeCamp can not be created
    private CodeCamp() {

    }
}