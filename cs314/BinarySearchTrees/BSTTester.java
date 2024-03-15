/*
 * Student information for assignment:
 *
 *  On my honor, Arnav Bhasin, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  TA name: Brad
 *  Number of slip days I am using: 0
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.TreeSet;

/**
Random Integer Adding Experiment Results for BinarySearchTree:
Results for N = 1000
    Average Time: 991406.0 nanoseconds
    Average Height: 21.7
    Average Size: 1000.0 nodes
Results for N = 2000
    Average Time: 408215.7 nanoseconds
    Average Height: 24.3
    Average Size: 2000.0 nodes
Results for N = 4000
    Average Time: 763761.6 nanoseconds
    Average Height: 26.8
    Average Size: 4000.0 nodes
Results for N = 8000
    Average Time: 1598593.1 nanoseconds
    Average Height: 28.5
    Average Size: 8000.0 nodes
Results for N = 16000
    Average Time: 3226037.5 nanoseconds
    Average Height: 32.1
    Average Size: 16000.0 nodes
Results for N = 32000
    Average Time: 7353211.5 nanoseconds
    Average Height: 35.2
    Average Size: 31999.9 nodes
Results for N = 64000
    Average Time: 1.88753982E7 nanoseconds
    Average Height: 38.0
    Average Size: 63999.5 nodes
Results for N = 128000
    Average Time: 5.60559779E7 nanoseconds
    Average Height: 40.8
    Average Size: 127998.3 nodes
Results for N = 256000
    Average Time: 8.95706267E7 nanoseconds
    Average Height: 42.7
    Average Size: 255991.9 nodes
Results for N = 512000
    Average Time: 2.548338311E8 nanoseconds
    Average Height: 45.2
    Average Size: 511968.0 nodes
Results for N = 1024000
    Average Time: 6.906727703E8 nanoseconds
    Average Height: 47.7
    Average Size: 1023879.0 nodes

For each value of N, the minimum possible height of the tree is Log2(N), assuming N unique
integers are inserted into the tree.

Random Integer Adding Experiment Results for TreeSet:
Results for N = 1000
    Average Time: 1124116.2 nanoseconds
    Average Size: 1000.0 nodes
Results for N = 2000
    Average Time: 714843.6 nanoseconds
    Average Size: 2000.0 nodes
Results for N = 4000
    Average Time: 1106869.2 nanoseconds
    Average Size: 4000.0 nodes
Results for N = 8000
    Average Time: 1654481.7 nanoseconds
    Average Size: 8000.0 nodes
Results for N = 16000
    Average Time: 3634733.5 nanoseconds
    Average Size: 16000.0 nodes
Results for N = 32000
    Average Time: 9611665.9 nanoseconds
    Average Size: 31999.9 nodes
Results for N = 64000
    Average Time: 2.3355642E7 nanoseconds
    Average Size: 63999.7 nodes
Results for N = 128000
    Average Time: 7.67869378E7 nanoseconds
    Average Size: 127998.4 nodes
Results for N = 256000
    Average Time: 1.241841372E8 nanoseconds
    Average Size: 255991.7 nodes
Results for N = 512000
    Average Time: 3.007680458E8 nanoseconds
    Average Size: 511969.7 nodes
Results for N = 1024000
    Average Time: 7.701927196E8 nanoseconds
    Average Size: 1023875.7 nodes

The average times for the TreeSet are higher than those for the BinarySearchTree.

Sorted Integer Adding for BinarySearchTree:
Results for N = 1000
   Average Time: 2542059.1 nanoseconds
Results for N = 2000
   Average Time: 4847608.9 nanoseconds
Results for N = 4000
   Average Time: 2.09546309E7 nanoseconds
Results for N = 8000
   Average Time: 8.63995891E7 nanoseconds
Results for N = 16000
   Average Time: 3.740650328E8 nanoseconds
Results for N = 32000
   Average Time: 1.769427998E9 nanoseconds
Results for N = 64000
   Average Time: 7.5501225371E9 nanoseconds

The average size of the tree is N, and the average height of the tree is N - 1, with N being the
number of elements in the tree.
I would predict the time to be 0.51 seconds for 128000 elements, 1.03 second for 256000
elements, and 2.05 seconds for 512000 elements as the add method is O(N) for sorted values.

Sorted Integer Adding for TreeSet:
Results for N = 1000
    Average Time: 697058.5 nanoseconds
Results for N = 2000
    Average Time: 318537.6 nanoseconds
Results for N = 4000
    Average Time: 666942.8 nanoseconds
Results for N = 8000
    Average Time: 924461.6 nanoseconds
Results for N = 16000
    Average Time: 1840096.1 nanoseconds
Results for N = 32000
    Average Time: 3864296.2 nanoseconds
Results for N = 64000
    Average Time: 1.02112991E7 nanoseconds

The average times for the TreeSet are lower than those for the BinarySearchTree. I think this is
because TreeSets are red-black trees and are self-balancing while binary search trees are not, so
we end up with skewed trees in sorted add where the height is N - 1.
 */

