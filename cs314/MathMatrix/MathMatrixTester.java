import java.util.Random;

/*  Student information for assignment:
 *
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  Grader name: Brad
 *  Number of slip days I am using: 1
 */

// Results for Experiment 1:
// Trial 1: Two 600x600 matrices were added, time: 1.094570234 seconds
// Trial 2: Two 1200x1200 matrices were added, time: 2.645798336 seconds
// Trial 3: Two 2400x2400 matrices were added, time: 9.693240654 seconds
// Results for Experiment 2:
// Trial 1: Two 200x200 matrices were multiplied, time: 1.406141492 seconds
// Trial 2: Two 400x400 matrices were multiplied, time: 14.513051354 seconds
// Trial 3: Two 800x800 matrices were multiplied, time: 131.896791996 seconds
// Answers to Questions:
// 1. I would expect the add method to take approximately 36 seconds to complete if I
// were to double the dimensions of the MathMatrix again.
// 2. The Big O of the add method is O(N^2) based on my code, and the timing data supports this.
// 3. I would expect the multiply method to take approximately 1048 seconds to complete if I
// were to double the dimensions of the MathMatrix again.
// 4. The Big O of the multiply method is O(N^3) based on my code, and the timing data
// supports this.
// 5. The largest matrix I can create is a 10000x10000 MathMatrix. My program is allocated 400
// megabytes of RAM, taking up 2.5% of the RAM on my computer. If I were to increase the size
// further, the program would be allocated 40000 megabytes of RAM, taking up 250% of the RAM
// on my computer.

/**
 * A class to run tests on the MathMatrix class
 */
public class MathMatrixTester {

