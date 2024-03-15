/*  Student information for assignment:
 *
 *  On MY honor, Arnav Bhasin,
 *  this programming assignment is MY own work
 *  and I have not provided this code to any other student.
 *
 *  Number of slip days used: 2
 *
 *  Student 1: Arnav Bhasin
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  TA name: Brad
 */

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/*
It is unwise to implement all three methods union, intersection, and difference because the
intersection method can be implemented using the union and difference methods, so we can
achieve the intersection by taking the union of sets A and B, then subtracting the (A-B) and
(B-A) elements to get only the elements that exist in both. We can leave the implementation of
union and intersection to the subclasses of AbstractSet.

Experiment Results:

Sorted Set
File         Size (kb)   Total Words   Increase from Previous Row   Unique Words   Inc. Prev. Row   Actual Time   Inc. Prev. Row
gu1.txt      39          6242          -                            2022           -                0.032 sec.    -
gu2.txt      29          4562          0.7x                         1579           0.8x             0.024 sec.    0.8x
gu3.txt      297         45914         3.8x                         14049          8.9x             0.27 sec.     11.3x
gu4.txt      1153        185083        4x                           22366          1.6x             0.72 sec.     2.7x

Unsorted Set
File         Size (kb)   Total Words   Increase from Previous Row   Unique Words   Inc. Prev. Row   Actual Time   Inc. Prev. Row
gu1.txt      39          6242          -                            2022           -                0.048 sec.    -
gu2.txt      29          4562          0.7x                         1579           0.8x             0.037 sec.    0.8x
gu3.txt      297         45914         10.1x                        14049          8.9x             0.88 sec.     23.8x
gu4.txt      1153        185083        4x                           22366          1.6x             2.42 sec.     2.75x

HashSet
File         Size (kb)   Total Words   Increase from Previous Row   Unique Words   Inc. Prev. Row   Actual Time   Inc. Prev. Row
gu1.txt      39          6242          -                            2022           -                0.011 sec.    -
gu2.txt      29          4562          0.7x                         1579           0.8x             0.014 sec.    1.3x
gu3.txt      297         45914         10.1x                        14049          8.9x             0.032 sec.    2.3x
gu4.txt      1153        185083        4x                           22366          1.6x             0.11 sec.     3.4x

TreeSet
File         Size (kb)   Total Words   Increase from Previous Row   Unique Words   Inc. Prev. Row   Actual Time   Inc. Prev. Row
gu1.txt      39          6242          -                            2022           -                0.016 sec.    -
gu2.txt      29          4562          0.7x                         1579           0.8x             0.015 sec.    0.9x
gu3.txt      297         45914         10.1x                        14049          8.9x             0.048 sec     3.2x
gu4.txt      1153        185083        4x                           22366          1.6x             0.13 sec.     2.7x

Based on the numbers, I'd think that the Big O is O(N) for both word process methods.
Based on the numbers, I'd think that the Big O is O(N) for the add method for Unsorted and Sorted Set, O(1) for HashSet, and O(LogN) for TreeSet.
The elements of the HashSet are printed "randomly" based on the internal hash code of the elements of the set, while the elements of the TreeSet
are printed in sorted order.
 */


public class SetTester {

