"""
Test a WordleSolver.
"""

import multiprocessing
import random
import time
from wordle_solver import WordleSolver


def main():
    """
    Test a WordleSolver.
    Read in the dictionary and create the list of secret words.
    Test the solver for every word in the secret word list and
    print out the result.
    :return: None
    """
    solver = WordleSolver()
    all_words = get_words()
    secret_words = make_secret_list(all_words)
    all_words_set = set(all_words)
    stats = multiprocessing.Array('f', range(4))
    for i in range(4):
        stats[i] = 0
    start_time = time.time()
    # Lots of help from:
    # https://stackoverflow.com/questions/492519/timeout-on-a-function-call
    # https://stackoverflow.com/questions/10415028/how-to-get-the-return-value-of-a-function-passed-to-multiprocessing-process/10415215#10415215
    p = multiprocessing.Process(target=run_rounds,
            args=(stats, secret_words, all_words_set, solver))
    p.start()

    # Wait for 300 seconds or until process finishes
    p.join(300)

    # If thread is still active
    if p.is_alive():
        print("Thread still running. Stopping it.")
        print(stats)
        for i in range(4):
            print(stats[i])
        stats[3] = time.time() - start_time
        # Terminate - may not work if process is stuck for good
        p.terminate()
        # OR Kill - will work for sure, no chance for process to finish nicely however
        # p.kill()
        p.join()

    # run_rounds(stats, secret_words, all_words_set, solver)
    print(f'solved,{stats[0]:.0f},'
          f'unsolved,{stats[1]:.0f},'
          f'total guesses,{stats[2]:.0f},'
          f'average guesses,{(stats[2] / (stats[0] + stats[1])):.2f},'
          f'total time,{stats[3]:.2f},'
          f'rounds per second,{(stats[0] + stats[1]) / stats[3]:.2f}')


def run_rounds(stats, secret_words, all_words_set, solver):
    """Run the rounds. Stats is a list to store the statistics:
    [num_solved, num_unsolved, total_guesses, total_time]
    """
    start_time = time.time()
    for word in secret_words:
        solved, guesses = play_round(all_words_set, word, solver)
        stats[2] += guesses
        if solved:
            stats[0] += 1
        else:
            stats[1] += 1
    stats[3] = time.time() - start_time


def make_secret_list(all_words):
    """
    Make a list of secret words.
    One quarter of the words in all words are selected, randomly,
    to be possible secret words.
    :param all_words: A set of strings of all possible words.
    :return: a list of possible secret words, shuffled.
    """
    random.seed(2142023)
    num_secret = 1000
    return random.sample(all_words, num_secret)


def play_round(all_words, secret_word, solver):
    """
    Play one round of Wordle with the given solver.
    :param all_words: All legal words.
    :param secret_word: The secret word.
    :param solver: A WordleSolver to ask for guesses.
    :return: if the WordleSolver solved the problem and how many guesses it took.
    """
    guesses = 0
    feedback = []
    solver_guess= ''
    MAX_GUESSES = 7
    # print('\nSECRET WORD:', secret_word)
    while guesses < MAX_GUESSES and solver_guess != secret_word:
        solver_guess = solver.get_guess(feedback)
        # If the solver picks a word not in all_words we count
        # that as a pass.
        if solver_guess not in all_words:
            solver_guess = '*******'
        feedback.append(get_feedback(solver_guess, secret_word))
        feedback.append(solver_guess)
        guesses += 1
    return solver_guess == secret_word, guesses


def get_feedback(user_guess, secret_word):
    """
    Return the feedback for the user guess based on the rules of Wordle.
    G for GREEN, correct letter in correct spot.
    O for letter in word but not in right spot. Careful not to double
    count letters.
    - for letter not in word.
    """
    temp_result = ['-' for x in range(7)]
    temp_secret = list(secret_word)
    # First pass, get the letters in the right spot
    for i in range(len(user_guess)):
        if user_guess[i] == temp_secret[i]:
            temp_result[i] = 'G'
            temp_secret[i] = '*'  # so we don't match it again
    # Second pass, get the letters in word, but not right spot.
    for i in range(len(user_guess)):
        if temp_result[i] != 'G' and user_guess[i] in temp_secret:
            index = temp_secret.index(user_guess[i])
            temp_result[i] = 'O'
            temp_secret[index] = '*'  # so we don't match it again
    return ''.join(temp_result)


def get_words():
    """ Read the words from the dictionary file.
    We assume the  required files are in the current working directory
    and is named all_words_7.txt. We also assume all words are
    seven letters long, one word per line.
    Return all the words as a list.
    """
    all_words = []
    with open('all_words_7.txt', 'r') as data_file:
        all_lines = data_file.readlines()
        for line in all_lines:
            all_words.append(line.strip())
    return all_words


if __name__ == '__main__':
    main()
