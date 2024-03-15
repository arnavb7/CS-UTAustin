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

import java.util.ArrayList;
import java.util.Collections;

/**
 * Tester class for the methods in Recursive.java
 * @author scottm
 *
 */
public class RecursiveTester {

    public static void main(String[] args)
    {
        studentTests();
    }

    private static void studentTests() {

        // Binary Conversion Test 1
        String actualBinary = Recursive.getBinary(16);
        String expectedBinary = "10000";
        if (actualBinary.equals(expectedBinary)) {
            System.out.println("Own tests: test 1 passed. get binary.");
        } else {
            System.out.println("Own tests: test 1 failed. get binary. expected: "
                    + expectedBinary + ", actual: " + actualBinary);
        }

        // Binary Conversion Test 2
        actualBinary = Recursive.getBinary(-60);
        expectedBinary = "-111100";
        if (actualBinary.equals(expectedBinary)) {
            System.out.println("Own tests: test 2 passed. get binary.");
        } else {
            System.out.println("Own tests: test 2 failed. get binary. expected: "
                    + expectedBinary + ", actual: " + actualBinary);
        }

        System.out.println();

        // Reversing a String Test 1
        String actualRev = Recursive.revString("ekiM");
        String expectedRev = "Mike";
        if (actualRev.equals(expectedRev)) {
            System.out.println("Own tests: test 1 passed. reverse string.");
        } else {
            System.out.println("Own tests: test 1 failed. reverse string. expected: " +
                    expectedRev + ", actual: " + actualRev);
        }

        // Reversing a String Test 2
        actualRev = Recursive.revString("CS314");
        expectedRev = "413SC";
        if (actualRev.equals(expectedRev)) {
            System.out.println("Own tests: test 2 passed. reverse string.");
        } else {
            System.out.println("Own tests: test 2 failed. reverse string. expected: "
                    + expectedRev + ", actual: " + actualRev);
        }

        System.out.println();

        // nextIsDouble Test 1
        int[] numsForDouble = {100, 200, 400, 800, 1600, 3200};
        int actualDouble = Recursive.nextIsDouble(numsForDouble);
        int expectedDouble = 5;
        if (actualDouble == expectedDouble) {
            System.out.println("Own tests: test 1 passed. next is double.");
        } else {
            System.out.println("Test 1 failed. next is double. expected: "
                    + expectedDouble + ", actual: " + actualDouble);
        }

        // nextIsDouble Test 2
        numsForDouble = new int[] {2, 5, 10, -20, 30, 40};
        actualDouble = Recursive.nextIsDouble(numsForDouble);
        expectedDouble = 1;
        if (actualDouble == expectedDouble) {
            System.out.println("Own tests: test 2 passed. next is double.");
        } else {
            System.out.println("Test 2 failed. next is double. expected: "
                    + expectedDouble + ", actual: " + actualDouble);
        }

        System.out.println();

        // Phone Mnemonics Test 1
        ArrayList<String> mnemonics = Recursive.listMnemonics("33");
        ArrayList<String> expected = new ArrayList<>();
        Collections.sort(mnemonics);
        expected.add("DD");                        //A B C
        expected.add("DE");                        //D E F
        expected.add("DF");
        expected.add("ED");
        expected.add("EE");
        expected.add("EF");
        expected.add("FD");
        expected.add("FE");
        expected.add("FF");
        Collections.sort(expected);
        if (mnemonics.equals(expected)) {
            System.out.println("Own tests: test 1 passed. Phone mnemonics.");
        } else {
            System.out.println("Own tests: Test 1 failed. Phone mnemonics.");
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + mnemonics);
        }

