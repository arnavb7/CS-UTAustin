# Name: Arnav Bhasin
# UTEID: ab78845
#
# On my honor, Arnav Bhasin, this programming assignment is my own work
# and I have not provided this code to any other student.
#
# Explain your addded feature here:
# My added feature was a button that gives a hint to the user, telling
# them how many vowels are in the secret word. However, this ability
# is not accessible until the user has made at least 3 guesses.
# Additionally, trying the button when a game has been completed
# prompts the user to start a new game.

import random
from tkinter import *
from tkinter import ttk

class WordleBoard:
    '''
    The WordleBoard class contains basic variables needed to track the 
    game, such as state of game (ongoing or over), number of guesses
    made, secret word for round, lists of all possible secret words 
    and all possible guessable words.

    Methods are getters for major variables, being game state, secret 
    word, and current guess, game reset method to reset major variables
    and select new secret word, other methods are for incrementing 
    major variables like current round and changing state of game from 
    ongoing to over, method for parsing text files containing list of 
    all possible words for guess and secret word, and method for 
    returning the "feedback string" of a guess based on its similarity 
    to the secret word.
    '''
    def __init__(self):
        self.__game_over = False
        self.__current_round = 0
        self.__secret_word = ''
        self.__all_words = []
        self.__all_wordles = []
        self.__get_words()
        self.__pick_secret()

    def get_secret_word(self):
        return self.__secret_word

    def get_game_over(self):
        return self.__game_over
    
    def end_game(self):
        self.__game_over = True

    def reset(self):
        self.__current_round = 0
        self.__game_over = False
        self.__pick_secret()

    def __pick_secret(self):
        self.__secret_word = ''
        self.__secret_word += random.choice(self.__all_wordles)
        
    def get_round(self):
        return self.__current_round

    def increment_round(self):
        self.__current_round += 1

    def check_valid_word(self, guess):
        if guess in self.__all_words:
            return True
        else:
            return False

    def __get_words(self):
        temp_secret_words = []
        with open('secret_words.txt', 'r') as data_file:
            all_lines = data_file.readlines()
            for line in all_lines:
                temp_secret_words.append(line.strip().upper())
        temp_secret_words.sort()
        secret_words = tuple(temp_secret_words)
        all_words = set(secret_words)
        with open('other_valid_words.txt', 'r') as data_file:
            all_lines = data_file.readlines()
            for line in all_lines:
                all_words.add(line.strip().upper())
        self.__all_wordles = secret_words
        self.__all_words = all_words

    def get_feedback(self, guess):
        green_status = ""
        full_status = ""
        secret_word_letters = list(self.__secret_word)
        for letter1, letter2 in zip(self.__secret_word, guess):
            if letter1 == letter2:
                green_status += "G"
                secret_word_letters.remove(letter1)
            else:
                green_status += "-"
        for letter1, letter2 in zip(green_status, guess):
            if letter1 == "G":
                full_status += "G"
            elif letter2 in secret_word_letters:
                full_status += "Y"
                secret_word_letters.remove(letter2)
            else:
                full_status += "-"
        return full_status

# Main method for running the loop of the GUI, creating the board, and
# all labels attached.
def main():
    # Set the seed to make grading easier.
    # Final version turned in must have this line
    # of code. First three words with this seed
    # should be AFFIX, PROXY, APING
    random.seed(3252024)
    root = Tk()
    root.title("Wordle")
    root.configure(bg="gray94")
    root.resizable(False, False)
    board = WordleBoard()
    info_var = StringVar()
    labels = create_labels(root)
    create_control_buttons(root, labels, board, info_var)
    root.bind('<KeyPress>', lambda event: set_letter(
                        event.char, board, labels, info_var))
    root.mainloop()

# Method for giving the number of vowels in the secret word, but it
# only gives the hint if the user has made at least three guesses.
def give_hint(board, info_var):
    if board.get_game_over():
        info_var.set("Game over. Please start a new game.")
        return
    if board.get_round() <= 2:
        info_var.set("You must make at least 3 " +
                    "guesses before you can get the hint.")
        return
    word = board.get_secret_word()
    vowel_set = set("AEIOU")
    num_vowels = 0
    for letter in word:
        if letter in vowel_set:
            num_vowels += 1
    if (num_vowels == 1):
        info_var.set("HINT: There is 1 vowel in the secret word.")
    else:
        info_var.set("HINT: There are " + str(num_vowels)
                    + " vowels in the secret word.")

