/*
 *  Student information for assignment:
 *
 *  On my honor, Arnav Bhasin, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  TA name: Brad
 *  Number of slip days I am using: 0
 */

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;

public class AnagramSolver {

    // int Map instance field containing all words in dictionary mapped to their LetterInventories
    private Map<String, LetterInventory> inventories;

    /*
     * pre: list != null
     * @param list Contains the words to form anagrams from.
     */
    // takes in String Set for dictionary of words that anagrams can be created out of and
    // constructs an AnagramSolver object containing Map of every word to its LetterInventory
    // preconditions: String Set parameter cannot be null
    public AnagramSolver(Set<String> dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Set parameter cannot be null!");
        }
        inventories = new HashMap<String, LetterInventory>();
        for (String word : dictionary) {
            inventories.put(word, new LetterInventory(word));
        }
    }

    /*
     * pre: maxWords >= 0, s != null, s contains at least one
     * English letter.
     * Return a list of anagrams that can be formed from s with
     * no more than maxWords, unless maxWords is 0 in which case
     * there is no limit on the number of words in the anagram
     */
    // takes in String s for word we will find anagrams for and max number of anagrams we can find
    // per word and returns ArrayList of List of Strings for all anagrams generated
    // preconditions: int parameter cannot be less than 0 and String parameter cannot be null
    // and must contain at least one English character
    public List<List<String>> getAnagrams(String s, int maxWords) {
        if (maxWords < 0 || s == null || !containsEnglish(s)) {
            throw new IllegalArgumentException("One or more parameters fail preconditions!");
        }
        ArrayList<List<String>> anagrams = new ArrayList<List<String>>();
        LetterInventory word = new LetterInventory(s);
        ArrayList<String> currentWords = new ArrayList<String>();
        for (Map.Entry<String, LetterInventory> entry : inventories.entrySet()) {
            if (word.subtract(entry.getValue()) != null) {
                currentWords.add(entry.getKey());
            }
        }
        maxWords = (maxWords == 0) ? inventories.size() : maxWords;
        recursiveAnagrams(anagrams, currentWords, word, new ArrayList<String>(), maxWords, 0);
        for (List<String> anagram : anagrams) {
            Collections.sort(anagram);
        }
        Collections.sort(anagrams, new AnagramComparator());
        return anagrams;
    }

    // recursive helper method to aid process of recursion in getAnagrams method, takes in
    // ArrayList of List of Strings for anagrams, ArrayList of Strings for preprocessed
    // words, LetterInventory for letters left so far, ArrayList of Strings for current anagram,
    // int for maxWords per anagram, and int for current index
    private void recursiveAnagrams(ArrayList<List<String>> anagrams,
                                ArrayList<String> currentWords,
                                LetterInventory lettersSoFar,
                                ArrayList<String> anagramSoFar, int maxWords, int index) {
        if (lettersSoFar.isEmpty() && anagramSoFar.size() <= maxWords) {
            anagrams.add(new ArrayList(anagramSoFar));
        }
        else if (anagramSoFar.size() < maxWords) {
            for (int i = index; i < currentWords.size(); i++) {
                LetterInventory newLettersSoFar =
                        lettersSoFar.subtract(inventories.get(currentWords.get(i)));
                if (newLettersSoFar != null) {
                    anagramSoFar.add(currentWords.get(i));
                    recursiveAnagrams(anagrams, currentWords, newLettersSoFar, anagramSoFar,
                            maxWords, i);
                    anagramSoFar.remove(anagramSoFar.size() - 1);
                }
            }
        }
    }

    // helper method taking in String parameter for word and returning whether or not parameter
    // contains any English letters
    private static boolean containsEnglish(String word) {
        char[] letters = word.toLowerCase().toCharArray();
        for (char letter : letters) {
            if ('a' <= letter && letter <= 'z') {
                return true;
            }
        }
        return false;
    }

    // nested custom comparator class to sort anagrams ArrayList of List of Strings by size and
    // lexicographical order within each anagram
    private static class AnagramComparator implements Comparator<List<String>> {
        @Override
        public int compare(List<String> a1, List<String> a2) {
            if (a1.size() != a2.size()) {
                return a1.size() - a2.size();
            }
            else {
                for (int i = 0; i < a1.size(); i++) {
                    if (a1.get(i).compareTo(a2.get(i)) != 0) {
                        return a1.get(i).compareTo(a2.get(i));
                    }
                }
            }
            return 0;
        }
    }
}