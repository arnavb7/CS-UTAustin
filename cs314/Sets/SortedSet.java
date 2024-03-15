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
 * In this implementation of the ISet interface the elements in the Set are
 * maintained in ascending order.
 * <p>
 * The data type for E must be a type that implements Comparable.
 * <p>
 * Implement methods that were not implemented in AbstractSet
 * and override methods that can be done more efficiently. An ArrayList must
 * be used as the internal storage container. For methods involving two set,
 * if that method can be done more efficiently if the other set is also a
 * SortedSet, then do so.
 */
public class SortedSet<E extends Comparable<? super E>> extends AbstractSet<E> {

    private ArrayList<E> myCon;

    /**
     * create an empty SortedSet
     */
    public SortedSet() {
        myCon = new ArrayList<>();
    }

    /**
     * Create a copy of other that is sorted.<br>
     *
     * @param other != null
     */
    // Big O: O(NLogN)
    public SortedSet(ISet<E> other) {
        if (other == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        myCon = new ArrayList<>();
        for (E item : other) {
            add(item);
        }
    }

    /**
     * Return the smallest element in this SortedSet.
     * <br> pre: size() != 0
     *
     * @return the smallest element in this SortedSet.
     */
    // Big O: O(1)
    public E min() {
        if (size() == 0) {
            throw new IllegalArgumentException("This operation cannot be called on empty set!");
        }
        return myCon.get(0);
    }

    /**
     * Return the largest element in this SortedSet.
     * <br> pre: size() != 0
     *
     * @return the largest element in this SortedSet.
     */
    // Big O: O(1)
    public E max() {
        if (size() == 0) {
            throw new IllegalArgumentException("This operation cannot be called on empty set!");
        }
        return myCon.get(size() - 1);
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
        int index = binarySearch(item);
        if (index < 0) {
            int i = 0;
            while (i < size() && item.compareTo(myCon.get(i)) > 0) {
                i++;
            }
            myCon.add(i, item);
            return true;
        }
        return false;
    }

    /**
     * Adds all items from otherSet to calling set excluding duplicates
     *
     * @param otherSet to be added, otherSet != null
     * @return true if set altered, false otherwise
     */
    // Big O: O(N)
    @Override
    public boolean addAll(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        if (otherSet instanceof SortedSet) {
            ArrayList<E> otherCon = ((SortedSet<E>) otherSet).myCon, newCon = new ArrayList<>();
            int i = 0, j = 0;
            while (i < size() && j < otherCon.size()) {
                if (myCon.get(i).compareTo(otherCon.get(j)) < 0) {
                    newCon.add(myCon.get(i));
                    i++;
                } else if (myCon.get(i).compareTo(otherCon.get(j)) > 0) {
                    newCon.add(otherCon.get(j));
                    j++;
                } else {
                    newCon.add(myCon.get(i));
                    i++;
                    j++;
                }
            }
            while (i < size()) {
                newCon.add(myCon.get(i));
                i++;
            }
            while (j < otherCon.size()) {
                newCon.add(newCon.get(j));
                j++;
            }
            ArrayList<E> tempCon = myCon;
            myCon = newCon;
            return !tempCon.equals(myCon);
        } else {
            return super.addAll(otherSet);
        }
    }

    /**
     * Removes all items from calling set
     */
    // Big O: O(N)
    @Override
    public void clear() {
        myCon.clear();
    }

    /**
     * Determines if item is in calling set
     *
     * @param item being checked in calling set, item != null
     * @return true if calling set contains item, false otherwise
     */
    // Big O: O(LogN)
    @Override
    public boolean contains(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item parameter cannot be null!");
        }
        return (binarySearch(item) >= 0);
    }

    /**
     * Determines if all items from otherSet are in calling set
     *
     * @param otherSet being checked in calling set, otherSet != null
     * @return true if calling set contains all items from otherSet, false otherwise
     */
    // Big O: O(N)
    @Override
    public boolean containsAll(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        if (otherSet instanceof SortedSet) {
            ArrayList<E> otherCon = ((SortedSet<E>) otherSet).myCon;
            if (otherCon.size() > size()) {
                return false;
            }
            int i = 0, j = 0;
            while (i < size() && j < otherCon.size()) {
                if (myCon.get(i).compareTo(otherCon.get(j)) == 0) {
                    j++;
                }
                i++;
            }
            return j == otherCon.size();
        } else {
            return super.containsAll(otherSet);
        }
    }

    /**
     * Creates new set containing all elements from calling set excluding elements also in
     * otherSet
     *
     * @param otherSet being subtracted from calling set, otherSet != null
     * @return new set that is difference of calling set and otherSet
     */
    // Big O: O(N)
    @Override
    public ISet<E> difference(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        SortedSet<E> difference = new SortedSet<>();
        if (otherSet instanceof SortedSet) {
            ArrayList<E> otherCon = ((SortedSet<E>) otherSet).myCon, newCon = new ArrayList<>();
            int i = 0, j = 0;
            while (i < size() && j < otherSet.size()) {
                if (myCon.get(i).compareTo(otherCon.get(j)) < 0) {
                    newCon.add(myCon.get(i));
                    i++;
                } else if (myCon.get(i).compareTo(otherCon.get(j)) > 0) {
                    j++;
                } else {
                    i++;
                    j++;
                }
            }
            while (i < size()) {
                newCon.add(myCon.get(i));
                i++;
            }
            difference.myCon = newCon;
            return difference;
        } else {
            for (E val : this) {
                if (!otherSet.contains(val)) {
                    difference.add(val);
                }
            }
            return difference;
        }
    }

    /**
     * Determines if calling set is equal to object other, with criteria for equal being if
     * they have exactly the same elements, discounting order
     *
     * @param other the object to compare to this set
     * @return true if calling set is equal to other, false otherwise
     */
    // Big O: O(N)
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
        SortedSet<E> otherSortedSet;
        if (otherSet instanceof SortedSet<?>) {
            otherSortedSet = (SortedSet<E>) otherSet;
        } else {
            otherSortedSet = new SortedSet<E>((ISet<E>) otherSet);
        }
        return myCon.equals(otherSortedSet.myCon);
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
    // Big O: O(N)
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
     * Helper Binary Search algorithm adapted from code in CS314 lecture slides
     */
    private int binarySearch(E target) {
        int targetIndex = -1;
        int low = 0;
        int high = myCon.size() - 1;
        while (targetIndex == -1 && low <= high) {
            int mid = low + ((high - low) / 2);
            int compareResult = target.compareTo(myCon.get(mid));
            if (compareResult == 0) {
                targetIndex = mid;
            } else if (compareResult > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return targetIndex;
    }
}