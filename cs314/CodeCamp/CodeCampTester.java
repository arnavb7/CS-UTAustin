import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// CodeCamp.java - CS314 Assignment 1 - Tester class

/*
 * Student information for assignment:
 * Name: Arnav Bhasin
 * email address: bhasin.arnav07@gmail.com
 * UTEID: ab78845
 * Section 5 digit ID: 52665
 * Grader name: Brad
 * Number of slip days used on this assignment: 0
 */

/*
The average number of pairs of people with shared birthdays, calculated over 1000000 experiments
with 182 people each in a 365 day year, is about 45.121918 pairs.

I would guess that it takes at least 50 people for a 50% chance of two of them having the same
birthday, because 365 days in a year is a lot of days and there's a low likelihood that of all the
days in the year, two people can share a single day as a birthday.

It requires 23 people to have a 50% chance of there being at least 1 shared birthday,
given a 365 day year. This answer is less than half my original prediction, which surprises me
because 23 people in a 365 day year seems like a really low proportion of kids to days.

Table:
Num people: 2, number of experiments with one or more shared birthday: 120, percentage: 0.24
Num people: 3, number of experiments with one or more shared birthday: 432, percentage: 0.864
Num people: 4, number of experiments with one or more shared birthday: 820, percentage: 1.6400000000000001
Num people: 5, number of experiments with one or more shared birthday: 1403, percentage: 2.806
Num people: 6, number of experiments with one or more shared birthday: 1978, percentage: 3.956
Num people: 7, number of experiments with one or more shared birthday: 2787, percentage: 5.574
Num people: 8, number of experiments with one or more shared birthday: 3650, percentage: 7.3
Num people: 9, number of experiments with one or more shared birthday: 4731, percentage: 9.462
Num people: 10, number of experiments with one or more shared birthday: 5869, percentage: 11.738
Num people: 11, number of experiments with one or more shared birthday: 6920, percentage: 13.84
Num people: 12, number of experiments with one or more shared birthday: 8468, percentage: 16.936
Num people: 13, number of experiments with one or more shared birthday: 9733, percentage: 19.466
Num people: 14, number of experiments with one or more shared birthday: 11154, percentage: 22.308
Num people: 15, number of experiments with one or more shared birthday: 12705, percentage: 25.41
Num people: 16, number of experiments with one or more shared birthday: 14137, percentage: 28.274
Num people: 17, number of experiments with one or more shared birthday: 15700, percentage: 31.4
Num people: 18, number of experiments with one or more shared birthday: 17284, percentage: 34.568
Num people: 19, number of experiments with one or more shared birthday: 18889, percentage: 37.778
Num people: 20, number of experiments with one or more shared birthday: 20705, percentage: 41.410000000000004
Num people: 21, number of experiments with one or more shared birthday: 22347, percentage: 44.694
Num people: 22, number of experiments with one or more shared birthday: 23686, percentage: 47.372
Num people: 23, number of experiments with one or more shared birthday: 25509, percentage: 51.017999999999994
Num people: 24, number of experiments with one or more shared birthday: 26858, percentage: 53.715999999999994
Num people: 25, number of experiments with one or more shared birthday: 28335, percentage: 56.67
Num people: 26, number of experiments with one or more shared birthday: 29830, percentage: 59.660000000000004
Num people: 27, number of experiments with one or more shared birthday: 31158, percentage: 62.316
Num people: 28, number of experiments with one or more shared birthday: 32702, percentage: 65.404
Num people: 29, number of experiments with one or more shared birthday: 34051, percentage: 68.10199999999999
Num people: 30, number of experiments with one or more shared birthday: 35404, percentage: 70.808
Num people: 31, number of experiments with one or more shared birthday: 36504, percentage: 73.008
Num people: 32, number of experiments with one or more shared birthday: 37596, percentage: 75.19200000000001
Num people: 33, number of experiments with one or more shared birthday: 38794, percentage: 77.58800000000001
Num people: 34, number of experiments with one or more shared birthday: 39696, percentage: 79.392
Num people: 35, number of experiments with one or more shared birthday: 40682, percentage: 81.364
Num people: 36, number of experiments with one or more shared birthday: 41608, percentage: 83.21600000000001
Num people: 37, number of experiments with one or more shared birthday: 42524, percentage: 85.048
Num people: 38, number of experiments with one or more shared birthday: 43191, percentage: 86.382
Num people: 39, number of experiments with one or more shared birthday: 43959, percentage: 87.91799999999999
Num people: 40, number of experiments with one or more shared birthday: 44540, percentage: 89.08
Num people: 41, number of experiments with one or more shared birthday: 45176, percentage: 90.352
Num people: 42, number of experiments with one or more shared birthday: 45731, percentage: 91.462
Num people: 43, number of experiments with one or more shared birthday: 46188, percentage: 92.376
Num people: 44, number of experiments with one or more shared birthday: 46647, percentage: 93.294
Num people: 45, number of experiments with one or more shared birthday: 47019, percentage: 94.038
Num people: 46, number of experiments with one or more shared birthday: 47508, percentage: 95.016
Num people: 47, number of experiments with one or more shared birthday: 47749, percentage: 95.498
Num people: 48, number of experiments with one or more shared birthday: 48054, percentage: 96.108
Num people: 49, number of experiments with one or more shared birthday: 48328, percentage: 96.65599999999999
Num people: 50, number of experiments with one or more shared birthday: 48542, percentage: 97.084
Num people: 51, number of experiments with one or more shared birthday: 48643, percentage: 97.286
Num people: 52, number of experiments with one or more shared birthday: 48919, percentage: 97.83800000000001
Num people: 53, number of experiments with one or more shared birthday: 49051, percentage: 98.102
Num people: 54, number of experiments with one or more shared birthday: 49197, percentage: 98.394
Num people: 55, number of experiments with one or more shared birthday: 49297, percentage: 98.59400000000001
Num people: 56, number of experiments with one or more shared birthday: 49390, percentage: 98.78
Num people: 57, number of experiments with one or more shared birthday: 49500, percentage: 99.0
Num people: 58, number of experiments with one or more shared birthday: 49596, percentage: 99.19200000000001
Num people: 59, number of experiments with one or more shared birthday: 49646, percentage: 99.292
Num people: 60, number of experiments with one or more shared birthday: 49735, percentage: 99.47
Num people: 61, number of experiments with one or more shared birthday: 49728, percentage: 99.456
Num people: 62, number of experiments with one or more shared birthday: 49821, percentage: 99.642
Num people: 63, number of experiments with one or more shared birthday: 49830, percentage: 99.66000000000001
Num people: 64, number of experiments with one or more shared birthday: 49858, percentage: 99.71600000000001
Num people: 65, number of experiments with one or more shared birthday: 49895, percentage: 99.79
Num people: 66, number of experiments with one or more shared birthday: 49904, percentage: 99.80799999999999
Num people: 67, number of experiments with one or more shared birthday: 49915, percentage: 99.83
Num people: 68, number of experiments with one or more shared birthday: 49931, percentage: 99.862
Num people: 69, number of experiments with one or more shared birthday: 49925, percentage: 99.85000000000001
Num people: 70, number of experiments with one or more shared birthday: 49960, percentage: 99.92
Num people: 71, number of experiments with one or more shared birthday: 49952, percentage: 99.90400000000001
Num people: 72, number of experiments with one or more shared birthday: 49977, percentage: 99.954
Num people: 73, number of experiments with one or more shared birthday: 49975, percentage: 99.95
Num people: 74, number of experiments with one or more shared birthday: 49990, percentage: 99.98
Num people: 75, number of experiments with one or more shared birthday: 49979, percentage: 99.958
Num people: 76, number of experiments with one or more shared birthday: 49986, percentage: 99.97200000000001
Num people: 77, number of experiments with one or more shared birthday: 49986, percentage: 99.97200000000001
Num people: 78, number of experiments with one or more shared birthday: 49997, percentage: 99.994
Num people: 79, number of experiments with one or more shared birthday: 49997, percentage: 99.994
Num people: 80, number of experiments with one or more shared birthday: 49997, percentage: 99.994
Num people: 81, number of experiments with one or more shared birthday: 49999, percentage: 99.998
Num people: 82, number of experiments with one or more shared birthday: 49998, percentage: 99.996
Num people: 83, number of experiments with one or more shared birthday: 49999, percentage: 99.998
Num people: 84, number of experiments with one or more shared birthday: 49998, percentage: 99.996
Num people: 85, number of experiments with one or more shared birthday: 49999, percentage: 99.998
Num people: 86, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 87, number of experiments with one or more shared birthday: 49998, percentage: 99.996
Num people: 88, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 89, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 90, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 91, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 92, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 93, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 94, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 95, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 96, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 97, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 98, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 99, number of experiments with one or more shared birthday: 50000, percentage: 100.0
Num people: 100, number of experiments with one or more shared birthday: 50000, percentage: 100.0
 */