    /**
     * main method that runs simple test on the MathMatrix class
     *
     * @param args not used
     */
    public static void main(String[] args) {
        // Experiment Code:
        /* Experiment 1:
        Stopwatch s = new Stopwatch();
        MathMatrix arr1 = new MathMatrix(600, 600, 5);
        MathMatrix arr2 = new MathMatrix(600, 600, 10);
        int numTests = 1000;
        s.start();
        for (int i = 0; i < numTests; i++) {
            arr1.add(arr2);
        }
        s.stop();
        System.out.println(s.time()); // Gives time for trial 1
        arr1 = new MathMatrix(1200, 1200, 5);
        arr2 = new MathMatrix(1200, 1200, 10);
        s.start();
        for (int i = 0; i < numTests; i++) {
            arr1.add(arr2);
        }
        s.stop();
        System.out.println(s.time()); // Gives time for trial 2
        arr1 = new MathMatrix(2400, 2400, 5);
        arr2 = new MathMatrix(2400, 2400, 10);
        s.start();
        for (int i = 0; i < numTests; i++) {
            arr1.add(arr2);
        }
        s.stop();
        System.out.println(s.time()); // Gives time for trial 3
        // End of Experiment 1
        // Experiment 2:
        arr1 = new MathMatrix(200, 200, 5);
        arr2 = new MathMatrix(200, 200, 10);
        int numTests = 100;
        s.start();
        for (int i = 0; i < numTests; i++) {
            arr1.multiply(arr2);
        }
        s.stop();
        System.out.println(s.time()); // Gives time for trial 1
        arr1 = new MathMatrix(400, 400, 5);
        arr2 = new MathMatrix(400, 400, 10);
        s.start();
        for (int i = 0; i < numTests; i++) {
            arr1.multiply(arr2);
        }
        s.stop();
        System.out.println(s.time()); // Gives time for trial 2
        arr1 = new MathMatrix(800, 800, 5);
        arr2 = new MathMatrix(800, 800, 10);
        s.start();
        for (int i = 0; i < numTests; i++) {
            arr1.multiply(arr2);
        }
        s.stop();
        System.out.println(s.time()); // Gives time for trial 3
        // End of Experiment 2 */

        //Personal test 1 & 2 for constructor with specific sizes and initial value
        MathMatrix mat1 = new MathMatrix(3, 3, 10);
        int[][] e1 = new int[][] {{10, 10, 10}, {10, 10, 10}, {10, 10, 10}};
        printTestResult( get2DArray(mat1), e1, 1,
                "constructor with size and initial val specified");
        mat1 = new MathMatrix(3, 5, -2);
        e1 = new int[][] {{-2, -2, -2, -2, -2}, {-2, -2, -2, -2, -2}, {-2, -2, -2, -2, -2}};
        printTestResult( get2DArray(mat1), e1, 2,
                "constructor with size and initial val specified");

        //Personal tests 3 & 4 for constructor with integer array parameter
        int[][] data1 = { {0, 1, 2}, {3, 4, 5} };
        int[][] data2 = { {5, 4, 3}, {2, 1, 0} };
        mat1 = new MathMatrix( data1 );
        data1[0][0] = 1;
        e1 = new int[][] { {1, 1, 2}, {3, 4, 5} };
        printTestResult( data1, e1, 3, "constructor with one parameter of type int[][]");
        e1 = new int[][] { {0, 1, 2}, {3, 4, 5} };
        printTestResult( get2DArray(mat1), e1, 3,
                "constructor with one parameter of type int[][]. Testing deep copy made");
        mat1 = new MathMatrix( data2 );
        data2[0][0] = 1;
        e1 = new int[][] { {1, 4, 3}, {2, 1, 0} };
        printTestResult( data2, e1, 4, "constructor with one parameter of type int[][]");
        e1 = new int[][] { {5, 4, 3}, {2, 1, 0} };
        printTestResult( get2DArray(mat1), e1, 4,
                "constructor with one parameter of type int[][]. Testing deep copy made");

        //Personal tests 5 & 6 for get numRows method
        data1 = new int[][] { {0, 1, 2}, {3, 4, 5}, {7, 8, 9}, {10, 11, 12} };
        data2 = new int[][] { {5, 4, 3}, {2, 1, 0} };
        mat1 = new MathMatrix( data1 );
        MathMatrix mat2 = new MathMatrix( data2 );
        if (mat1.getNumRows() == 4) {
            System.out.println("Passed personal test 5, getNumRows method.");
        } else {
            System.out.println("Failed personal test 5, getNumRows method.");
        }
        if (mat2.getNumRows() == 2) {
            System.out.println("Passed personal test 6, getNumRows method.");
        } else {
            System.out.println("Failed personal test 6, getNumRows method.");
        }

        //Personal tests 7 & 8 for get numColumns method
        data1 = new int[][] { {0, 1, 2}, {3, 4, 5}, {7, 8, 9}, {10, 11, 12} };
        data2 = new int[][] { {5, 4}, {3, 2} };
        mat1 = new MathMatrix( data1 );
        mat2 = new MathMatrix( data2 );
        if (mat1.getNumColumns() == 3) {
            System.out.println("Passed personal test 7, getNumColumns method.");
        } else {
            System.out.println("Failed personal test 7, getNumColumns method.");
        }
        if (mat2.getNumColumns() == 2) {
            System.out.println("Passed personal test 8, getNumColumns method.");
        } else {
            System.out.println("Failed personal test 8, getNumColumns method.");
        }

        //Personal tests 9 & 10 for getVal method
        data1 = new int[][] { {0, 1, 2}, {3, 4, 5}, {7, 8, 9}, {10, 11, 12}};
        data2 = new int[][] { {5, 4, 3}, {2, 1, 0} };
        mat1 = new MathMatrix( data1 );
        mat2 = new MathMatrix( data2 );
        if (mat1.getVal(2, 1) == 8) {
            System.out.println("Passed personal test 9, getVal method.");
        } else {
            System.out.println("Failed personal test 9, getVal method.");
        }
        if (mat2.getVal(0, 2) == 3) {
            System.out.println("Passed personal test 10, getVal method.");
        } else {
            System.out.println("Failed personal test 10, getVal method.");
        }

        //Personal test 11 for addition method
        data1 = new int[][] { {0, 1, 2}, {3, 4, 5} };
        data2 = new int[][] { {5, 4, 3}, {2, 1, 0} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data2);
        data1[0][0] = 1;
        data2[0][0] = 1;
        MathMatrix mat3 = mat1.add(mat2);
        e1 = new int[][] { {0, 1, 2}, {3, 4, 5} };
        printTestResult( get2DArray(mat1), e1, 11, "add method. Testing mat1 unchanged");
        e1 = new int[][] { {5, 4, 3}, {2, 1, 0} };
        printTestResult( get2DArray(mat2), e1, 11, "add method. Testing mat2 unchanged");
        e1 = new int[][] { {5, 5, 5}, {5, 5, 5} };
        printTestResult( get2DArray(mat3), e1, 11, "add method. Testing mat3 correct result");

        //Personal test 12 for addition method
        data1 = new int[][] { {1, 1, 1}, {1, 1, 1} };
        data2 = new int[][] { {2, 2, 2}, {2, 2, 2} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data2);
        data1[0][0] = 0;
        data2[0][0] = 0;
        mat3 = mat1.add(mat2);
        e1 = new int[][] { {1, 1, 1}, {1, 1, 1} };
        printTestResult( get2DArray(mat1), e1, 12, "add method. Testing mat1 unchanged");
        e1 = new int[][] { {2, 2, 2}, {2, 2, 2} };
        printTestResult( get2DArray(mat2), e1, 12, "add method. Testing mat2 unchanged");
        e1 = new int[][] { {3, 3, 3}, {3, 3, 3} };
        printTestResult( get2DArray(mat3), e1, 12, "add method. Testing mat3 correct result");

        //Personal test 13 for subtraction method
        data1 = new int[][] { {0, 1, 2}, {3, 4, 5} };
        data2 = new int[][] { {5, 4, 3}, {2, 1, 0} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data2);
        data1[0][0] = 1;
        data2[0][0] = 1;
        mat3 = mat1.subtract(mat2);
        e1 = new int[][] { {0, 1, 2}, {3, 4, 5} };
        printTestResult( get2DArray(mat1), e1, 13, "subtract method. Testing mat1 unchanged");
        e1 = new int[][] { {5, 4, 3}, {2, 1, 0} };
        printTestResult( get2DArray(mat2), e1, 13, "subtract method. Testing mat2 unchanged");
        e1 = new int[][] { {-5, -3, -1}, {1, 3, 5} };
        printTestResult( get2DArray(mat3), e1, 13, "subtract method. Testing mat3 correct result");

        //Personal test 14 for subtraction method
        data1 = new int[][] { {2, 2, 2}, {2, 2, 2} };
        data2 = new int[][] { {-1, -1, -1}, {-1, -1, -1} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data2);
        data1[0][0] = 0;
        data2[0][0] = 0;
        mat3 = mat1.subtract(mat2);
        e1 = new int[][] { {2, 2, 2}, {2, 2, 2} };
        printTestResult( get2DArray(mat1), e1, 14, "subtract method. Testing mat1 unchanged");
        e1 = new int[][] { {-1, -1, -1}, {-1, -1, -1} };
        printTestResult( get2DArray(mat2), e1, 14, "subtract method. Testing mat2 unchanged");
        e1 = new int[][] { {3, 3, 3}, {3, 3, 3} };
        printTestResult( get2DArray(mat3), e1, 14, "subtract method. Testing mat3 correct result");

        //test 15, multiplication
        data1 = new int[][] { {4, 5, 8, 9}, {0, 9, 7, 9} };
        data2 = new int[][] { {9, 5, 0, 6, 4}, {2, 4, 8, 0, 7}, {4, 7, 9, 8, 4}, {6, 8, 5, 7, 1} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data2);
        mat3 = mat1.multiply(mat2);
        e1 = new int[][] { {132, 168, 157, 151, 92}, {100, 157, 180, 119, 100} };
        printTestResult( get2DArray(mat3), e1, 15, "multiply method");

        //test 16, multiplication
        data1 = new int[][] { {4, 5, 6}, {7, 8, 9} };
        data2 = new int[][] { {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data2);
        mat3 = mat1.multiply(mat2);
        e1 = new int[][] { {111, 126, 141}, {174, 198, 222} };
        printTestResult( get2DArray(mat3), e1, 16, "multiply method");

        //test 17, ScaledMatrix
        data1 = new int[][] { {1, 1, 1}, {1, 1, 1} };
        mat1 = new MathMatrix(data1);
        mat2 = mat1.getScaledMatrix(5);
        e1 = new int[][] { {5, 5, 5}, {5, 5, 5} };
        printTestResult( get2DArray(mat2), e1, 17, "scaledMatrix method");

        //test 18, ScaledMatrix
        data1 = new int[][] { {1, -2, 3}, {4, -5, 6} };
        mat1 = new MathMatrix(data1);
        mat2 = mat1.getScaledMatrix(2);
        e1 = new int[][] { {2, -4, 6}, {8, -10, 12} };
        printTestResult( get2DArray(mat2), e1, 18, "scaledMatrix method");

        //test 19, Transpose
        data1 = new int[][] { {1, 2}, {3, 4} };
        mat1 = new MathMatrix(data1);
        mat2 = mat1.getTranspose();
        e1 = new int[][] { {1, 3}, {2, 4} };
        printTestResult( get2DArray(mat2), e1, 19, "Transpose method");

        //test 20, Transpose
        data1 = new int[][] { {1, -2, 3}, {4, -5, 6}, {7, -8, 9} };
        mat1 = new MathMatrix(data1);
        mat2 = mat1.getTranspose();
        e1 = new int[][] { {1, 4, 7}, {-2, -5, -8}, {3, 6, 9} };
        printTestResult( get2DArray(mat2), e1, 20, "Transpose method");

        //test 21, equals()
        data1 = new int[][] { {1, 1}, {2, 2} };
        data2 = new int[][] { {1, 1}, {2, 2} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data2);
        if (mat1.equals(mat2)) {
            System.out.println("passed personal test 21, Equals method.");
        } else {
            System.out.println("failed personal test 21, Equals method.");
        }

        //test 22, equals()
        data1 = new int[][] { {5, 5, 5}, {5, 5, 5}, {5, 5, 5} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(3, 3, 5);
        if (mat1.equals(mat2)) {
            System.out.println("passed personal test 22, Equals method.");
        } else {
            System.out.println("failed personal test 22, Equals method.");
        }

        //test 23, toString()
        data1 = new int[][] { {1, 1}, {2, 2} };
        mat1 = new MathMatrix(data1);
        String expected = "| 1 1|\n|" +
                " 2 2|\n";
        if (mat1.toString().equals( expected )) {
            System.out.println("passed personal test 23, toString method.");
        } else {
            System.out.println("failed personal test 23, toString method.");
        }

        //test 24, toString()
        data1 = new int[][] { {1, 1, 1}, {2, 2, 2}, {3, 3, -3} };
        mat1 = new MathMatrix(data1);
        expected = "|  1  1  1|\n|" +
                "  2  2  2|\n|  3  3 -3|\n";
        if (mat1.toString().equals( expected )) {
            System.out.println("passed personal test 24, toString method.");
        } else {
            System.out.println("failed personal test 24, toString method.");
        }

        //test 25, upperTriangular
        data1 = new int[][] { {1, 2, 3, 4}, {0, 6, 7, 8}, {0, 0, 11, 12}, {0, 0, 0, 16} };
        mat1 = new MathMatrix(data1);
        if (mat1.isUpperTriangular()) {
            System.out.println("Passed personal test 25, upperTriangular method.");
        } else {
            System.out.println("Failed personal test 25, upperTriangular method.");
        }

        //test 26, upperTriangular
        data1 = new int[][] {{1, 0, 0, 0}, {5, 6, 0, 0}, {9, 10, 11, 0}, {13, 14, 15, 16}};
        mat1 = new MathMatrix(data1);
        if (!mat1.isUpperTriangular()) {
            System.out.println("Passed personal test 26, upperTriangular method.");
        } else {
            System.out.println("Failed personal test 26, upperTriangular method.");
        }

    }

    // method that sums elements of mat, may overflow int!
    // pre: mat != null
    private static int sumVals(MathMatrix mat) {
        if (mat == null) {
            throw new IllegalArgumentException("mat may not be null");
        }
        int result = 0;
        final int ROWS =  mat.getNumRows();
        final int COLS = mat.getNumColumns();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                result += mat.getVal(r, c); // likely to overflow, but can still do simple check
            }
        }
        return result;
    }

