import java.util.Arrays;

// MathMatrix.java - CS314 Assignment 2

/*  Student information for assignment:
 *
 *  On my honor, Arnav Bhasin, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  Unique section number: 52665
 *  Number of slip days I am using: 1
 */

/**
 * A class that models systems of linear equations (Math Matrices)
 * as used in linear algebra.
 */
public class MathMatrix {
    // Private instance variable for 2D array representing mathematical matrix of integer values
    private int[][] values;

    /**
     * create a MathMatrix with cells equal to the values in mat.
     * A "deep" copy of mat is made.
     * Changes to mat after this constructor do not affect this
     * Matrix and changes to this MathMatrix do not affect mat
     * @param  mat  mat !=null, mat.length > 0, mat[0].length > 0,
     * mat is a rectangular matrix
     */
    // Public method that constructs a MathMatrix object, taking in 2D integer array as parameter
    // Preconditions: array must not be a null reference and number of rows and columns in array
    // must be greater than zero
    public MathMatrix(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            throw new IllegalArgumentException("Parameter fails preconditions!");
        }
        values = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                values[i][j] = mat[i][j];
            }
        }
    }

    /**
     * create a MathMatrix of the specified size with all cells set to the initialValue.
     * <br>pre: numRows > 0, numCols > 0
     * <br>post: create a matrix with numRows rows and numCols columns.
     * All elements of this matrix equal initialVal.
     * In other words after this method has been called getVal(r,c) = initialVal
     * for all valid r and c.
     * @param numRows numRows > 0
     * @param numCols numCols > 0
     * @param initialVal all cells of this Matrix are set to initialVal
     */
    // Public method that constructs a MathMatrix object, taking in parameters for number of rows
    // and columns of matrix, and integer value to be stored at every matrix position
    // Preconditions: number of rows and columns must be greater than zero
    public MathMatrix(int numRows, int numCols, int initialVal) {
        if (numRows <= 0 || numCols<= 0) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        values = new int[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                values[i][j] = initialVal;
            }
        }
    }

    /**
     * Get the number of rows.
     * @return the number of rows in this MathMatrix
     */
    // Public getter method returning the number of rows in the MathMatrix object
    public int getNumRows() {
        return values.length;
    }

    /**
     * Get the number of columns.
     * @return the number of columns in this MathMatrix
     */
    // Public getter method returning the number of columns in the MathMatrix object
    public int getNumColumns(){
        return values[0].length;
    }

    /**
     * get the value of a cell in this MathMatrix.
     * <br>pre: row  0 <= row < getNumRows(), col  0 <= col < getNumColumns()
     * @param  row  0 <= row < getNumRows()
     * @param  col  0 <= col < getNumColumns()
     * @return the value at the specified position
     */
    // Public getter method taking in two integer values for the specific row and column in the
    // MathMatrix and returning the integer value at that position in the MathMatrix
    // Preconditions: values for row and column must be valid positions in MathMatrix, so greater
    // than zero and less than the number of rows and columns, respectively
    public int getVal(int row, int col) {
        if (row < 0 || row >= getNumRows() || col < 0 || col >= getNumColumns()) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        return values[row][col];
    }

    // Private helper setter method taking in three integer values for the specific row and column
    // in the MathMatrix and the value to be set at that position in the MathMatrix
    // Preconditions: values for row and column must be valid positions in MathMatrix, so greater
    // than zero and less than the number of rows and columns, respectively
    private void setVal(int row, int col, int val) {
        values[row][col] = val;
    }

    /**
     * implements MathMatrix addition, (this MathMatrix) + rightHandSide.
     * <br>pre: rightHandSide != null, rightHandSide.getNumRows() = getNumRows(),
     * rightHandSide.numCols() = getNumColumns()
     * <br>post: This method does not alter the calling object or rightHandSide
     * @param rightHandSide rightHandSide.getNumRows() = getNumRows(),
     * rightHandSide.numCols() = getNumColumns()
     * @return a new MathMatrix that is the result of adding this Matrix to rightHandSide.
     * The number of rows in the returned Matrix is equal to the number of rows in this MathMatrix.
     * The number of columns in the returned Matrix is equal to the number of columns
     * in this MathMatrix.
     */
    // Public method taking in MathMatrix object and adding it to the MathMatrix being called on,
    // returning a new MathMatrix
    // Preconditions: two MathMatrix objects being added must have same number of rows and columns
    public MathMatrix add(MathMatrix rightHandSide){
        if (rightHandSide == null || rightHandSide.getNumRows() != values.length ||
                rightHandSide.getNumColumns() != values[0].length) {
            throw new IllegalArgumentException("Parameter fails preconditions!");
        }
        MathMatrix resultant = new MathMatrix(values.length, values[0].length, 0);
        for (int i = 0; i < resultant.getNumRows(); i++) {
            for (int j = 0; j < resultant.getNumColumns(); j++) {
                int sum = values[i][j] + rightHandSide.getVal(i, j);
                resultant.setVal(i, j, sum);
            }
        }
        return resultant;
    }

    /**
     * implements MathMatrix subtraction, (this MathMatrix) - rightHandSide.
     * <br>pre: rightHandSide != null, rightHandSide.getNumRows() = getNumRows(),
     * rightHandSide.numCols() = getNumColumns()
     * <br>post: This method does not alter the calling object or rightHandSide
     * @param rightHandSide rightHandSide.getNumRows() = getNumRows(),
     * rightHandSide.numCols() = getNumColumns()
     * @return a new MathMatrix that is the result of subtracting rightHandSide
     * from this MathMatrix. The number of rows in the returned MathMatrix is equal to the number
     * of rows in this MathMatrix.The number of columns in the returned MathMatrix is equal to
     * the number of columns in this MathMatrix.
     */
    // Public method taking in MathMatrix object and subtracting it from the MathMatrix being
    // called on, returning a new MathMatrix
    // Preconditions: two MathMatrix objects being added must have same number of rows and columns
    public MathMatrix subtract(MathMatrix rightHandSide){
        if (rightHandSide == null || rightHandSide.getNumRows() != values.length ||
                rightHandSide.getNumColumns() != values[0].length) {
            throw new IllegalArgumentException("Parameter fails preconditions!");
        }
        MathMatrix resultant = new MathMatrix(values.length, values[0].length, 0);
        for (int i = 0; i < resultant.getNumRows(); i++) {
            for (int j = 0; j < resultant.getNumColumns(); j++) {
                int difference = values[i][j] - rightHandSide.getVal(i, j);
                resultant.setVal(i, j, difference);
            }
        }
        return resultant;
    }

    /**
     * implements matrix multiplication, (this MathMatrix) * rightHandSide.
     * <br>pre: rightHandSide != null, rightHandSide.getNumRows() = getNumColumns()
     * <br>post: This method should not alter the calling object or rightHandSide
     * @param rightHandSide rightHandSide.getNumRows() = getNumColumns()
     * @return a new MathMatrix that is the result of multiplying 
     * this MathMatrix and rightHandSide.
     * The number of rows in the returned MathMatrix is equal to the number of rows
     * in this MathMatrix. The number of columns in the returned MathMatrix is equal to the number
     * of columns in rightHandSide.
     */
    // Public method taking in MathMatrix object and multiplying it by the MathMatrix being called
    // on, returning a new MathMatrix
    // Preconditions: number of rows in MathMatrix being called on and number of columns in
    // parameter MathMatrix must be equal
    public MathMatrix multiply(MathMatrix rightHandSide){
        if (rightHandSide == null || rightHandSide.getNumRows() != values[0].length) {
            throw new IllegalArgumentException("Parameter fails preconditions!");
        }
        MathMatrix resultant = new MathMatrix(values.length, rightHandSide.getNumColumns(), 0);
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < rightHandSide.getNumColumns(); j++) {
                int productSum = 0;
                for (int l = 0; l < values[0].length; l++) {
                    productSum += (values[i][l] * rightHandSide.getVal(l, j));
                }
                resultant.setVal(i, j, productSum);
            }
        }
        return resultant;
    }

    /**
     * Create and return a new Matrix that is a copy
     * of this matrix, but with all values multiplied by a scale
     * value.
     * <br>pre: none
     * <br>post: returns a new Matrix with all elements in this matrix
     * multiplied by factor.
     * In other words after this method has been called
     * returned_matrix.getVal(r,c) = original_matrix.getVal(r, c) * factor
     * for all valid r and c.
     * @param factor the value to multiply every cell in this Matrix by.
     * @return a MathMatrix that is a copy of this MathMatrix, but with all
     * values in the result multiplied by factor.
     */
    // Public method taking in a constant integer factor and multiplying it by the MathMatrix
    // being called on, returning a new MathMatrix
    public MathMatrix getScaledMatrix(int factor) {
        MathMatrix resultant = new MathMatrix(values.length, values[0].length, 0);
        for (int i = 0; i < resultant.getNumRows(); i++) {
            for (int j = 0; j < resultant.getNumColumns(); j++) {
                int product = factor * values[i][j];
                resultant.setVal(i, j, product);
            }
        }
        return resultant;
    }

    /**
     * accessor: get a transpose of this MathMatrix.
     * This Matrix is not changed.
     * <br>pre: none
     * @return a transpose of this MathMatrix
     */
    // Public method taking in MathMatrix object and mathematically transposing it, returning a
    // new MathMatrix
    public MathMatrix getTranspose() {
        MathMatrix resultant = new MathMatrix(values[0].length, values.length, 0);
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                resultant.setVal(j, i, values[i][j]);
            }
        }
        return resultant;
    }

    /**
     * override equals.
     * @return true if rightHandSide is the same size as this MathMatrix and all values in the
     * two MathMatrix objects are the same, false otherwise
     */
    // Public method taking in MathMatrix object and returning boolean value of its equivalence to
    // MathMatrix being called on
    public boolean equals(Object rightHandSide) {
        if (rightHandSide == null || this.getClass() != rightHandSide.getClass()) {
            return false;
        }
        // We know rightHandSide refers to a non-null MathMatrix object, safe to cast.
        MathMatrix otherMathMatrix = (MathMatrix) rightHandSide;
        // Now we can access the private instance variables of otherMathMatrix
        // and / or call MathMatrix methods on otherMathMatrix.
        if (values.length != otherMathMatrix.getNumRows() ||
                values[0].length != otherMathMatrix.getNumColumns()) {
            return false;
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                if (values[i][j] != otherMathMatrix.getVal(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * override toString.
     * @return a String with all elements of this MathMatrix.
     * Each row is on a separate line.
     * Spacing based on longest element in this Matrix.
     */
    // Public method returning a String representation of MathMatrix object being called on
    // with equal spacing and each row on a separate line
    public String toString() {
        int maxLength = getMaxLength(values);
        String resultant = "|";
        for (int i = 0; i < values.length; i++) {
            if (i != 0) {
                resultant += "|\n|";
            }
            for (int j = 0; j < values[0].length; j++) {
                String val = "" + values[i][j];
                int valLength = val.length();
                resultant += " ";
                for (int k = 0; k < maxLength - valLength; k++) {
                    resultant += " ";
                }
                resultant += values[i][j];
            }
        }
        resultant += "|\n";
        return resultant;
    }

    // Private helper setter method taking in an integer array and returning length of longest
    // value in terms of digits and symbol length, for example
    // "-300" is four digits long: -, 3, 0, and 0
    private int getMaxLength(int[][] arr) {
        int maxLength = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                String val = "" + arr[i][j];
                if (val.length() > maxLength) {
                    maxLength = val.length();
                }
            }
        }
        return maxLength;
    }

    /**
     * Return true if this MathMatrix is upper triangular. To
     * be upper triangular all elements below the main
     * diagonal must be 0.<br>
     * pre: this is a square matrix. getNumRows() == getNumColumns()
     * @return <tt>true</tt> if this MathMatrix is upper triangular,
     * <tt>false</tt> otherwise.
     */
    // Public method returning boolean value of whether MathMatrix object being called on is
    // mathematically upper triangular or not
    // Preconditions: the MathMatrix being called on must have the same number of columns and rows
    public boolean isUpperTriangular() {
        if (values.length != values[0].length) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                if (i > j && values[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // method to ensure mat is rectangular. It is public so that
    // tester classes can use it.
    // pre: mat != null, mat has at least one row
    // return true if all rows in mat have the same
    // number of columns false otherwise.
    public static boolean rectangularMatrix(int[][] mat) {
        if (mat == null || mat.length == 0) {
            throw new IllegalArgumentException("argument mat may not be null and must "
                    + " have at least one row. mat = " + Arrays.toString(mat));
        }
        boolean isRectangular = true;
        int row = 1;
        final int COLUMNS = mat[0].length;
        while (isRectangular && row < mat.length) {
            isRectangular = (mat[row].length == COLUMNS);
            row++;
        }
        return isRectangular;
    }
}