public class CodeCampTester {

    public static void main(String[] args) {
        final String newline = System.getProperty("line.separator");

        // Personal test 1, hamming distance
        int[] h1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] h2 = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        int expected = 8;
        int actual = CodeCamp.hammingDistance(h1, h2);
        System.out.println("Personal Test 1 hamming distance: expected value: " + expected
                + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed personal test 1, hamming distance");
        } else {
            System.out.println(" ***** FAILED ***** test 1, hamming distance");
        }

        // Personal test 2, hamming distance
        h1 = new int[1000];
        h2 = new int[1000];
        expected = 0;
        actual = CodeCamp.hammingDistance(h1, h2);
        System.out.println(newline + "Personal Test 2 hamming distance: expected value: " + expected
                + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed personal test 2, hamming distance");
        } else {
            System.out.println(" ***** FAILED ***** personal test 2, hamming distance");
        }

        // Personal test 3, isPermutation
        int[] a = { 1, 4, 5, 0, 3, 7, 4, 8 };
        int[] b = { 1, 0, 7, 4, 8, 4, 5, 3 };
        boolean expectedBool = true;
        boolean actualBool = CodeCamp.isPermutation(a, b);
        System.out.println(newline + "Personal Test 3 isPermutation: expected value: " + expectedBool
                + ", actual value: " + actualBool);
        if (expectedBool == actualBool) {
            System.out.println(" passed personal test 3, isPermutation");
        } else {
            System.out.println(" ***** FAILED ***** personal test 3, isPermutation");
        }