        // Phone Mnemonics Test 2
        mnemonics = Recursive.listMnemonics("55");
        expected.clear();
        Collections.sort(mnemonics);
        expected.add("JJ");                        //A B C
        expected.add("JK");                        //D E F
        expected.add("JL");                        //J K L
        expected.add("KL");
        expected.add("KK");
        expected.add("LK");
        expected.add("LJ");
        expected.add("LL");
        expected.add("KJ");
        Collections.sort(expected);
        if (mnemonics.equals(expected)) {
            System.out.println("Own tests: test 2 passed. Phone mnemonics.");
        } else {
            System.out.println("Own tests: Test 2 failed. Phone mnemonics.");
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + mnemonics);
        }

        System.out.println();

        // Sierpinski Carpet Test 1
        Recursive.drawCarpet(729, 9);
        System.out.println("Own tests: test 1 passed. Sierpinski carpet.");

        // Sierpinski Carpet Test 2
        Recursive.drawCarpet(243, 1);
        System.out.println("Own tests: test 2 passed. Sierpinski carpet.");

        System.out.println();

        // Water flowing off a map Test 1
        int[][] world = {{6,6,6,6,6},
                {6,6,6,6,6},
                {6,6,6,6,6},
                {6,6,6,6,6},
                {6,6,5,6,6},
                {6,3,4,8,6},
                {6,6,3,6,6},
                {6,6,1,6,6}};
        System.out.println("Can Flow Off Map Test Number 1: ");
        boolean actual = Recursive.canFlowOffMap(world, 4, 2);
        System.out.println("Start location = " + 4 + ", " + 2);
        System.out.println("Expected result = " + expected + " actual result = " + actual);
        if (actual) {
            System.out.println("Own tests: passed test 1 can flow off map.");
        } else {
            System.out.println("Own tests: FAILED TEST 1 can flow off map.");
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + actual);
        }

        // Water flowing off a map Test 2
        world = new int [][]{{3,3,3,3,3,3,3,3,3},
                {3,3,3,3,3,3,3,3,3},
                {3,3,3,3,0,3,3,3,3},
                {3,3,3,3,1,3,3,3,3},
                {3,3,3,3,2,3,3,3,3},
                {3,3,3,3,1,3,3,3,3},
                {3,3,3,3,3,3,3,3,3},
                {3,3,3,3,0,3,3,3,3}};
        System.out.println("Can Flow Off Map Test Number 2: ");
        actual = Recursive.canFlowOffMap(world, 4, 2);
        System.out.println("Start location = " + 4 + ", " + 2);
        System.out.println("Expected result = " + expected + " actual result = " + actual);
        if (!actual) {
            System.out.println("Own tests: passed test 2 can flow off map.");
        } else {
            System.out.println("Own tests: FAILED TEST 2 can flow off map.");
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + actual);
        }

        System.out.println();

        // Creating fair teams Test 1
        int[] abilities = {1, 1, 2, 2, 3, 3, 4, 4};
        int expResult = Recursive.minDifference(2, abilities);
        int actualResult = 0;
        if (expResult == actualResult) {
            System.out.println("Own tests: passed test 1, fair teams.");
        }
        else {
            System.out.println("FAILED test 1, fair teams");
        }

        // Creating fair teams Test 2
        abilities = new int [] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        expResult = Recursive.minDifference(4, abilities);
        actualResult = 0;
        if (expResult == actualResult) {
            System.out.println("Own tests: passed test 2, fair teams.");
        }
        else {
            System.out.println("FAILED test 2, fair teams");
        }

        System.out.println();

        // MazeSolver Test 1
        char [][] test = new char[][] {
                {'$', 'Y', '*', '*', '*'},
                {'S', 'E', '*', '*', '$'},
                {'S', 'Y', '*', '*', '$'}};
        int res = 1;
        int actualRes = Recursive.canEscapeMaze(test);
        if (res == actualRes) {
            System.out.println("Own tests: passed test 1, canEscapeMaze.");
        }
        else {
            System.out.println("Own tests: failed test 1, canEscapeMaze.");
        }

        // MazeSolver Test 2
        test = new char[][] {
                {'$', 'Y', '*', '*', '*'},
                {'S', 'G', 'E', '*', '*'},
                {'$', 'Y', '*', '*', '*'}};
        res = 2;
        actualRes = Recursive.canEscapeMaze(test);
        if (res == actualRes) {
            System.out.println("Own tests: passed test 2, canEscapeMaze.");
        }
        else {
            System.out.println("Own tests: failed test 2, canEscapeMaze.");
        }
    }
}