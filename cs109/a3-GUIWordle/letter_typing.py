from tkinter import *
from tkinter import ttk


def set_letter(letter_var, let, label):
    let = let.upper()
    if let.isalpha():
        letter_var.set(let)
        if let in 'AEIOU':
            label.config(foreground='red')
        else:
            label.config(foreground='black')


def main():
    # reference: https://stackoverflow.com/questions/32582751/tkinter-center-label-in-frame-of-fixed-size
    # Set up the main application window.
    root = Tk()
    root.title("Letter Typing")
    dim = 200
    mainframe = ttk.Frame(root, width=dim, height=dim, padding="3 3 12 12")
    mainframe.grid(row=0, column=0)
    mainframe.update()
    letter_var = StringVar()
    letter_var.set('A')
    letter_label = ttk.Label(mainframe, foreground='red',
                             text='A', textvariable=letter_var,
                             font=('Times', 108))
    letter_label.place(x=dim / 2, y=dim / 2, anchor='center')
    # letter_label_2 = ttk.Label(mainframe, x=dim / 2, y = dim / 2,
    #                            text='*',
    #                            font=('Times', 10))
    # letter_label_2.place(x=0, y=0, anchor='center')
    scale = ttk.Scale(length=200)
    b = ttk.Button(length=200)
    root.bind('<KeyPress>', lambda event: set_letter(letter_var,
                                                     event.char, letter_label))
    root.mainloop()


if __name__ == '__main__':
    main()