# Method for giving the number of vowels in the secret word, but it
# only gives the hint if the user has made at least three guesses.
def new_game(board, labels, info_var):
    info_var.set("")
    for row in labels:
        for label in row:
            label.config(text=' ')
            label.config(bg='gray94')
    board.reset()

# Method for setting the appropriate label of the GUI to the letter of 
# the key that was just clicked by the user
def set_letter(let, board, labels, info_var):
    if board.get_game_over():
        info_var.set("Game over. Please start a new game.")
        return
    let = let.upper()
    if let.isalpha():
        current_round = board.get_round()
        for i in range(5):
            if labels[current_round][i]['text'] == ' ':
                break
            elif i == 4 and labels[current_round][i]['text'] != ' ':
                return
        labels[current_round][i].config(text=let)

# Method for undoing the last letter clicked and returning the label 
# to being empty
def undo_letter(board, labels):
    current_round = board.get_round()
    if current_round >= 6:
        return
    for i in range(4, -1, -1):
        if labels[current_round][i]['text'] != ' ':
            break
    labels[current_round][i].config(text=' ')

# Method for entering the guess, retrieving the feedback string
# compared to the secret word, and setting the colors of the labels
# to green, yellow, or gray depending on the feedback, additionally
# advances the game forward a guess and updates game state if out of
# guesses or secret word was guessed
def enter_guess(board, labels, info_var):
    if board.get_game_over():
        info_var.set("Game over. Please start a new game.")
        return
    info_var.set("")
    guess = ""
    current_round = board.get_round()
    guess_length = 0
    for position in range(5):
        guess += labels[current_round][position].cget('text')
    if not guess.isalpha():
        info_var.set("Must have 5 letters to make guess.")
        return
    elif board.check_valid_word(guess) == False:
        info_var.set("I don't know that word.")
        return
    else:
        board.increment_round()
        feedback_string = board.get_feedback(guess)
        for i, feedback_letter in enumerate(feedback_string):
            letter_label = labels[current_round][i]
            if feedback_letter == 'G':
                letter_label.configure(bg="green")
            if feedback_letter == 'Y':
                letter_label.configure(bg="yellow")
            if feedback_letter == '-':
                letter_label.configure(bg="gray")
    if guess.lower() == board.get_secret_word().lower():
        board.end_game()
        win_words = ["Genius!", "Magnificent!", "Impressive!", "Splendid!", 
            "Great!", "Phew!"]
        word = win_words[board.get_round() - 1]
        info_var.set("You got it! " + word)
    elif board.get_round() >= 6:
        board.end_game()
        word = board.get_secret_word().upper()
        info_var.set("Game over. Secret word was " + word)

# Method for creating the wordle labels within the GUI
def create_labels(root):
    label_frame = Frame(root, bg="gray94")
    label_frame.grid(row=1, column=1, columnspan=4)
    labels = []
    for row in range(6):
        label_row = []
        for col in range(5):
            label = Label(label_frame, font='Courier 64 bold', text=' ',
                    borderwidth=2, relief='solid', bg='gray94', fg='black')
            label.grid(row=row, column=col, padx=2, pady=2)
            label_row.append(label)
        labels.append(label_row)
    return labels

# Method for creating the control buttons within the GUI, being the
# new game, enter guess, undo letter, and give hint buttons
def create_control_buttons(root, labels, board, info_var):
    bottom_frame = Frame(root, bg="gray94")
    bottom_frame.grid(row=2, column=1, columnspan=4)
    new_game_button = Button(bottom_frame, font='Arial 20 bold',
            text='New Game', highlightbackground="gray94",
            command=lambda: new_game(board, labels, info_var))
    new_game_button.grid(row=1, column=1)
    enter_guess_button = Button(bottom_frame, font='Arial 20 bold',
            text='Enter Guess', highlightbackground="gray94",
            command=lambda: enter_guess(board, labels, info_var))
    enter_guess_button.grid(row=1, column=2)
    undo_letter_button = Button(bottom_frame, font='Arial 20 bold',
            text='Undo Choice', highlightbackground="gray94",
            command=lambda: undo_letter(board, labels))
    undo_letter_button.grid(row=1, column=3)
    give_hint_button = Button(bottom_frame, font='Arial 20 bold',
            text='Give Hint', highlightbackground="gray94",
            command=lambda: give_hint(board, info_var))
    give_hint_button.grid(row=1, column=4)
    info_label = Label(bottom_frame, font='Arial 16 bold',
                           textvariable=info_var, bg="gray94", fg='black')
    info_label.grid(row=2, column=1, columnspan=4)

# Method to run the appropriate main method
if __name__ == '__main__':
    main()