    // create a matrix with random values
    // pre: rows > 0, cols > 0, randNumGen != null
    public static MathMatrix createMat(Random randNumGen, int rows, int cols, final int LIMIT) {

        if (randNumGen == null) {
            throw new IllegalArgumentException("randomNumGen variable may no be null");
        } else if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("rows and columns must be greater than 0. " +
                    "rows: " + rows + ", cols: " + cols);
        }

        int[][] temp = new int[rows][cols];
        final int SUB = LIMIT / 4;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                temp[r][c] = randNumGen.nextInt(LIMIT) - SUB;
            }
        }

        return new MathMatrix(temp);
    }

    private static void printTestResult(int[][] data1, int[][] data2, int testNum,
                                        String testingWhat) {
        System.out.print("Personal Test number " + testNum + " tests the " + testingWhat +". " +
                "The test ");
        String result = equals(data1, data2) ? "passed" : "failed";
        System.out.println(result);
    }

    // pre: m != null, m is at least 1 by 1 in size
    // return a 2d array of ints the same size as m and with
    // the same elements
    private static int[][] get2DArray(MathMatrix m) {
        //check precondition
        if ((m == null) || (m.getNumRows() == 0)
                || (m.getNumColumns() == 0)) {
            throw new IllegalArgumentException("Violation of precondition: get2DArray");
        }

        int[][] result = new int[m.getNumRows()][m.getNumColumns()];
        for (int r = 0; r < result.length; r++) {
            for (int c = 0; c < result[0].length; c++) {
                result[r][c] = m.getVal(r,c);
            }
        }
        return result;
    }

    // pre: data1 != null, data2 != null, data1 and data2 are at least 1 by 1 matrices
    //      data1 and data2 are rectangular matrices
    // post: return true if data1 and data2 are the same size and all elements are
    //      the same
    private static boolean equals(int[][] data1, int[][] data2) {
        //check precondition
        if ((data1 == null) || (data1.length == 0)
                || (data1[0].length == 0) || !rectangularMatrix(data1)
                ||  (data2 == null) || (data2.length == 0)
                || (data2[0].length == 0) || !rectangularMatrix(data2)) {
            throw new IllegalArgumentException( "Violation of precondition: " +
                    "equals check on 2d arrays of ints");
        }
        boolean result = (data1.length == data2.length) && (data1[0].length == data2[0].length);
        int row = 0;
        while (result && row < data1.length) {
            int col = 0;
            while (result && col < data1[0].length) {
                result = (data1[row][col] == data2[row][col]);
                col++;
            }
            row++;
        }

        return result;
    }

    // method to ensure mat is rectangular
    // pre: mat != null, mat is at least 1 by 1
    private static boolean rectangularMatrix( int[][] mat ) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            throw new IllegalArgumentException("Violation of precondition: "
                    + " Parameter mat may not be null"
                    + " and must be at least 1 by 1");
        }
        return MathMatrix.rectangularMatrix(mat);
    }
}