        // Personal test 4, is Permutation
        a = new int[] { 5, 6, 7, 8, 9, 10 };
        b = new int[] { 5, 6, 7, 8 };
        expectedBool = false;
        actualBool = CodeCamp.isPermutation(a, b);
        System.out.println(newline + "Personal Test 4 isPermutation: expected value: " + expectedBool
                + ", actual value: " + actualBool);
        if (expectedBool == actualBool) {
            System.out.println(" passed personal test 4, isPermutation");
        } else {
            System.out.println(" ***** FAILED ***** personal test 4 isPermutation");
        }

        // Personal test 5, mostVowels
        String[] arrayOfStrings = { "abracadabra", "avadacadavra" };
        int expectedResult = 1;
        int actualResult = CodeCamp.mostVowels(arrayOfStrings);
        System.out.println(newline + "Personal Test 5 mostVowels: expected result: " + expectedResult
                + ", actual result: " + actualResult);
        if (actualResult == expectedResult) {
            System.out.println(" passed personal test 5, mostVowels");
        } else {
            System.out.println("***** FAILED ***** personal test 5, mostVowels");
        }

        // Personal test 6, mostVowels
        arrayOfStrings = new String[] { "Whoops", "absolutely", "", null, "Whoops", "Oops", "pneumonoultramicroscopicsilicovolcanoconiosis",
                "a/';a][u/][;e(&!i^p%^#?%{a!}e!#%{#?%{?e@{o#?@{>%#a{%ue?" };
        expectedResult = 6;
        actualResult = CodeCamp.mostVowels(arrayOfStrings);
        System.out.println(newline + "Personal Test 6 mostVowels: expected result: " + expectedResult
                + ", actual result: " + actualResult);
        if (actualResult == expectedResult) {
            System.out.println(" passed personal test 6, mostVowels");
        } else {
            System.out.println("***** FAILED ***** personal test 6, mostVowels");
        }