/**
 * Some test cases for CS314 Binary Search Tree assignment.
 *
 */
public class BSTTester {

    /**
     * The main method runs the tests.
     * @param args Not used
     */
    public static void main(String[] args) {
        // Personal Tests:
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(42);
        tree.add(44);
        tree.add(35);
        tree.add(55);
        tree.add(21);
        tree.add(34);
        tree.add(37);
        tree.add(38);
        tree.add(60);
        tree.add(51);
        System.out.println("Initial Tree");

        // Test 1: recursive add
        System.out.println("Test 1: add method");
        showTestResults(tree.add(30), 1);

        // Test 2: recursive add
        System.out.println("Test 2: add method");
        showTestResults(tree.add(36), 2);

        // Test 3: remove
        System.out.println("Test 3: remove method");
        showTestResults(tree.remove(30), 3);

        // Test 4: remove
        System.out.println("Test 4: remove method");
        showTestResults(tree.remove(35), 4);

        // Test 5: isPresent
        System.out.println("Test 5: isPresent method");
        showTestResults(tree.isPresent(34), 5);

        // Test 6: isPresent
        System.out.println("Test 6: isPresent method");
        showTestResults(!tree.isPresent(39), 6);

        // Test 7: size
        System.out.println("Test 7: size method");
        showTestResults(tree.size() == 10, 7);

        tree.remove(60);
        // Test 8: size
        System.out.println("Test 8: size method");
        showTestResults(tree.size()== 9, 8);

        // Test 9: height
        System.out.println("Test 9: height method");
        showTestResults(tree.height() == 3, 9);

        tree.add(50);
        // Test 10: height
        System.out.println("Test 10: height method");
        showTestResults(tree.height() == 4, 10);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(21);
        list.add(34);
        list.add(36);
        list.add(37);
        list.add(38);
        list.add(42);
        list.add(44);
        list.add(50);
        list.add(51);
        list.add(55);
        // Test 11: getAll
        System.out.println("Test 11: getAll method");
        showTestResults(tree.getAll().equals(list), 11);

        tree.add(49);
        list.add(7, 49);
        // Test 12: getAll
        System.out.println("Test 12: getAll method");
        showTestResults(tree.getAll().equals(list), 12);

        // Test 13: max
        System.out.println("Test 13: max method");
        showTestResults(tree.max() == 55, 13);

        tree.remove(55);
        // Test 14: max
        System.out.println("Test 14: max method");
        showTestResults(tree.max() == 51, 14);

        // Test 15: min
        System.out.println("Test 15: min method");
        showTestResults(tree.min() == 21, 15);

        tree.add(19);
        // Test 16: min
        System.out.println("Test 16: min method");
        showTestResults(tree.min() == 19, 16);

        // Test 17: iterativeAdd
        System.out.println("Test 17: iterativeAdd method");
        showTestResults(tree.iterativeAdd(20), 17);

        // Test 18: iterativeAdd
        System.out.println("Test 18: iterativeAdd method");
        showTestResults(!tree.iterativeAdd(19), 18);

        // Test 19: get
        System.out.println("Test 19: get method");
        showTestResults(tree.get(2) == 21, 19);

        // Test 20: get
        System.out.println("Test 20: get method");
        showTestResults(tree.get(9) == 49, 20);

        list.clear();
        list.add(19);
        list.add(20);
        list.add(21);
        // Test 21: getAllLessThan
        System.out.println("Test 21: getAllLessThan method");
        showTestResults(tree.getAllLessThan(34).equals(list), 21);

        list.add(34);
        list.add(36);
        list.add(37);
        // Test 22: getAllLessThan
        System.out.println("Test 22: getAllLessThan method");
        showTestResults(tree.getAllLessThan(38).equals(list), 22);

        list.clear();
        list.add(44);
        list.add(49);
        list.add(50);
        list.add(51);
        // Test 23: getAllGreaterThan
        System.out.println("Test 23: getAllGreaterThan method");
        showTestResults(tree.getAllGreaterThan(42).equals(list), 23);

        list.add(0, 42);
        list.add(0, 38);
        list.add(0, 37);
        // Test 24: getAllGreaterThan
        System.out.println("Test 24: getAllGreaterThan method");
        showTestResults(tree.getAllGreaterThan(36).equals(list), 24);

        // Test 25: numNodesAtDepth
        System.out.println("Test 25: numNodesAtDepth method");
        showTestResults(tree.numNodesAtDepth(2) == 3, 25);

        // Test 26: numNodesAtDepth
        System.out.println("Test 26: numNodesAtDepth method");
        showTestResults(tree.numNodesAtDepth(3) == 4, 26);
    }

    private static void showTestResults(boolean passed, int testNum) {
        if (passed) {
            System.out.println("Test " + testNum + " passed.");
        } else {
            System.out.println("TEST " + testNum + " FAILED.");
        }
    }
}