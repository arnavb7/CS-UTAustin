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

import java.util.Iterator;

/**
 * Students are to complete this class.
 * Students should implement as many methods
 * as they can using the Iterator from the iterator
 * method and the other methods.
 */
public abstract class AbstractSet<E> implements ISet<E> {

    /**
     * Adds item to calling set
     *
     * @param item to be added, item != null
     * @return true if set altered, false otherwise
     */
    @Override
    public abstract boolean add(E item);

    /**
     * Adds all items from otherSet to calling set excluding duplicates
     *
     * @param otherSet to be added, otherSet != null
     * @return true if set altered, false otherwise
     */
    // Big O: O(N^2)
    @Override
    public boolean addAll(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        boolean changed = false;
        for (E otherVal : otherSet) {
            if (add(otherVal)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Removes all items from calling set
     */
    // Big O: O(N)
    @Override
    public void clear() {
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    /**
     * Determines if item is in calling set
     *
     * @param item being checked in calling set, item != null
     * @return true if calling set contains item, false otherwise
     */
    // Big O: O(N)
    @Override
    public boolean contains(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item parameter cannot be null!");
        }
        for (E val : this) {
            if (val.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if all items from otherSet are in calling set
     *
     * @param otherSet being checked in calling set, otherSet != null
     * @return true if calling set contains all items from otherSet, false otherwise
     */
    // Big O: O(N^2)
    @Override
    public boolean containsAll(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        for (E otherVal : otherSet) {
            if (!contains(otherVal)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates new set containing all elements from calling set excluding elements also in
     * otherSet
     *
     * @param otherSet being subtracted from calling set, otherSet != null
     * @return new set that is difference of calling set and otherSet
     */
    @Override
    public abstract ISet<E> difference(ISet<E> otherSet);

    /**
     * Determines if calling set is equal to object other, with criteria for equal being if
     * they have exactly the same elements, discounting order
     *
     * @param other the object to compare to this set
     * @return true if calling set is equal to other, false otherwise
     */
    // Big O: O(N^2)
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ISet<?>)) {
            return false;
        }
        ISet<?> otherSet = (ISet<?>) other;
        if (size() != otherSet.size()) {
            return false;
        }
        for (E item : this) {
            boolean isPresent = false;
            for (Object otherItem : otherSet) {
                if (item.equals(otherItem)) {
                    isPresent = true;
                }
            }
            if (!isPresent) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates new set containing only duplicate elements present in both calling set and otherSet
     *
     * @param otherSet being intersected with calling set, otherSet != null
     * @return new set that is intersection of calling set and otherSet
     */
    // Big O: O(N^2)
    @Override
    public ISet<E> intersection(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        ISet<E> union = this.union(otherSet);
        ISet<E> diff1 = this.difference(otherSet);
        ISet<E> diff2 = otherSet.difference(this);
        ISet<E> temp = union.difference(diff1);
        ISet<E> intersection = temp.difference(diff2);
        return intersection;
    }

    /**
     * Creates an Iterator object for the elements of this set
     *
     * @return an Iterator object for the elements of this set
     */
    @Override
    public abstract Iterator<E> iterator();

    /**
     * Remove specified item from calling set if exists.
     *
     * @param item to remove from the set, item != null
     * @return true if set altered, false otherwise
     */
    // Big O: O(N)
    @Override
    public boolean remove(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item parameter cannot be null!");
        }
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Counts number of elements in calling set
     *
     * @return the size of the set
     */
    // Big O: O(N)
    @Override
    public int size() {
        int count = 0;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    /**
     * Creates new set that contains all elements from both calling set and otherSet excluding
     * duplicates
     *
     * @param otherSet being merged with calling set, otherSet != null
     * @return new set that is union of calling set and otherSet
     */
    @Override
    public abstract ISet<E> union(ISet<E> otherSet);

    /**
     * Creates String representation of calling set, formatted as (e1, e2, ... en)
     *
     * @return calling set in String representation
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        String seperator = ", ";
        result.append("(");

        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            result.append(seperator);
        }
        // get rid of extra separator
        if (this.size() > 0) {
            result.setLength(result.length() - seperator.length());
        }

        result.append(")");
        return result.toString();
    }
}