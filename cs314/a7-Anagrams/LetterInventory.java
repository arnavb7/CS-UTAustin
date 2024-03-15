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

public class LetterInventory {

    // final static int instance field representing constant alphabet length of magic number 26
    private static final int ALPHABET_LENGTH = 26;

    // int array instance field containing frequencies of all alphabet letters in inventory
    private int[] frequencyInventory;

    // int instance field containing total number of letters in inventory
    private int size;

    // takes in String for word being stored in inventory and constructs LetterInventory
    // preconditions: String parameter cannot be null
    public LetterInventory(String word) {
        if (word == null) {
            throw new IllegalArgumentException("String parameter cannot be null!");
        }
        frequencyInventory = new int[ALPHABET_LENGTH];
        char[] letters = word.toLowerCase().toCharArray();
        for (char letter : letters) {
            if ('a' <= letter && letter <= 'z') {
                frequencyInventory[letter - 'a']++;
                size++;
            }
        }
    }

    // takes in character for letter whose frequency is to be returned
    // preconditions: char parameter is somewhere from a to z, inclusive
    public int get(char letter) {
        letter = Character.toLowerCase(letter);
        if (!('a' <= letter && letter <= 'z')) {
            throw new IllegalArgumentException("Char parameter is not an English letter!");
        }
        return frequencyInventory[letter - 'a'];
    }

    // returns total number of letters stored in given inventory
    public int size() {
        return size;
    }

    // returns whether or not total number of letters stored is zero
    public boolean isEmpty() {
        return (size == 0);
    }

    // returns alphabetical String representation of all letters stored in inventory
    public String toString() {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            for (int j = 0; j < frequencyInventory[i]; j++) {
                build.append((char) ('a' + i));
            }
        }
        return build.toString();
    }

    // takes in LetterInventory object being added to called object and returns new
    // LetterInventory that has addition of parameter and called object inventories
    // preconditions: LetterInventory parameter cannot be null
    public LetterInventory add(LetterInventory object) {
        if (object == null) {
            throw new IllegalArgumentException("LetterInventory parameter cannot be null!");
        }
        LetterInventory resultant = new LetterInventory("");
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            resultant.frequencyInventory[i] = frequencyInventory[i] + object.frequencyInventory[i];
        }
        resultant.size = size + object.size;
        return resultant;
    }

    // takes in LetterInventory object being subtracted from called object and returns new
    // LetterInventory that has subtraction of parameter inventory from called object inventory,
    // returns null if parameter inventory cannot be subtracted from called inventory
    // preconditions: LetterInventory parameter cannot be null
    public LetterInventory subtract(LetterInventory object) {
        if (object == null) {
            throw new IllegalArgumentException("LetterInventory parameter cannot be null!");
        }
        LetterInventory resultant = new LetterInventory("");
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            resultant.frequencyInventory[i] = frequencyInventory[i] - object.frequencyInventory[i];
            if (resultant.frequencyInventory[i] < 0) {
                return null;
            }
        }
        resultant.size = size - object.size;
        return resultant;
    }

    // takes in object for LetterInventory and checks if equivalent to called LetterInventory
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        LetterInventory object = (LetterInventory) obj;
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            if (frequencyInventory[i] != object.frequencyInventory[i]) {
                return false;
            }
        }
        return true;
    }
}
