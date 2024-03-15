/*
 * Student information for assignment: Replace <NAME> in the following with your
 * name. You are stating, on your honor you did not copy any other code on this
 * assignment and have not provided your code to anyone.
 *
 * On my honor, Arnav Bhasin, this programming assignment is my own work
 * and I have not provided this code
 * to any other student.
 *
 * UTEID: ab78845
 * email address: bhasin.arnav07@gmail.com
 * Number of slip days I am using: 1
 */

import java.util.ArrayList;

public class NameRecord implements Comparable<NameRecord> {

    // Private instance string variable for NameRecord representing the name being recorded
    private String name;

    // Private instance int variable for NameRecord representing the base decade from when this
    // name is ranked
    private int baseDecade;

    // Private instance int arraylist variable for NameRecord storing a list of the popularity
    // ranks for all decades in which this name is ranked
    private ArrayList<Integer> ranks;

    // Public method that constructs a NameRecord object, taking in string representing name,
    // int representing base decade, and list representing ranks for all decades after base
    public NameRecord(String name, int baseDecade, ArrayList<Integer> ranks) {
        this.name = name;
        this.baseDecade = baseDecade;
        this.ranks = ranks;
    }

    // Public getter method returning the name stored in this NameRecord object
    public String getName() {
        return name;
    }

    // Public getter method returning starting base decade from when this name's rank is recorded
    public int getBaseDecade() {
        return baseDecade;
    }

    // Public getter method returning total number of decades where name rank is recorded
    public int getTotalRanks() {
        return ranks.size();
    }

    // Public getter method taking in int value (int represents num of decades after base) and
    // returning value of rank in that decade
    // Preconditions: decade parameter value must be within range of 0 up to max number of ranks
    public int getRank(int decade) {
        return ranks.get(decade);
    }

    // Public method returning year in which name has best rank
    public int bestDecade() {
        int best = ranks.get(0);
        int bestIndex = 0;
        for (int i = 1; i < ranks.size(); i++) {
            if (best == 0) {
                best = ranks.get(i);
                bestIndex = i;
            }
            else {
                if (ranks.get(i) == 0) {
                    continue;
                }
                else if (ranks.get(i) <= best) {
                    best = ranks.get(i);
                    bestIndex = i;
                }
            }
        }
        return (bestIndex * 10) + baseDecade;
    }

    // Public method returning num decades in which name rank is top 1000
    public int numDecadesRanked() {
        int count = 0;
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i) > 0 && ranks.get(i) <= 1000) {
                count++;
            }
        }
        return count;
    }

    // Public method returning true if name rank is top 1000 in all decades where rank is recorded
    public boolean allTop1000() {
        return (numDecadesRanked() == ranks.size());
    }

    // Public method returning true if name rank is top 1000 in one singular decade
    public boolean oneTop1000() {
        return (numDecadesRanked() == 1);
    }

    // Public method returning true if name rank improves over the decades (closer to 1)
    public boolean morePopular() {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i) == 0) {
                temp.add(1001);
            }
            else {
                temp.add(ranks.get(i));
            }
        }
        for (int i = 0; i < temp.size() - 1; i++) {
            if (temp.get(i + 1) >= temp.get(i)) {
                return false;
            }
        }
        return true;
    }

    // Public method returning true if name rank gets worse over the decades (farther from 1)
    public boolean lessPopular() {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i) == 0) {
                temp.add(1001);
            }
            else {
                temp.add(ranks.get(i));
            }
        }
        for (int i = 0; i < temp.size() - 1; i++) {
            if (temp.get(i + 1) <= temp.get(i)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String result = name + "\n";
        for (int i = 0; i < ranks.size(); i++) {
            int currentDecade = baseDecade + (i * 10);
            result += currentDecade + ": " + ranks.get(i) + "\n";
        }
        return result;
    }

    public int compareTo(NameRecord other) {
        return name.compareTo(other.getName());
    }
}
