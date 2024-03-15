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

import java.util.ArrayList;
import java.util.List;

/**
 * Shell for a binary search tree class.
 *
 * @param <E> The data type of the elements of this BinarySearchTree.
 *            Must implement Comparable or inherit from a class that implements
 *            Comparable.
 * @author scottm
 */
public class BinarySearchTree<E extends Comparable<? super E>> {

    private BSTNode<E> root;
    private int size;

    /**
     * Add the specified item to this Binary Search Tree if it is not already present.
     * <br>
     * pre: <tt>value</tt> != null<br>
     * post: Add value to this tree if not already present. Return true if this tree
     * changed as a result of this method call, false otherwise.
     *
     * @param value the value to add to the tree
     * @return false if an item equivalent to value is already present
     * in the tree, return true if value is added to the tree and size() = old size() + 1
     */
    // add method takes in a comparable value and recursively adds it to binary search tree,
    // returning true if tree is altered, false otherwise
    // Preconditions: value != null
    // code adapted from lecture on binary search trees
    public boolean add(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value parameter cannot be null!");
        }
        int oldSize = size;
        root = addHelp(root, value);
        return (oldSize != size);
    }

    // add helper method to aid recursive process
    private BSTNode<E> addHelp(BSTNode<E> node, E val) {
        if (node == null) {
            size++;
            return new BSTNode<>(val);
        }
        int dir = val.compareTo(node.data);
        if (dir < 0) {
            node.left = addHelp(node.left, val);
        } else if (dir > 0) {
            node.right = addHelp(node.right, val);
        }
        return node;
    }

    /**
     * Remove a specified item from this Binary Search Tree if it is present.
     * <br>
     * pre: <tt>value</tt> != null<br>
     * post: Remove value from the tree if present, return true if this tree
     * changed as a result of this method call, false otherwise.
     *
     * @param value the value to remove from the tree if present
     * @return false if value was not present
     * returns true if value was present and size() = old size() - 1
     */
    // remove method takes in a comparable value and removes it from binary search tree,
    // returning true if tree is altered, false otherwise
    // Preconditions: value != null
    // code adapted from lecture on binary search trees
    public boolean remove(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value parameter cannot be null!");
        }
        int oldSize = size;
        root = removeHelp(root, value);
        return (oldSize != size);
    }

    // remove helper method to aid recursive process
    private BSTNode<E> removeHelp(BSTNode<E> node, E val) {
        if (node != null) {
            int dir = val.compareTo(node.data);
            if (dir < 0) {
                node.left = removeHelp(node.left, val);
            } else if (dir > 0) {
                node.right = removeHelp(node.right, val);
            } else {
                size--;
                if (node.left == null && node.right == null) {
                    node = null;
                } else if (node.right == null) {
                    node = node.left;
                } else if (node.left == null) {
                    node = node.right;
                } else {
                    node.data = minHelp(node.right);
                    node.right = removeHelp(node.right, node.data);
                    size++;
                }
            }
        }
        return node;
    }

    // remove helper method to find smallest value in one branch of tree
    private E minHelp(BSTNode<E> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    /**
     * Check to see if the specified element is in this Binary Search Tree.
     * <br>
     * pre: <tt>value</tt> != null<br>
     * post: return true if value is present in tree, false otherwise
     *
     * @param value the value to look for in the tree
     * @return true if value is present in this tree, false otherwise
     */
    // isPresent method takes in a comparable value and
    // returns true if present in tree, false otherwise
    // Preconditions: value != null
    public boolean isPresent(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value parameter cannot be null!");
        }
        BSTNode<E> temp = root;
        while (temp != null) {
            int dir = value.compareTo(temp.data);
            if (dir < 0) {
                temp = temp.left;
            } else if (dir > 0) {
                temp = temp.right;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Return how many elements are in this Binary Search Tree.
     * <br>
     * pre: none<br>
     * post: return the number of items in this tree
     *
     * @return the number of items in this Binary Search Tree
     */
    // size method returns size (total number of elements) of binary search tree
    public int size() {
        return size;
    }

    /**
     * return the height of this Binary Search Tree.
     * <br>
     * pre: none<br>
     * post: return the height of this tree.
     * If the tree is empty return -1, otherwise return the
     * height of the tree
     *
     * @return the height of this tree or -1 if the tree is empty
     */
    // height method returns height (depth of farthest node from root) of binary search tree
    // code adapted from lecture on binary search trees
    public int height() {
        return heightHelp(root);
    }

    // height helper method to aid recursive additive process
    private int heightHelp(BSTNode<E> r) {
        if (r == null) {
            return -1;
        }
        return 1 + Math.max(heightHelp(r.left), heightHelp(r.right));
    }

    /**
     * Return a list of all the elements in this Binary Search Tree.
     * <br>
     * pre: none<br>
     * post: return a List object with all data from the tree in ascending order.
     * If the tree is empty return an empty List
     *
     * @return a List object with all data from the tree in sorted order
     * if the tree is empty return an empty List
     */
    // getAll method returns a list of all elements from in-order traversal of binary search tree
    public List<E> getAll() {
        ArrayList<E> elements = new ArrayList<>();
        getAllHelp(root, elements);
        return elements;
    }

    // getAll helper method to aid recursive process and build list
    private void getAllHelp(BSTNode<E> node, ArrayList<E> elements) {
        if (node != null) {
            if (node.left != null) {
                getAllHelp(node.left, elements);
            }
            elements.add(node.data);
            if (node.right != null) {
                getAllHelp(node.right, elements);
            }
        }
    }

    /**
     * return the maximum value in this binary search tree.
     * <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the largest value in this Binary Search Tree
     *
     * @return the maximum value in this tree
     */
    // max method returns largest value in tree
    // Preconditions: size > 0
    public E max() {
        if (size <= 0) {
            throw new IllegalStateException("Tree cannot be empty!");
        }
        BSTNode<E> temp = root;
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.data;
    }

    /**
     * return the minimum value in this binary search tree.
     * <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the smallest value in this Binary Search Tree
     *
     * @return the minimum value in this tree
     */
    // min method returns smallest value in tree
    // Preconditions: size > 0
    public E min() {
        if (size <= 0) {
            throw new IllegalStateException("Tree cannot be empty!");
        }
        BSTNode<E> temp = root;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp.data;
    }

    /**
     * An add method that implements the add algorithm iteratively
     * instead of recursively.
     * <br>pre: data != null
     * <br>post: if data is not present add it to the tree,
     * otherwise do nothing.
     *
     * @param data the item to be added to this tree
     * @return true if data was not present before this call to add,
     * false otherwise.
     */
    // add method takes in a comparable value and iteratively adds it to binary search tree,
    // returning true if tree is altered, false otherwise
    // Preconditions: value != null
    public boolean iterativeAdd(E data) {
        if (data == null) {
            throw new IllegalArgumentException("Data parameter cannot be null!");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
            return true;
        }
        BSTNode<E> temp = root;
        boolean found = false;
        while (!found) {
            int dir = data.compareTo(temp.data);
            if (dir < 0) {
                if (temp.left == null) {
                    temp.left = new BSTNode<>(data);
                    size++;
                    return true;
                }
                temp = temp.left;
            } else if (dir > 0) {
                if (temp.right == null) {
                    temp.right = new BSTNode<>(data);
                    size++;
                    return true;
                }
                temp = temp.right;
            } else {
                found = true;
            }
        }
        return false;
    }

    /**
     * Return the "kth" element in this Binary Search Tree. If kth = 0 the
     * smallest value (minimum) is returned.
     * If kth = 1 the second smallest value is returned, and so forth.
     * <br>pre: 0 <= kth < size()
     *
     * @param kth indicates the rank of the element to get
     * @return the kth value in this Binary Search Tree
     */
    // get method takes in numerical rank and searches for value at that rank in the binary
    // search tree, returning said value
    // Preconditions: kth >= 0 && k < size
    public E get(int kth) {
        if (kth < 0 || kth >= size) {
            throw new IllegalArgumentException("K must be valid rank within tree!");
        }
        return getHelp(root, kth);
    }

    // get helper method to aid recursive process and search for kth element
    private E getHelp(BSTNode<E> node, int k) {
        int leftSize = sizeHelp(node.left);
        if (k == leftSize) {
            return node.data;
        } else if (k < leftSize) {
            return getHelp(node.left, k);
        } else {
            return getHelp(node.right, k - (leftSize + 1));
        }
    }

    // get helper method to calculate size of subtree
    private int sizeHelp(BSTNode<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeHelp(node.left) + sizeHelp(node.right);
    }

    /**
     * Return a List with all values in this Binary Search Tree
     * that are less than the parameter <tt>value</tt>.
     * <tt>value</tt> != null<br>
     *
     * @param value the cutoff value
     * @return a List with all values in this tree that are less than
     * the parameter value. If there are no values in this tree less
     * than value return an empty list. The elements of the list are
     * in ascending order.
     */
    // getAllLessThan method returns a list of all elements from binary search tree, in order, that
    // are less than a specific value
    // Preconditions: value != null
    public List<E> getAllLessThan(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value parameter cannot be null!");
        }
        ArrayList<E> elements = new ArrayList<>();
        getAllLessHelp(root, value, elements);
        return elements;
    }

    // getAllLessThan helper method to aid recursive process and build list
    private void getAllLessHelp(BSTNode<E> node, E val, ArrayList<E> elements) {
        if (node != null) {
            int dir = val.compareTo(node.data);
            if (dir > 0) {
                getAllHelp(node.left, elements);
                elements.add(node.data);
                getAllLessHelp(node.right, val, elements);
            } else if (dir == 0) {
                getAllHelp(node.left, elements);
            } else {
                getAllLessHelp(node.left, val, elements);
            }
        }
    }

    /**
     * Return a List with all values in this Binary Search Tree
     * that are greater than the parameter <tt>value</tt>.
     * <tt>value</tt> != null<br>
     *
     * @param value the cutoff value
     * @return a List with all values in this tree that are greater
     * than the parameter value. If there are no values in this tree
     * greater than value return an empty list.
     * The elements of the list are in ascending order.
     */
    // getAllGreaterThan method returns a list of all elements from binary search tree, in order,
    // that are greater than a specific value
    // Preconditions: value != null
    public List<E> getAllGreaterThan(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value parameter cannot be null!");
        }
        ArrayList<E> elements = new ArrayList<>();
        getAllGreaterHelp(root, value, elements);
        return elements;
    }

    // getAllGreaterThan helper method to aid recursive process and build list
    private void getAllGreaterHelp(BSTNode<E> node, E val, ArrayList<E> elements) {
        if (node != null) {
            int dir = val.compareTo(node.data);
            if (dir > 0) {
                getAllGreaterHelp(node.right, val, elements);
            } else if (dir == 0) {
                getAllHelp(node.right, elements);
            } else {
                getAllGreaterHelp(node.left, val, elements);
                elements.add(node.data);
                getAllHelp(node.right, elements);
            }
        }
    }

    /**
     * Find the number of nodes in this tree at the specified depth.
     * <br>pre: none
     *
     * @param d The target depth.
     * @return The number of nodes in this tree at a depth equal to
     * the parameter d.
     */
    // numNodesAtDepth method returns number of elements at specific depth in binary search tree
    public int numNodesAtDepth(int d) {
        return depthCountHelper(root, 0, d);
    }

    // getAllGreaterThan helper method to aid recursive process and count elements at depth
    private int depthCountHelper(BSTNode<E> node, int depth, int target) {
        if (node == null) {
            return 0;
        }
        if (depth == target) {
            return 1;
        }
        return depthCountHelper(node.left, depth + 1, target) + depthCountHelper(node.right,
                depth + 1, target);

    }

    /**
     * Prints a vertical representation of this tree.
     * The tree has been rotated counter clockwise 90
     * degrees. The root is on the left. Each node is printed
     * out on its own row. A node's children will not necessarily
     * be at the rows directly above and below a row. They will
     * be indented three spaces from the parent. Nodes indented the
     * same amount are at the same depth.
     * <br>pre: none
     */
    public void printTree() {
        printTree(root, "");
    }

    private void printTree(BSTNode<E> n, String spaces) {
        if (n != null) {
            printTree(n.getRight(), spaces + "  ");
            System.out.println(spaces + n.getData());
            printTree(n.getLeft(), spaces + "  ");
        }
    }

    private static class BSTNode<E extends Comparable<? super E>> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;

        public BSTNode() {
            this(null);
        }

        public BSTNode(E initValue) {
            this(null, initValue, null);
        }

        public BSTNode(BSTNode<E> initLeft,
                       E initValue,
                       BSTNode<E> initRight) {
            data = initValue;
            left = initLeft;
            right = initRight;
        }

        public E getData() {
            return data;
        }

        public void setData(E theNewValue) {
            data = theNewValue;
        }

        public BSTNode<E> getLeft() {
            return left;
        }

        public void setLeft(BSTNode<E> theNewLeft) {
            left = theNewLeft;
        }

        public BSTNode<E> getRight() {
            return right;
        }

        public void setRight(BSTNode<E> theNewRight) {
            right = theNewRight;
        }
    }
}