    public static void main(String[] args) {
        // Personal Tests:
        // Tests for UnsortedSet and AbstractSet:
        ISet<String> s1 = new UnsortedSet<>();
        s1.add("Z");
        s1.add("Y");
        s1.add("V");
        s1.add("U");
        s1.add("X");
        s1.add("W");
        ISet<String> s2 = new UnsortedSet<>();
        s2.add("Z");
        s2.add("B");
        s2.add("C");
        s2.add("A");

        // Test 1 for Unsorted add
        boolean actual = s1.toString().equals("(Z, Y, V, U, X, W)");
        showTestResults(actual, true, 1, s1, null, "add method UnsortedSet");

        // Test 2 for Unsorted/Abstract addAll
        s1.addAll(s2);
        actual = s1.toString().equals("(Z, Y, V, U, X, W, B, C, A)");
        showTestResults(actual, true, 2, s1, s2, "addAll method UnsortedSet/AbstractSet");

        // Test 3 for Unsorted/Abstract clear
        s2.clear();
        actual = s2.toString().equals("()");
        showTestResults(actual, true, 3, s2, null, "clear method UnsortedSet/AbstractSet");

        // Test 4 for Unsorted/Abstract contains
        actual = s1.contains("B");
        showTestResults(actual, true, 4, s1, s2, "contains method UnsortedSet/AbstractSet");

        // Test 5 for Unsorted/Abstract containsAll
        s2.add("Z");
        s2.add("W");
        s2.add("C");
        actual = s1.containsAll(s2);
        showTestResults(actual, true, 5, s1, s2, "containsAll method UnsortedSet/AbstractSet");

        // Test 6 for Unsorted difference
        ISet<String> s3 = s1.difference(s2);
        actual = s3.toString().equals("(Y, V, U, X, B, A)");
        showTestResults(actual, true, 6, s1, s2, "difference method UnsortedSet");

        // Test 7 for Unsorted/Abstract equals
        s3.add("Z");
        s3.add("W");
        s3.add("C");
        actual = s1.equals(s3);
        showTestResults(actual, true, 7, s1, s3, "equals method UnsortedSet/AbstractSet");

        // Test 8 for Unsorted/Abstract intersection
        s2.add("D");
        s3 = s1.intersection(s2);
        actual = s3.toString().equals("(Z, W, C)");
        showTestResults(actual, true, 8, s1, s3, "intersection method UnsortedSet");

        // Test 9 for Unsorted iterator
        s3.add("D");
        Iterator<String> it1 = s2.iterator();
        Iterator<String> it2 = s3.iterator();
        boolean equal = true;
        while (equal && it1.hasNext()) {
            equal = it1.next().equals(it2.next());
        }
        showTestResults(equal, true, 9, s3, s3, "iterator method UnsortedSet");

        // Test 10 for Unsorted/Abstract remove
        s3.remove("C");
        s3.remove("Z");
        s3.remove("D");
        actual = s3.toString().equals("(W)");
        showTestResults(actual, true, 10, s3, null, "remove method UnsortedSet/AbstractSet");

        // Test 11 for Unsorted size
        actual = s3.size() == 1;
        showTestResults(actual, true, 11, s3, null, "size method UnsortedSet");

        // Test 12 for Unsorted union
        s3 = s1.union(s2);
        actual = s3.toString().equals("(Z, Y, V, U, X, W, B, C, A, D)");
        showTestResults(actual, true, 12, s3, null, "union method UnsortedSet");

        // Tests for SortedSet:
        s1 = new SortedSet<>();
        s1.add("Z");
        s1.add("Y");
        s1.add("V");
        s1.add("U");
        s1.add("X");
        s1.add("W");
        s2 = new SortedSet<>();
        s2.add("Z");
        s2.add("B");
        s2.add("C");
        s2.add("A");

        // Test 13 for Sorted add
        actual = s1.toString().equals("(U, V, W, X, Y, Z)");
        showTestResults(actual, true, 13, s1, null, "add method SortedSet");

        // Test 14 for Sorted addAll
        s1.addAll(s2);
        actual = s1.toString().equals("(A, B, C, U, V, W, X, Y, Z)");
        showTestResults(actual, true, 14, s1, s2, "addAll method SortedSet");

        // Test 15 for Sorted clear
        s2.clear();
        actual = s2.toString().equals("()");
        showTestResults(actual, true, 15, s2, null, "clear method SortedSet");

        // Test 16 for Sorted contains
        actual = s1.contains("V");
        showTestResults(actual, true, 16, s1, null, "contains method SortedSet");

        // Test 17 for Sorted containsAll
        s2.add("Z");
        s2.add("W");
        s2.add("C");
        actual = s1.containsAll(s2);
        showTestResults(actual, true, 17, s1, s2, "containsAll method SortedSet");

        // Test 18 for Sorted difference
        s3 = s1.difference(s2);
        actual = s3.toString().equals("(A, B, U, V, X, Y)");
        showTestResults(actual, true, 18, s1, s2, "difference method SortedSet");

        // Test 19 for Sorted equals
        s3.add("Z");
        s3.add("W");
        s3.add("C");
        actual = s1.equals(s3);
        showTestResults(actual, true, 19, s1, s3, "equals method SortedSet");

        // Test 20 for Sorted intersection
        s2.add("D");
        s3 = s1.intersection(s2);
        actual = s3.toString().equals("(C, W, Z)");
        showTestResults(actual, true, 20, s1, s3, "intersection method SortedSet");

        // Test 21 for Sorted iterator
        s3.add("D");
        s3 = new SortedSet<>(s3);
        it1 = s2.iterator();
        it2 = s3.iterator();
        equal = true;
        while (equal && it1.hasNext()) {
            equal = it1.next().equals(it2.next());
        }
        showTestResults(equal, true, 21, s2, s3, "iterator method SortedSet");

        // Test 22 for Sorted remove
        s3.remove("C");
        s3.remove("Z");
        s3.remove("D");
        actual = s3.toString().equals("(W)");
        showTestResults(actual, true, 22, s3, null, "remove method SortedSet");

        // Test 23 for Sorted size
        actual = s3.size() == 1;
        showTestResults(actual, true, 23, s3, null, "size method SortedSet");

        // Test 24 for Sorted union
        s3 = s1.union(s2);
        s3 = new SortedSet<>(s3);
        actual = s3.toString().equals("(A, B, C, D, U, V, W, X, Y, Z)");
        showTestResults(actual, true, 24, s3, null, "union method SortedSet");

        // Test 25 for Sorted parameterized constructor
        s1 = new UnsortedSet<>();
        s1.add("Z");
        s1.add("M");
        s1.add("D");
        SortedSet<String> s4 = new SortedSet<>(s1);
        actual = s4.toString().equals("(D, M, Z)");
        showTestResults(actual, true, 25, s1, s4, "parameterized constructor SortedSet");

        // Test 26 for Sorted min
        actual = s4.min().equals("D");
        showTestResults(actual, true, 26, s4, null, "min method SortedSet");

        // Test 27 for Sorted max
        actual = s4.max().equals("Z");
        showTestResults(actual, true, 27, s4, null, "max method SortedSet");

        // Experiment Code:
//         try {
//         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//         }
//         catch(Exception e) {
//         System.out.println("Unable to change look and feel");
//         }
//         Scanner sc = new Scanner(System.in);
//         String response = "";
//         do {
//         largeTest();
//         System.out.print("Another file? Enter y to do another file: ");
//         response = sc.next();
//         } while( response != null && response.length() > 0
//         && response.substring(0,1).equalsIgnoreCase("y") );
    }

