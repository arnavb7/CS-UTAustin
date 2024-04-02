import random
from tkinter import *
from tkinter import ttk


class MasterMindBoard:

    def __init__(self):
        self.__board = []
        self.__feedback = []
        self.__colors = 'BGOPRY'
        self.__secret_code = ''
        self.__pick_secret()

    def make_guess(self, guess):
        """
        User is making a guess.
        :param guess: The guess, only BGOPRY. (Blue, Green, Orange,
        Purple, Red, Yellow). len(guess) == 4
        :return: The feedback from the guess
        """
        self.__board.append(guess)
        self.__feedback.append(self.__get_feedback(guess))
        return self.__feedback[-1]

    def __get_feedback(self, guess):
        guess_list = list(guess)
        secret_code_list = list(self.__secret_code)
        feedback = ''
        for i in range(len(guess)):
            if guess_list[i] == self.__secret_code[i]:
                feedback += 'B'
                guess_list[i] = '*'
                secret_code_list[i] = 'X'
        for i in range(len(guess)):
            if guess_list[i] in secret_code_list:
                feedback += 'W'
                index = secret_code_list.index(guess_list[i])
                secret_code_list[index] = 'X'
        return feedback

    def __pick_secret(self):
        self.__secret_code = ''
        for i in range(4):
            self.__secret_code += random.choice(self.__colors)

    def game_over(self):
        return (len(self.__board) == 10
                or (len(self.__board) > 0
                    and self.__secret_code == self.__board[-1]))

    def won(self):
        return len(self.__board) > 0 and self.__secret_code == self.__board[-1]

    def get_secret_code(self):
        return self.__secret_code

    def current_round(self):
        return len(self.__board)

    def __str__(self):
        result = ''
        for i in range(len(self.__board)):
            for j in range(4):
                result += self.__board[i][j] + ' '
            result += ':' + self.__feedback[i] + '\n'
        return result


def main():
    # Create a UI to allow user to play one game of Mastermind.
    root = Tk()
    root.title("Mastermind")
    root.resizable(False, False)
    board = MasterMindBoard()
    feedback_vars, labels = create_labels(root)
    # A list of Strings of length 1 that stores the current user guess.
    guess = []
    create_color_buttons(root, labels, guess, board)
    create_control_buttons(root, labels, feedback_vars, guess, board)
    root.mainloop()


def create_color_buttons(root, labels, guess, board):
    """
    Create the buttons the user will press to pick a color to
    place in the current guess.
    :param root: The root window.
    :param labels: The labels. These color of these will change in response to
    the user pressing a button.
    :param guess: A list of strings of length 1 that stores the current user
    guess
    :param board: The mastermind board. Used to determine which label to update
    when a button is pressed.
    """
    button_frame = ttk.Frame(root, padding="3 3 3 3")
    button_frame.grid(row=1, column=2, stick='N')
    colors = ['Blue', 'Green', 'Orange', 'Purple', 'Red', 'Yellow']
    for i in range(len(colors)):
        button = Button(button_frame, text=colors[i], font='Arial 24 bold',
                        bg=colors[i],
                        command=lambda color=colors[i]:
                        update_guess(guess, color, labels, board))
        button.grid(row=i + 1, sticky='WE', pady=3)
        letter = colors[i][0]
        root.bind(letter, lambda event, color=colors[i]:
                update_guess(guess, color, labels, board, event))
        root.bind(letter.lower(), lambda event, color=colors[i]:
                update_guess(guess, color, labels, board, event))


def update_guess(guess, color, labels, board, _event=NONE):
    """
    Update proper label's background color to show the user's guess.
    The mastermind board gives us the row and the guess gives us the
    column of the label to update.
    :param guess: A list of strings of length 1 that stores the current
    user guess.
    :param color: Update the proper label's background label to this color.
    :param labels: A list of lists of the labels.
    :param board: The mastermind board.
    :param _event: The event that triggered this function. Not used but needed
    if event invoked due to a keypress.
    """
    if len(guess) < 4 and not board.game_over():
        guess.append(color[0])
        labels[board.current_round()][len(guess) - 1].configure(bg=color)
    # Otherwise, this guess is ready to be entered.
    # Possibly inform the user of that if they try and enter another guess?


