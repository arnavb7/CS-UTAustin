# Name: Arnav Bhasin
# UTEID: ab78845
#
# On my honor, Arnav Bhasin, this programming assignment is my own work
# and I have not provided this code to any other student.


import copy
import random

class WordleSolver:

    def __init__(self):
        self.__all_words = []
        self.get_words()

    def get_guess(self, feedback):
        """
        Make a guess for the current round of Wordle.
        :param feedback: A list of strings representing the guesses so far
        and the feedback for those guesses in the current game of Wordle.
        If feedback is empty then this is the first guess.
        The order of the elements of feedback is [feedback_1, guess_1,
        feedback_2, guess_2, ...]
        All strings are length 7.
        The feedback strings consist of G, O, and -.
        G for GREEN, correct letter in correct spot.
        O for letter in word but not in right spot.
        - for letter not in word.
        :return: A string that is in __all_words and is the next guess.
        """
        if (len(feedback) == 0):
            return random.choice(self.__all_words)
        else:
            eligible_words = self.filter_words(feedback)
            return random.choice(list(eligible_words))

    def filter_words(self, feedback):
        # eligible_words = set(self.__all_words)
        eligible_words = set()
        for word in self.__all_words:
            eligible_words.add(word)
        for i in range(0, len(feedback), 2):
            status_string = feedback[i]
            guess = feedback[i + 1]
            # create deep copy of list of remaining eligible words so that we can update while we iterate
            # and not skip over any words
            words_to_remove = set()
            # iterate through eligible words
            for word in eligible_words:
                # iterate through status string and actual guess
                for index, (letter1, letter2) in enumerate(zip(status_string, guess)):
                    if letter1 == 'G' and letter2 != word[index]:
                        words_to_remove.add(word)
                        break
                    elif letter1 == '-' and letter2 == word[index]:
                        words_to_remove.add(word)
                        break
                    elif letter1 == 'O':
                        eligible_word_letters = list(word)
                        if letter2 not in eligible_word_letters:
                            words_to_remove.add(word)
                            break
            # after updating set of words, set eligible word set equal to new updated set for next guess iteration
            for word in words_to_remove:
                eligible_words.remove(word)
        return eligible_words

    def get_words(self):
        """ Read the words from the dictionary file and place them
        in the __all_words instance variable.
        We assume the  required files are in the current working directory
        and is named all_words_5.txt. We also assume all words are
        seven letters long, one word per line.
        Returns a set of strings with all the words from the file.
        """
        with open('all_words_7.txt', 'r') as data_file:
            all_lines = data_file.readlines()
            for line in all_lines:
                self.__all_words.append(line.strip())

    def show_words(self):
        """
        Debugging method to check file was read in correctly.
        :return: None
        """
        print(len(self.__all_words))
        for word in self.__all_words:
            print(word)


'''
CS109 Students, include your write up (15/50 points) here at the end of your class.
Essentially, how my algorithm works is it constantly checks the set of
possible wordles against the feedback strings and guesses made. I
choose a random word from the set of remaining eligible words and
make that guess, but how I reduce this set is how I am able to solve
the wordles. I iterate through the set, and at each word I iterate
through the feedback and guess strings, checking if the char in the
feedback string is green, orange, or -. Depending on which char it is,
I check if the letter in the guess is equal to or not equal to the
letter in the current "eligible" word at the same index. I disqualify
any words that don't fit the results of the feedback string. I do this
disqualifying process by adding all disqualified words to a different
set, then essentially doing a set difference operation at the end as
to not repeat iterations of words that are disqualified. Through this
process, I wither down the number of words in eligible words until it
becomes clearer which word the wordle is, finally making that guess at
the end.
'''
