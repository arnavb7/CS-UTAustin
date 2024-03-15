/*  Student information for assignment:
 *
 *  On my honor, Arnav Bhasin, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  Number of slip days I am using: 1
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A collection of NameRecords.
 * Stores NameRecord objects and provides methods to select
 * NameRecords based on various criteria.
 */
public class Names {

    // Private instance NameRecord arraylist variable for Names storing list of all NameRecords
    // from scanned file
    private ArrayList<NameRecord> names;

    /**
     * Construct a new Names object based on the data source the Scanner
     * sc is connected to. Assume the first two lines in the
     * data source are the base year and number of decades to use.
     * Any lines without the correct number of decades are discarded
     * and are not part of the resulting Names object.
     * Any names with ranks of all 0 are discarded and not
     * part of the resulting Names object.
     * @param sc Is connected to a data file with baby names
     * and positioned at the start of the data source.
     */
    // Public method that constructs a Names object, taking in scanner object to scan text file
    // Preconditions: parameter scanner is connected to data file and is positioned at start
    public Names(Scanner sc) {
        names = new ArrayList<NameRecord>();
        int baseDecade = sc.nextInt();
        int numDecades = sc.nextInt();
        while (sc.hasNextLine()) {
            String[] line = (sc.nextLine()).split(" ");
            String name = line[0];
            ArrayList<Integer> ranks = new ArrayList<Integer>();
            boolean allZero = true;
            for (int i = 1; i < line.length; i++) {
                int val = Integer.parseInt(line[i]);
                if (val > 0) {
                    allZero = false;
                }
                ranks.add(val);
            }
            if (!allZero && ranks.size() == numDecades) {
                NameRecord newName = new NameRecord(name, baseDecade, ranks);
                names.add(newName);
            }
        }
        Collections.sort(names);
    }

    /**
     * Returns an ArrayList of NameRecord objects that contain a
     * given substring, ignoring case.  The names must be in sorted order based
     * on the names of the NameRecords.
     * @param partialName != null, partialName.length() > 0
     * @return an ArrayList of NameRecords whose names contains
     * partialName. If there are no NameRecords that meet this
     * criteria returns an empty list.
     */
    // Public method that takes in string parameter and searches Names object, returning list of
    // all NameRecords in Names object that contain string parameter
    // Preconditions: string parameter is not null and has a length greater than zero
    public ArrayList<NameRecord> getMatches(String partialName) {
        if (partialName == null || partialName.length() <= 0) {
            throw new IllegalArgumentException("Parameter fails preconditions!");
        }
        ArrayList<NameRecord> resultant = new ArrayList<NameRecord>();
        for (int i = 0; i < names.size(); i++) {
            NameRecord temp = names.get(i);
            String tempName = (temp.getName()).toLowerCase();
            if (tempName.contains(partialName.toLowerCase())) {
                resultant.add(temp);
            }
        }
        return resultant;
    }

    /**
     * Returns an ArrayList of Strings of names that have been ranked in the
     * top 1000 or better for every decade. The Strings  must be in sorted
     * order based on the name of the NameRecords.
     * @return A list of the names that have been ranked in the top
     * 1000 or better in every decade. The list is in sorted ascending
     * order. If there are no NameRecords that meet this
     * criteria returns an empty list.
     */
    // Public method that searches Names object, returning list of all names that were ranked in
    // the top 1000 every decade
    public ArrayList<String> rankedEveryDecade() {
        ArrayList<String> resultant = new ArrayList<String>();
        for (int i = 0; i < names.size(); i++) {
            NameRecord temp = names.get(i);
            if (temp.allTop1000()) {
              resultant.add(temp.getName());
            }
        }
        return resultant;
    }

    /**
     * Returns an ArrayList of Strings of names that have been ranked in the
     * top 1000 or better in exactly one decade. The Strings must be in sorted
     * order based on the name of the NameRecords.
     * @return A list of the names that have been ranked in the top
     * 1000 or better in exactly one decade. The list is in sorted ascending
     * order. If there are no NameRecords that meet this
     * criteria returns an empty list.
     */
    // Public method that searches Names object, returning list of all names that were ranked in
    // the top 1000 in only one decade
    public ArrayList<String> rankedOnlyOneDecade() {
        ArrayList<String> resultant = new ArrayList<String>();
        for (int i = 0; i < names.size(); i++) {
            NameRecord temp = names.get(i);
            if (temp.oneTop1000()) {
                resultant.add(temp.getName());
            }
        }
        return resultant;
    }

    /**
     * Returns an ArrayList of Strings of names that have been getting more
     * popular every decade. The Strings  must be in sorted
     * order based on the name of the NameRecords.
     * @return A list of the names that have been getting more popular in
     * every decade. The list is in sorted ascending
     * order. If there are no NameRecords that meet this
     * criteria returns an empty list.
     */
    // Public method that searches Names object, returning list of all names that increase in
    // popularity over the decades
    public ArrayList<String> alwaysMorePopular() {
        ArrayList<String> resultant = new ArrayList<String>();
        for (int i = 0; i < names.size(); i++) {
            NameRecord temp = names.get(i);
            if (temp.morePopular()) {
                resultant.add(temp.getName());
            }
        }
        return resultant;
    }

    /**
     * Returns an ArrayList of Strings of names that have been getting less
     * popular every decade. The Strings  must be in sorted order based
     * on the name of the NameRecords.
     * @return A list of the names that have been getting less popular in
     * every decade. The list is in sorted ascending
     * order. If there are no NameRecords that meet this
     * criteria returns an empty list.
     */
    // Public method that searches Names object, returning list of all names that increase in
    // popularity over the decades
    public ArrayList<String> alwaysLessPopular() {
        ArrayList<String> resultant = new ArrayList<String>();
        for (int i = 0; i < names.size(); i++) {
            NameRecord temp = names.get(i);
            if (temp.lessPopular()) {
                resultant.add(temp.getName());
            }
        }
        return resultant;
    }

    /**
     * Return the NameRecord in this Names object that matches the given String ignoring case.
     * <br>
     * <tt>pre: name != null</tt>
     * @param name The name to search for.
     * @return The name record with the given name or null if no NameRecord in this Names
     * object contains the given name.
     */
    // Public method that takes in string parameter and searches Names object, returning
    // NameRecord object containing that specific string name
    // Preconditions: string parameter is not null
    public NameRecord getName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The parameter name cannot be null");
        }
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                return names.get(i);
            }
        }
        return null;
    }

    // Public method that searches Names object, recording the number of names with double letters,
    // then checking how many of these names are top 1000 in all decades they are ranked, and
    // finally returning approximate percent of all double letter names that are always top 1000
    public int doubleLetterNameFrequency() {
        ArrayList<NameRecord> nameList = new ArrayList<NameRecord>();
        for (int i = 0; i < names.size(); i++) {
            String tempName = names.get(i).getName().toLowerCase();
            for (int j = 0; j < tempName.length() - 1; j++) {
                char currentChar = tempName.charAt(j);
                char nextChar = tempName.charAt(j + 1);
                if (currentChar == nextChar) {
                    nameList.add(names.get(i));
                    break;
                }
            }
        }
        double numNamesPopular = 0.0;
        for (int i = 0; i < nameList.size(); i++) {
            if (nameList.get(i).allTop1000()) {
                numNamesPopular++;
            }
        }
        double percent = 100 * (numNamesPopular / nameList.size());
        return (int) percent;
    }
}