def create_labels(root):
    """
    Create the frame for the color labels and feedback.
    The color labels are used to show what colors the user
    has guessed for the current round of mastermind.
    The feedback variables shall be used to show the result of
    black or white when the user enters a guess.
    :param root: The root window.
    :return: Two lists. The list of feedback StringVars to place
    feedback from guesses and a list of lists of labels.
    """
    label_frame = ttk.Frame(root, padding="3 3 3 3")
    label_frame.grid(row=1, column=1)
    feedback_vars = []
    labels = []
    for row in range(1, 11):
        label_row = []
        for col in range(1, 5):
            label = Label(label_frame, font='Courier 32 bold', text=' ',
                          borderwidth=1, relief='solid')
            label.grid(row=row, column=col, padx=2, pady=2)
            label_row.append(label)
        labels.append(label_row)
        feedback_var = StringVar()
        feedback_var.set('    ')
        feedback_vars.append(feedback_var)
        feedback_label = Label(label_frame, font='Courier 20 bold',
                               textvariable=feedback_var)
        feedback_label.grid(row=row, column=5, sticky='W')
    return feedback_vars, labels


def create_control_buttons(root, labels, feedback, guess, board):
    """
    Create the main control buttons to undo a guess and
    enter a guess. Also, a label for information to show user.
    :param root: The root window.
    :param labels: The labels. These color of these will change in response to
    the user pressing a button.
    :param feedback: The StringVars that show the feedback from a guess.
    :param guess: A list of strings of length 1 that stores the current user
    guess
    :param board: The mastermind board.
    """
    bottom_frame = ttk.Frame(root)
    bottom_frame.grid(row=2, column=1, columnspan=2)
    # To give the user information on errors and inform them when they win.
    info_var = StringVar()
    undo_button = Button(bottom_frame, font='Arial 24 bold',
                         text='Undo Choice',
                         command=lambda: undo_last_pick(labels, board, guess))
    undo_button.grid(row=1, column=1, padx=5, pady=5)
    enter_guess_button = Button(bottom_frame, font='Arial 24 bold',
            text='Enter Guess',
            command=lambda: enter_guess(feedback, board, guess, info_var))
    enter_guess_button.grid(row=1, column=2, padx=5, pady=5)
    info_label = ttk.Label(bottom_frame, font='Arial 16 bold',
                           textvariable=info_var)
    info_label.grid(row=2, column=1, columnspan=2)


def enter_guess(feedback_vars, board, guess, info_var):
    """
    If a row is filled enter the guess into the mastermind board and display
    the feedback.
    :param feedback_vars: The feedback String vars to update with the result of
    a guess.
    :param board: The mastermind board.
    :param guess: A list of strings of length 1 that stores the current user
    guess
    :param info_var: The Label to update with information to the user with
    error messages and game status.
    """
    if len(guess) == 4:
        guess_string = ''.join(guess)
        guess.clear()
        feedback_index = board.current_round()
        feedback = board.make_guess(guess_string)
        if len(feedback) == 0:
            feedback = 'NONE'
        feedback_vars[feedback_index].set(feedback)
        if board.won():  # They won!
            info_var.set("You won! Well Done!")
        elif board.game_over():  # Game is over.
            info = 'Game over. Secret code was ' + board.get_secret_code()
            info_var.set(info)
    else:
        info_var.set('Must have 4 colors to make guess.')


def undo_last_pick(labels, board, guess):
    # Undo the last pick by the user if there is one or more guesses for
    # the current row.
    if len(guess) >= 1:
        row = board.current_round()
        column = len(guess) - 1
        labels[row][column].configure(bg='SystemButtonFace')
        guess.pop()


def text_main():
    board = MasterMindBoard()
    while not board.game_over():
        guess = input('Enter guess: ').upper()
        board.make_guess(guess)
        print(board)

    if board.won():
        print('You won!')
    else:
        print('Not quite. The secret code was', board.get_secret_code())


if __name__ == '__main__':
    main()
