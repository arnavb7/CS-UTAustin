/*
 * Student information for assignment:
 * On my honor, Arnav Bhasin, this programming assignment is my own work
 * and I have not provided this code to any other student.
 * UTEID: ab78845
 * email address: bhasin.arnav07@gmail.com
 * Number of slip days I am using: 2
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements IList<E> {
    // CS314 students. Add you instance variables here.
    // You decide what instance variables to use.
    // Must adhere to assignment requirements.
    // No ArrayLists or Java LinkedLists.

    private final DoubleListNode<E> HEADER;
    private int size;

    // CS314 students, add constructors here:
    public LinkedList() {
        HEADER = new DoubleListNode<E>();
        HEADER.next = HEADER;
        HEADER.prev = HEADER;
        size = 0;
    }

    /**
     * add item to the front of the list. <br>
     * pre: item != null <br>
     * post: size() = old size() + 1, get(0) = item
     *
     * @param item the data to add to the front of this list
     */
    public void addFirst(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = new DoubleListNode<E>(HEADER, item, HEADER.next);
        HEADER.next = temp;
        temp.next.prev = temp;
        size++;
    }

    /**
     * add item to the end of the list. <br>
     * pre: item != null <br>
     * post: size() = old size() + 1, get(size() -1) = item
     *
     * @param item the data to add to the end of this list
     */
    public void addLast(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = new DoubleListNode<E>(HEADER.prev, item, HEADER);
        HEADER.prev = temp;
        temp.prev.next = temp;
        size++;
    }

    /**
     * remove and return the first element of this list. <br>
     * pre: size() > 0 <br>
     * post: size() = old size() - 1
     *
     * @return the old first element of this list
     */
    public E removeFirst() {
        if (size <= 0) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = HEADER.next;
        HEADER.next = temp.next;
        temp.next.prev = HEADER;
        size--;
        return temp.data;
    }

    /**
     * remove and return the last element of this list. <br>
     * pre: size() > 0 <br>
     * post: size() = old size() - 1
     *
     * @return the old last element of this list
     */
    public E removeLast() {
        if (size <= 0) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = HEADER.prev;
        HEADER.prev = temp.prev;
        temp.prev.next = HEADER;
        size--;
        return temp.data;
    }

    /**
     * Add an item to the end of this list.
     * <br>pre: item != null
     * <br>post: size() = old size() + 1, get(size() - 1) = item
     * @param item the data to be added to the end of this list,
     * item != null
     */
    @Override
    public void add(E item) {
        addLast(item);
    }

    /**
     * Insert an item at a specified position in the list.
     * <br>pre: 0 <= pos <= size(), item != null
     * <br>post: size() = old size() + 1, get(pos) = item,
     * all elements in the list with a positon >= pos have a
     * position = old position + 1
     * @param pos the position to insert the data at in the list
     * @param item the data to add to the list, item != null
     */
    @Override
    public void insert(int pos, E item) {
        if (pos < 0 || pos > size || item == null) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp1 = HEADER.next;
        for (int i = 0; i < pos; i++) {
            temp1 = temp1.next;
        }
        DoubleListNode<E> temp2 = new DoubleListNode<>(temp1.prev, item, temp1);
        temp1.prev = temp2;
        temp2.prev.next = temp2;
        size++;
    }

    /**
     * Change the data at the specified position in the list.
     * the old data at that position is returned.
     * <br>pre: 0 <= pos < size(), item != null
     * <br>post: get(pos) = item, return the
     * old get(pos)
     * @param pos the position in the list to overwrite
     * @param item the new item that will overwrite the old item,
     * item != null
     * @return the old data at the specified position
     */
    @Override
    public E set(int pos, E item) {
        if (pos < 0 || pos >= size || item == null) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = HEADER.next;
        for (int i = 0; i < pos; i++) {
            temp = temp.next;
        }
        E prevItem = temp.data;
        temp.data = item;
        return prevItem;
    }

    /**
     * Get an element from the list.
     * <br>pre: 0 <= pos < size()
     * <br>post: return the item at pos
     * @param pos specifies which element to get
     * @return the element at the specified position in the list
     */
    @Override
    public E get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = HEADER.next;
        for (int i = 0; i < pos; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    /**
     * Remove an element in the list based on position.
     * <br>pre: 0 <= pos < size()
     * <br>post: size() = old size() - 1, all elements of
     * list with a position > pos have a position = old position - 1
     * @param pos the position of the element to remove from the list
     * @return the data at position pos
     */
    @Override
    public E remove(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = HEADER.next;
        for (int i = 0; i < pos; i++) {
            temp = temp.next;
        }
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        size--;
        return temp.data;
    }

    /**
     * Remove the first occurrence of obj in this list.
     * Return <tt>true</tt> if this list changed
     * as a result of this call, <tt>false</tt> otherwise.
     * <br>pre: obj != null
     * <br>post: if obj is in this list the first occurrence
     * has been removed and size() = old size() - 1.
     * If obj is not present the list is not altered in any way.
     * @param obj The item to remove from this list. obj != null
     * @return Return <tt>true</tt> if this list changed
     * as a result of this call, <tt>false</tt> otherwise.
     */
    @Override
    public boolean remove(E obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = HEADER.next;
        int i = 0;
        while (temp.getData() != obj) {
            if (i == size) {
                return false;
            }
            temp = temp.next;
            i++;
        }
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        size--;
        return true;
    }

    /**
     * Return a sublist of elements in this list
     * from <tt>start</tt> inclusive to <tt>stop</tt> exclusive.
     * This list is not changed as a result of this call.
     * <br>pre: <tt>0 <= start <= size(), start <= stop <= size()</tt>
     * <br>post: return a list whose size is stop - start
     * and contains the elements at positions start through stop - 1
     * in this list.
     * @param start index of the first element of the sublist.
     * @param stop stop - 1 is the index of the last element
     * of the sublist.
     * @return a list with <tt>stop - start</tt> elements,
     * The elements are from positions <tt>start</tt> inclusive to
     * <tt>stop</tt> exclusive in this list.
     * If start == stop an empty list is returned.
     */
    @Override
    public IList<E> getSubList(int start, int stop) {
        if (start < 0 || start > size || start > stop || stop > size) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        IList<E> resultant = new LinkedList<E>();
        DoubleListNode<E> temp = HEADER.next;
        for (int i = 0; i < start; i++) {
            temp = temp.next;
        }
        for (int i = start; i < stop; i++) {
            resultant.add(temp.data);
            temp = temp.next;
        }
        return resultant;
    }

    /**
     * Return the size of this list.
     * In other words the number of elements in this list.
     * <br>pre: none
     * <br>post: return the number of items in this list
     * @return the number of items in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Find the position of an element in the list.
     * <br>pre: item != null
     * <br>post: return the index of the first element equal to item
     * or -1 if item is not present
     * @param item the element to search for in the list. item != null
     * @return return the index of the first element equal to item
     * or a -1 if item is not present
     */
    @Override
    public int indexOf(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = HEADER.next;
        int i = 0;
        while (temp.data != item) {
            if (i == size) {
                return -1;
            }
            temp = temp.next;
            i++;
        }
        return i;
    }

    /**
     * find the position of an element in the list starting
     * at a specified position.
     * <br>pre: 0 <= pos < size(), item != null
     * <br>post: return the index of the first element equal
     * to item starting at pos
     * or -1 if item is not present from position pos onward
     * @param item the element to search for in the list. Item != null
     * @param pos the position in the list to start searching from
     * @return starting from the specified position
     * return the index of the first element equal to item
     * or a -1 if item is not present between pos
     * and the end of the list
     */
    @Override
    public int indexOf(E item, int pos) {
        if (pos < 0 || pos >= size || item == null) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        DoubleListNode<E> temp = HEADER.next;
        for (int i = 0; i < pos; i++) {
            temp = temp.next;
        }
        int i = pos;
        while (temp.data != item) {
            if (i == size) {
                return -1;
            }
            temp = temp.next;
            i++;
        }
        return i;
    }

    /**
     * return the list to an empty state.
     * <br>pre: none
     * <br>post: size() = 0
     */
    @Override
    public void makeEmpty() {
        HEADER.next = HEADER;
        HEADER.prev = HEADER;
        size = 0;
    }

    /**
     * return an Iterator for this list.
     * <br>pre: none
     * <br>post: return an Iterator object for this List
     */
    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    /**
     * Remove all elements in this list from <tt>start</tt>
     * inclusive to <tt>stop</tt> exclusive.
     * <br>pre: <tt>0 <= start <= size(), start <= stop <= size()</tt>
     * <br>post: <tt>size() = old size() - (stop - start)</tt>
     * @param start position at beginning of range of elements
     * to be removed
     * @param stop stop - 1 is the position at the end
     * of the range of elements to be removed
     */
    @Override
    public void removeRange(int start, int stop) {
        if (start < 0 || start > size || start > stop || stop > size) {
            throw new IllegalArgumentException("Parameters fail preconditions!");
        }
        if (size == 0 || start == stop) {
            return;
        }
        DoubleListNode<E> temp1 = HEADER.next;
        for (int i = 0; i < start; i++) {
            temp1 = temp1.next;
        }
        DoubleListNode<E> temp2 = temp1;
        for (int i = start; i < stop; i++) {
            temp2 = temp2.next;
        }
        temp1.prev.next = temp2;
        temp2.prev = temp1.prev;
        size -= (stop - start);
    }


    /**
     * Return a String version of this list enclosed in
     * square brackets, []. Elements are in
     * are in order based on position in the
     * list with the first element
     * first. Adjacent elements are separated by comma's
     * @return a String representation of this IList
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder build = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            E item = get(i);
            if (i == size - 1) {
                build.append(item + "]");
            }
            else {
                build.append(item + ", ");
            }
        }
        return build.toString();
    }

    /**
     * Determine if this IList is equal to other. Two
     * ILists are equal if they contain the same elements
     * in the same order.
     * @return true if this IList is equal to other, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        LinkedList<E> otherList = (LinkedList<E>) other;
        if (size != otherList.size) {
            return false;
        }
        DoubleListNode<E> temp1 = HEADER.next;
        DoubleListNode<E> temp2 = otherList.HEADER.next;
        while (temp1 != HEADER && temp2 != otherList.HEADER) {
            if (!temp1.data.equals(temp2.data)) {
                return false;
            }
            temp1 = temp1.next;
            temp2 = temp2.next;
        }
        return true;
    }

    private class CustomIterator implements Iterator<E> {
        private DoubleListNode<E> nodeLastReturnedToUser;
        private DoubleListNode<E> previousNode;

        public CustomIterator() {
            nodeLastReturnedToUser = HEADER;
            previousNode = null;
        }

        @Override
        public boolean hasNext() {
            return nodeLastReturnedToUser.next != HEADER;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            previousNode = nodeLastReturnedToUser;
            nodeLastReturnedToUser = nodeLastReturnedToUser.next;
            return nodeLastReturnedToUser.data;
        }

        @Override
        public void remove() {
            if (previousNode == null) {
                throw new IllegalStateException("remove() cannot be called if next() has never " +
                        "been called");
            }
            if (nodeLastReturnedToUser == HEADER.prev) {
                HEADER.prev = previousNode;
                previousNode.next = HEADER;
            }
            previousNode.next = nodeLastReturnedToUser.next;
            if (hasNext()) {
                nodeLastReturnedToUser.next.prev = previousNode;
            }
            size--;
        }
    }

    /**
     * A class that represents a node to be used in a linked list.
     * These nodes are doubly linked. All methods are O(1).
     *
     * @author Mike Scott
     * @version 9/25/2023
     */

    private static class DoubleListNode<E> {

        // instance variables

        // The data stored in this node.
        private E data;

        // The link to the next node (presumably in a list).
        private DoubleListNode<E> next;

        // The link to the previous node (presumably in a list).
        private DoubleListNode<E> prev;

        /**
         * default constructor.
         * <br>pre: none
         * <br>post: getData() = null, getNext() = null, getPrev() = null
         */
        public DoubleListNode() {
            this(null, null, null);
        }

        /**
         * create a DoubleListNode that holds the specified data
         * and refers to the specified next and previous elements.
         * <br>pre: none
         * <br>post: getData() = data, getNext() = next, getPrev() = prev
         * @param prev the previous node
         * @param data the  data this DoubleListNode should hold
         * @param next the next node
         */
        public DoubleListNode(DoubleListNode<E> prev, E data,
                              DoubleListNode<E> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }

        /* Note, the following methods are not necessary.
         * They are provided as a convenience. As this class is a private
         * nested class the only client is the LinkedList class itself and
         * the implementation of Iterator. We leave it up to the student
         * whether to access and change the private instance variables of a
         * node directly or via these methods.
         */
        /**
         * return the data in this node.
         * <br>pre: none
         * @return the data this DoubleListNode holds
         */
        public E getData() {
            return data;
        }

        /**
         * return the DoubleListNode this ListNode refers to.
         * <br>pre: none
         * @return the DoubleListNode this DoubleListNode refers to
         * (normally the next one in a list)
         */
        public DoubleListNode<E> getNext() {
            return next;
        }

        /**
         * return the DoubleListNode this DoubleListNode refers to.
         * <br>pre: none
         * @return the DoubleListNode this DoubleListNode refers to
         * (normally the previous one in a list)
         */
        public DoubleListNode<E> getPrev() {
            return prev;
        }

        /**
         * set the data in this node.
         * The old data is over written.
         * <br>pre: none
         * <br>post: getData() == data
         * @param data the new data for this DoubleListNode to hold
         */
        public void setData(E data) {
            this.data = data;
        }

        /**
         * set the next node this DoubleListNode refers to.
         * <br>pre: none
         * <br>post: getNext() = next
         * @param next the next node this DoubleListNode should refer to
         */
        public void setNext(DoubleListNode<E> next) {
            this.next = next;
        }

        /**
         * set the previous node this DoubleListNode refers to.
         * <br>pre: none
         * <br>post: getPrev() = next
         * @param prev the previous node this DoubleListNode should refer to
         */
        public void setPrev(DoubleListNode<E> prev) {
            this.prev = prev;
        }
    }
}