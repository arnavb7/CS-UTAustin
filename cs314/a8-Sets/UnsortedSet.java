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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple implementation of an ISet.
 * Elements are not in any particular order.
 * Students are to implement methods that
 * were not implemented in AbstractSet and override
 * methods that can be done more efficiently.
 * An ArrayList must be used as the internal storage container.
 */
public class UnsortedSet<E> extends AbstractSet<E> {

    private ArrayList<E> myCon;

    /**
     * Creates new instance of UnsortedSet
     */
    public UnsortedSet() {
        myCon = new ArrayList<>();
    }

    /**
     * Adds item to calling set
     *
     * @param item to be added, item != null
     * @return true if set altered, false otherwise
     */
    @Override
    // Big O: O(N)
    public boolean add(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item parameter cannot be null!");
        }
        boolean isPresent = contains(item);
        if (!isPresent) {
            myCon.add(item);
        }
        return !isPresent;
    }

    /**
     * Creates new set containing all elements from calling set excluding elements also in
     * otherSet
     *
     * @param otherSet being subtracted from calling set, otherSet != null
     * @return new set that is difference of calling set and otherSet
     */
    // Big O: O(N^2)
    @Override
    public ISet<E> difference(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        UnsortedSet<E> difference = new UnsortedSet<>();
        for (E val : this) {
            if (!otherSet.contains(val)) {
                difference.add(val);
            }
        }
        return difference;
    }

    /**
     * Creates an Iterator object for the elements of this set
     *
     * @return an Iterator object for the elements of this set
     */
    @Override
    // Big O: O(1)
    public Iterator<E> iterator() {
        return myCon.iterator();
    }

    /**
     * Counts number of elements in calling set
     *
     * @return the size of the set
     */
    // Big O: O(1)
    @Override
    public int size() {
        return myCon.size();
    }

    /**
     * Creates new set that contains all elements from both calling set and otherSet excluding
     * duplicates
     *
     * @param otherSet being merged with calling set, otherSet != null
     * @return new set that is union of calling set and otherSet
     */
    // Big O: O(N^2)
    @Override
    public ISet<E> union(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        UnsortedSet<E> union = new UnsortedSet<E>();
        union.addAll(this);
        union.addAll(otherSet);
        return union;
    }

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