        // Personal test 7, sharedBirthdays, simple test
        int pairs = CodeCamp.sharedBirthdays(50, 365);
        System.out.println(newline + "Personal Test 7 shared birthdays: expected: "
                + "a value of 0 or more, actual: " + pairs);
        if (pairs >= 0) {
            System.out.println(" passed personal test 7, shared birthdays");
        } else {
            System.out.println("***** FAILED ***** personal test 7, shared birthdays");
        }

        // Personal test 8, sharedBirthdays, simple test
        pairs = CodeCamp.sharedBirthdays(50, 1);
        System.out.println(
                newline + "Personal Test 8 shared birthdays: expected: 1225" + ", actual: " + pairs);
        if (pairs == 1225) {
            System.out.println(" passed personal test 8, shared birthdays");
        } else {
            System.out.println("***** FAILED ***** personal test 8, shared birthdays. "
                    + "Expected pairs to be 1225. Value returned: " + pairs);
        }

        // Personal test 9, queensAreASafe
        char[][] board = new char[][] {
                { '.', '.', '.', 'q', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.' },
                { 'q', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', 'q' },
                { '.', 'q', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', 'q', '.', '.', '.' } };
        expectedBool = true;
        actualBool = CodeCamp.queensAreSafe(board);
        System.out.println(newline + "Personal Test 9 queensAreSafe: expected value: " + expectedBool
                + ", actual value: " + actualBool);
        if (expectedBool == actualBool) {
            System.out.println(" passed personal test 9, queensAreSafe");
        } else {
            System.out.println(" ***** FAILED ***** personal test 9, queensAreSafe");
        }

        // Personal test 10, queensAreASafe
        board = new char[][] {
                { '.', '.', '.', 'q', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.' },
                { 'q', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', 'q', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '.', '.', 'q' },
                { '.', 'q', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', 'q', '.', '.', '.' } };
        expectedBool = false;
        actualBool = CodeCamp.queensAreSafe(board);
        System.out.println(newline + "Personal Test 10 queensAreSafe: expected value: " + expectedBool
                + ", actual value: " + actualBool);
        if (expectedBool == actualBool) {
            System.out.println(" passed personal test 10, queensAreSafe");
        } else {
            System.out.println(" ***** FAILED ***** personal test 10, queensAreSafe");
        }

        // Personal test 11, getValueOfMostValuablePlot
        int[][] city = { { -6, -3, -1 },
                         { 9, 2, 5 },
                         { -7, 1, -2 } };

        expected = 16;
        actual = CodeCamp.getValueOfMostValuablePlot(city);
        System.out.println(newline + "Personal Test 11 getValueOfMostValuablePlot: expected value: "
                + expected + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed personal test 11, getValueOfMostValuablePlot");
        } else {
            System.out.println(" ***** FAILED ***** personal test 11, getValueOfMostValuablePlot");
        }

        // Personal test 12, getValueOfMostValuablePlot
        city = new int[][] { {-1, -1, -1, -1},
                             {-1, 20, -1, -1},
                             {-1, -1, -1, -1},
                             {-1, -1, -1, -1 }};

        expected = 20;
        actual = CodeCamp.getValueOfMostValuablePlot(city);
        System.out.println("\nPersonal Test 12 getValueOfMostValuablePlot: expected value: " + expected
                + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed personal test 12, getValueOfMostValuablePlot");
        } else {
            System.out.println(" ***** FAILED ***** personal test 12, getValueOfMostValuablePlot");
        }

    } // end of main method

    // pre: list != null
    private static int[] intListToArray(List<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("list parameter may not be null.");
        }
        int[] result = new int[list.size()];
        int arrayIndex = 0;
        for (int x : list) {
            result[arrayIndex++] = x;
        }
        return result;
    }
}