    // print out results of test
    private static <E> void showTestResults(boolean actualResult, boolean expectedResult,
                                            int testNumber, ISet<E> set1, ISet<E> set2, String testDescription) {
        if (actualResult == expectedResult) {
            System.out.println("Passed test " + testNumber);
        } else {
            System.out.print("Failed test ");
            System.out.println(testNumber + ": " + testDescription);
            System.out.println("Expected result: " + expectedResult);
            System.out.println("Actual result  : " + actualResult);
            System.out.println("Set 1: " + set1);
            if (set2 != null) {
                System.out.println("Set 2: " + set2);
            }
        }

    }

    /*
     * Method asks user for file and compares run times to add words from file
     * to various sets, including CS314 UnsortedSet and SortedSet and Java's
     * TreeSet and HashSet.
     */
    private static void largeTest() {
        System.out.println();
        System.out.println("Opening Window to select file. "
                + "You may have to minimize other windows.");
        String text = convertFileToString();
        Scanner keyboard = new Scanner(System.in);
        System.out.println();
        System.out.println("***** CS314 SortedSet: *****");
        processTextCS314Sets(new SortedSet<String>(), text, keyboard);
        System.out.println("****** CS314 UnsortedSet: *****");
        processTextCS314Sets(new UnsortedSet<String>(), text, keyboard);
        System.out.println("***** Java HashSet ******");
        processTextJavaSets(new HashSet<String>(), text, keyboard);
        System.out.println("***** Java TreeSet ******");
        processTextJavaSets(new TreeSet<String>(), text, keyboard);
    }

    /*
     * pre: set != null, text != null Method to add all words in text to the
     * given set. Words are delimited by white space. This version for CS314
     * sets.
     */
    private static void processTextCS314Sets(ISet<String> set, String text, Scanner keyboard) {
        Stopwatch s = new Stopwatch();
        Scanner sc = new Scanner(text);
        int totalWords = 0;
        s.start();
        while (sc.hasNext()) {
            totalWords++;
            set.add(sc.next());
        }
        s.stop();

        showResultsAndWords(set, s, totalWords, set.size(), keyboard);
    }

    /*
     * pre: set != null, text != null Method to add all words in text to the
     * given set. Words are delimited by white space. This version for Java
     * Sets.
     */
    private static void processTextJavaSets(Set<String> set, String text,
                                            Scanner keyboard) {
        Stopwatch s = new Stopwatch();
        Scanner sc = new Scanner(text);
        int totalWords = 0;
        s.start();
        while (sc.hasNext()) {
            totalWords++;
            set.add(sc.next());
        }
        s.stop();
        sc.close();

        showResultsAndWords(set, s, totalWords, set.size(), keyboard);
    }

    /*
     * Show results of add words to given set.
     */
    private static <E> void showResultsAndWords(Iterable<E> set, Stopwatch s, int totalWords,
                                                int setSize, Scanner keyboard) {

        System.out.println("Time to add the elements in the text to this set: " + s.toString());
        System.out.println("Total number of words in text including duplicates: " + totalWords);
        System.out.println("Number of distinct words in this text " + setSize);

        System.out.print("Enter y to see the contents of this set: ");
        String response = keyboard.next();

        if (response != null && response.length() > 0
                && response.substring(0, 1).equalsIgnoreCase("y")) {
            for (Object o : set) {
                System.out.println(o);
            }
        }
        System.out.println();
    }

    /*
     * Ask user to pick a file via a file choosing window and convert that file
     * to a String. Since we are evaluating the file with many sets convert to
     * string once instead of reading through file multiple times.
     */
    private static String convertFileToString() {
        // create a GUI window to pick the text to evaluate
        JFileChooser chooser = new JFileChooser(".");
        StringBuilder text = new StringBuilder();
        int retval = chooser.showOpenDialog(null);

        chooser.grabFocus();

        // read in the file
        if (retval == JFileChooser.APPROVE_OPTION) {
            File source = chooser.getSelectedFile();
            Scanner s = null;
            try {
                s = new Scanner(new FileReader(source));

                while (s.hasNextLine()) {
                    text.append(s.nextLine());
                    text.append(" ");
                }

                s.close();
            } catch (IOException e) {
                System.out.println("An error occured while trying to read from the file: " + e);
            } finally {
                if (s != null) {
                    s.close();
                }
            }
        }

        return text.toString();
    }
}