/*  Student information for assignment:
 *
 *  On OUR honor, Arnav Bhasin and Aaron Zhao, this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  Grader name: Brad
 *  Section number: 52665
 *
 *  Student 2
 *  UTEID: yz28676
 *  email address: aaron.zhao.a.z@gmail.com
 */

import java.util.Iterator;
import java.util.LinkedList;

public class PriorityQueue314<E extends Comparable<E>> {

    /**
     * instance variable representing internal container for priority queue
     */
    private LinkedList<E> con;

    /**
     * constructor for priority queue
     */
    public PriorityQueue314() {
        con = new LinkedList<>();
    }

    /**
     * adds element to priority queue at proper place
     *
     * @param value to be added to priority queue
     */
    public void enqueue(E value) {
        Iterator<E> iterator = con.iterator();
        int index = 0;
        while (iterator.hasNext() && value.compareTo(iterator.next()) >= 0) {
            index++;
        }
        con.add(index, value);
    }

    /**
     * removes and returns first element from queue
     *
     * @return first element from queue
     */
    public E dequeue() {
        return con.remove();
    }

    /**
     * gets first element from queue without removing it
     *
     * @return first element from queue
     */
    public E front() {
        return con.element();
    }

    /**
     * returns whether or not queue is empty
     *
     * @return true if queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return con.isEmpty();
    }

    /**
     * gets current size of priority queue
     *
     * @return int size of queue
     */
    public int size() {
        return con.size();
    }

    /**
     * @return String representation of priority queue
     */
    public String toString() {
        StringBuilder build = new StringBuilder("{");
        Iterator<E> iterator = con.iterator();
        while (iterator.hasNext()) {
            build.append(iterator.next());
            if (iterator.hasNext()) {
                build.append(", ");
            }
        }
        build.append("}");
        return build.toString();
    }
}
