/*  Student information for assignment:
 *
 *  On OUR honor, Arnav Bhasin and Aaron Zhao, this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  Grader name: Brad
 *  Section number: 52665
 *
 *  Student 2
 *  UTEID: yz28676
 *  email address: aaron.zhao.a.z@gmail.com
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Various recursive methods to be implemented.
 * Given shell file for CS314 assignment.
 */
public class Recursive {

    /**
     * Problem 1: convert a base 10 int to binary recursively.
     *   <br>pre: n != Integer.MIN_VALUE<br>
     *   <br>post: Returns a String that represents N in binary.
     *   All chars in returned String are '1's or '0's.
     *   Most significant digit is at position 0
     *   @param n the base 10 int to convert to base 2
     *   @return a String that is a binary representation of the parameter n
     */
    // takes in base 10 integer and returns given integer in base 2
    // preconditions: integer parameter is not equal to Integer.MIN_VALUE
    public static String getBinary(int n) {
        if (n == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "getBinary. n cannot equal "
                    + "Integer.MIN_VALUE. n: " + n);
        }
        if (n < 0) {
            return "-" + getBinary(Math.abs(n));
        }
        if (n == 0 || n == 1) {
            return "" + n;
        }
        int remainder = n % 2;
        return getBinary(n / 2) + "" + remainder;
    }

    /**
     * Problem 2: reverse a String recursively.<br>
     *   pre: stringToRev != null<br>
     *   post: returns a String that is the reverse of stringToRev
     *   @param stringToRev the String to reverse.
     *   @return a String with the characters in stringToRev
     *   in reverse order.
     */
    // takes in String and returns given String with order of letters reversed
    // preconditions: String parameter is not null
    public static String revString(String stringToRev) {
        if (stringToRev == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "revString. parameter may not be null.");
        }
        if (stringToRev.length() == 0 || stringToRev.length() == 1) {
            return stringToRev;
        }
        return revString(stringToRev.substring(1))
                + stringToRev.substring(0, 1);
    }

    /**
     * Problem 3: Returns the number of elements in data
     * that are followed directly by value that is
     * double that element.
     * pre: data != null
     * post: return the number of elements in data
     * that are followed immediately by double the value
     * @param data The array to search.
     * @return The number of elements in data that
     * are followed immediately by a value that is double the element.
     */
    // takes in int array of values and returns how many values are directly followed by
    // double their value
    // preconditions: int array parameter is not null
    public static int nextIsDouble(int[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "revString. parameter may not be null.");
        }
        return doubleHelper(data, 0);
    }

    // self-coded helper method to aid process of recursion by iterating through array and
    // recursively adding double count
    private static int doubleHelper(int[] data, int index) {
        if (data.length == 0 || index == data.length - 1) {
            return 0;
        }
        int count = 0;
        if (data[index] * 2 == data[index + 1]) {
            count++;
        }
        return count + doubleHelper(data, index + 1);
    }

    /**  Problem 4: Find all combinations of mnemonics
     * for the given number.<br>
     *   pre: number != null, number.length() > 0,
     *   all characters in number are digits<br>
     *   post: see tips section of assignment handout
     *   @param number The number to find mnemonics for
     *   @return An ArrayList<String> with all possible mnemonics
     *   for the given number
     */
    // takes in number and returns all possible mnemonics based on number pattern
    // preconditions: String parameter is not null and its length is greater than 0
    public static ArrayList<String> listMnemonics(String number) {
        if (number == null ||  number.length() == 0 || !allDigits(number)) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "listMnemonics");
        }
        ArrayList<String> results = new ArrayList<>(); // to store the mnemonics
        recursiveMnemonics(results, "", number);
        return results;
    }

    /*
     * Helper method for listMnemonics
     * mnemonics stores completed mnemonics
     * mneominicSoFar is a partial (possibly complete) mnemonic
     * digitsLeft are the digits that have not been used
     * from the original number.
     */
    // self-coded helper method to aid process of recursion, builds all possible
    // mnemonics recursively
    private static void recursiveMnemonics(ArrayList<String> mnemonics,
                                            String mnemonicSoFar, String digitsLeft) {
        if (digitsLeft.length() == 0) {
            mnemonics.add(mnemonicSoFar);
            return;
        }
        char thisDigit = digitsLeft.charAt(0);
        String lettersLeft = digitLetters(thisDigit);
        for (int i = 0; i < lettersLeft.length(); i++) {
            String newMnemonicSoFar = mnemonicSoFar + lettersLeft.charAt(i);
            recursiveMnemonics(mnemonics, newMnemonicSoFar, digitsLeft.substring(1));
        }
    }

    /* Static code blocks are run once when this class is loaded.
     * Here we create an unmodifiable list to use with the phone
     * mnemonics method.
     */
    // given static block of code to represent letters each number corresponds to
    private static final List<String> LETTERS_FOR_NUMBER;
    static {
        String[] letters = {"0", "1", "ABC",
                "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
        ArrayList<String> lettersAsList = new ArrayList<>();
        for (String s : letters) {
            lettersAsList.add(s);
        }
        LETTERS_FOR_NUMBER = Collections.unmodifiableList(lettersAsList);
        // used by method digitLetters
    }

    /* helper method for recursiveMnemonics
     * pre: ch is a digit '0' through '9'
     * post: return the characters associated with
     * this digit on a phone keypad
     */
    // given helper method to aid process of recursion by returning corresponding letters for
    // given number
    private static String digitLetters(char ch) {
        if (ch < '0' || ch > '9') {
            throw new IllegalArgumentException("parameter "
                    + "ch must be a digit, 0 to 9. Given value = " + ch);
        }
        int index = ch - '0';
        return LETTERS_FOR_NUMBER.get(index);
    }

    /* helper method for listMnemonics
     * pre: s != null
     * post: return true if every character in s is
     * a digit ('0' through '9')
     */
    // given helper method to aid process of recursion by checking preconditions
    private static boolean allDigits(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "allDigits. String s cannot be null.");
        }
        boolean allDigits = true;
        int i = 0;
        while (i < s.length() && allDigits) {
            allDigits = s.charAt(i) >= '0' && s.charAt(i) <= '9';
            i++;
        }
        return allDigits;
    }

    /**
     * Problem 5: Draw a Sierpinski Carpet.
     * @param size the size in pixels of the window
     * @param limit the smallest size of a square in the carpet.
     */
    // takes in size and limit and creates drawing panels with Sierpinski carpet based on
    // given size and limit
    public static void drawCarpet(int size, int limit) {
        DrawingPanel p = new DrawingPanel(size, size);
        Graphics g = p.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,size,size);
        g.setColor(Color.WHITE);
        drawSquares(g, size, limit, 0, 0);
    }

    /* Helper method for drawCarpet
     * Draw the individual squares of the carpet.
     * @param g The Graphics object to use to fill rectangles
     * @param size the size of the current square
     * @param limit the smallest allowable size of squares
     * @param x the x coordinate of the upper left corner of the current square
     * @param y the y coordinate of the upper left corner of the current square
     */
    // self-coded helper method to aid process of recursion, demarcates all Sierpinski squares
    // recursively, dividing and demarcating from size until limit parameter has been reached
    private static void drawSquares(Graphics g, int size, int limit,
                                    double x, double y) {
        final int SIZE_DIVISOR = 3; // magic number
        if (size <= limit) {
            return;
        }
        double newSize = (double) size / SIZE_DIVISOR;
        for (int i = 0; i < size; i += newSize) {
            for (int j = 0; j < size; j += newSize) {
                if (x != newSize && y != newSize) {
                    double newX = x + i;
                    double newY = y + j;
                    drawSquares(g, (int) newSize, limit, newX, newY);
                }
            }
        }
        g.setColor(Color.WHITE);
        g.fillRect((int) (x + newSize), (int) (y + newSize), (int) newSize, (int) newSize);
    }

    /**
     * Problem 6: Determine if water at a given point
     * on a map can flow off the map.
     * <br>pre: map != null, map.length > 0,
     * map is a rectangular matrix, 0 <= row < map.length,
     * 0 <= col < map[0].length
     * <br>post: return true if a drop of water starting at the location
     * specified by row, column can reach the edge of the map,
     * false otherwise.
     * @param map The elevations of a section of a map.
     * @param row The starting row of a drop of water.
     * @param col The starting column of a drop of water.
     * @return return true if a drop of water starting at the location
     * specified by row, column can reach the edge of the map, false otherwise.
     */
    // takes in 2D int array for altitude map, int row and int column for starting point
    // preconditions: 2D int array parameter is not null, has a length > 0, is a rectangular 2D
    // array, and starting point is within bounds of map
    public static boolean canFlowOffMap(int[][] map, int row, int col) {
        if (map == null || map.length == 0 || !isRectangular(map)
                || !inbounds(row, col, map)) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "canFlowOffMap");
        }
        if (row == 0 || row == map.length - 1 || col == 0 || col == map[0].length - 1) {
            return true;
        }
        int[] rowDir = {1, -1, 0, 0};
        int[] colDir = {0, 0, 1, -1};
        for (int i = 0; i < rowDir.length; i++) {
            if (map[row + rowDir[i]][col + colDir[i]] < map[row][col]) {
                if (canFlowOffMap(map, row + rowDir[i], col + colDir[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    /* helper method for canFlowOfMap - CS314 students you should not have to
     * call this method,
     * pre: mat != null,
     */
    // given helper method to aid process of checking preconditions
    private static boolean inbounds(int r, int c, int[][] mat) {
        if (mat == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "inbounds. The 2d array mat may not be null.");
        }
        return r >= 0 && r < mat.length && mat[r] != null
                && c >= 0 && c < mat[r].length;
    }

    /*
     * helper method for canFlowOfMap - CS314 students you should not have to
     * call this method,
     * pre: mat != null, mat.length > 0
     * post: return true if mat is rectangular
     */
    // given helper method to aid process of checking preconditions
    private static boolean isRectangular(int[][] mat) {
        if (mat == null || mat.length == 0) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "inbounds. The 2d array mat may not be null "
                    + "and must have at least 1 row.");
        }
        boolean correct = true;
        final int numCols = mat[0].length;
        int row = 0;
        while (correct && row < mat.length) {
            correct = (mat[row] != null) && (mat[row].length == numCols);
            row++;
        }
        return correct;
    }

    /**
     * Problem 7: Find the minimum difference possible between teams
     * based on ability scores. The number of teams may be greater than 2.
     * The goal is to minimize the difference between the team with the
     * maximum total ability and the team with the minimum total ability.
     * <br> pre: numTeams >= 2, abilities != null, abilities.length >= numTeams
     * <br> post: return the minimum possible difference between the team
     * with the maximum total ability and the team with the minimum total
     * ability.
     * @param numTeams the number of teams to form.
     * Every team must have at least one member
     * @param abilities the ability scores of the people to distribute
     * @return return the minimum possible difference between the team
     * with the maximum total ability and the team with the minimum total
     * ability. The return value will be greater than or equal to 0.
     */
    // takes in int for number of teams and int array for all people's ability scores
    // preconditions: int parameter is greater than or equal to 2, int array parameter is not
    // null and its length is greater than int parameter
    public static int minDifference(int numTeams, int[] abilities) {
        int index = 0;
        int[] sumOfTeamsAbilities = new int [numTeams];
        int[] numPplOnTeams = new int [numTeams];
        return minDiffHelper(numTeams, abilities, index, sumOfTeamsAbilities, numPplOnTeams);
    }

    // self-coded helper method to aid process of recursion, optimizing team setup
    private static int minDiffHelper(int numOfTeams, int[] pplWithAbilities, int ind,
                                    int[] sumTeam, int[] pplOnTeams) {
        int minDiff = Integer.MAX_VALUE;
        int currentDiff = -1;
        if (ind == pplWithAbilities.length) {
            minDiff = minDiffBaseChecker(sumTeam, pplOnTeams);
            return minDiff;
        }
        final int loopCase = Math.min(sumTeam.length, ind + 1);
        for (int i = 0; i < loopCase; i++) {
            int currentPerson = pplWithAbilities[ind];
            sumTeam[i] += currentPerson;
            pplOnTeams[i]++;
            currentDiff = minDiffHelper(numOfTeams, pplWithAbilities, ind + 1,
                    sumTeam, pplOnTeams);
            if (currentDiff < minDiff) {
                minDiff = currentDiff;
            }
            sumTeam[i] -= currentPerson;
            pplOnTeams[i]--;
        }
        return minDiff;
    }

    // self-coded helper method to aid process of checking base cases for recursion
    private static int minDiffBaseChecker(int[] sumTeam, int[] pplOnTeams) {
        for (int i = 0; i < pplOnTeams.length; i++) {
            if (pplOnTeams[i] == 0) {
                return Integer.MAX_VALUE;
            }
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < sumTeam.length; i++) {
            if (sumTeam[i] < min) {
                min = sumTeam[i];
            }
            if (sumTeam[i] > max) {
                max = sumTeam[i];
            }
        }
        return max - min;
    }

    /**
     * Problem 8: Maze solver.
     * <br>pre: board != null
     * <br>pre: board is a rectangular matrix
     * <br>pre: board only contains characters 'S', 'E', '$', 'G', 'Y', and '*'
     * <br>pre: there is a single 'S' character present
     * <br>post: rawMaze is not altered as a result of this method.
     * Return 2 if it is possible to escape the maze after
     * collecting all the coins.
     * Return 1 if it is possible to escape the maze
     * but without collecting all the coins.
     * Return 0 if it is not possible
     * to escape the maze. More details in the assignment handout.
     * @param rawMaze represents the maze we want to escape.
     * rawMaze is not altered as a result of this method.
     * @return per the post condition
     */
    // takes in int array for the raw maze
    // preconditions: int array parameter is not null, is rectangular, and only contains S, E,
    // $, G, Y, and *, with S appearing only once
    public static int canEscapeMaze(char[][] rawMaze) {
        int startRow = -1;
        int startCol = -1;
        int totalCoins = 0;
        //find the starting point
        for (int row = 0; row < rawMaze.length; row++) {
            for (int col = 0; col < rawMaze[0].length; col++) {
                if (rawMaze[row][col] == '$') {
                    totalCoins++;
                }
                if (rawMaze[row][col] == 'S') {
                    startRow = row;
                    startCol = col;
                }
            }
        }
        return mazeHelper(rawMaze, startRow, startCol, totalCoins);
    }

    // self-coded helper method to aid process of checking base cases for recursion
    private static boolean outOfBounds(char[][] theMaze, int startRow, int startCol) {
        if (startRow == theMaze.length || startRow < 0 || startCol == theMaze[0].length ||
                startCol < 0) {
            return true;
        }
        return false;
    }

    // self-coded helper method to aid process of recursion by checking base cases and
    // performing recursive algorithm
    private static int mazeHelper(char[][] maze, int rowStart, int colStart, int numCoins) {
        if (outOfBounds(maze, rowStart, colStart)) {
            return 0;
        }
        if (maze[rowStart][colStart] == '*') {
            return 0;
        }
        if (maze[rowStart][colStart] == 'E') {
            if (numCoins == 0) {
                return 2;
            } else {
                return 1;
            }
        }
        return mazeAlgorithm(maze, rowStart, colStart, numCoins);
    }

    // self-coded helper method to aid process of recursion, checking all directions from
    // current position in maze
    private static int mazeAlgorithm(char[][] maze, int rowStart, int colStart, int numCoins) {
        int[] rowDirections = {1, -1, 0, 0};
        int[] colDirections = {0, 0, 1, -1};
        int bestResult = -1;
        char originalState = maze[rowStart][colStart];
        if (originalState == '$') {
            numCoins--;
        }
        maze[rowStart][colStart] = symbolHelper(originalState);
        for (int i = 0; i < rowDirections.length; i++) {
            int temp = mazeHelper(maze, rowStart + rowDirections[i],
                    colStart + colDirections[i], numCoins);
            if (temp == 2) {
                return 2;
            }
            if (temp > bestResult) {
                bestResult = temp;
            }
        }
        maze[rowStart][colStart] = originalState;
        return bestResult;
    }

    // self-coded helper method to aid process of recursive backtracking by returning what maze
    // value becomes after it's passed
    private static char symbolHelper(char state) {
        if (state == 'Y') {
            return '*';
        }
        if (state == 'G' || state == '$') {
            return 'Y';
        }
        if (state == 'S') {
            return 'G';
        }
        return ' ';
    }
}