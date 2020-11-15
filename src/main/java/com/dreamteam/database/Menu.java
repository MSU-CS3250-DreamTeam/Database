package com.dreamteam.database;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.lang.Thread;

public class Menu implements ActionListener {

    private volatile boolean lock_menu;
    private volatile boolean lock_input_form;
    private Options user_selection;
    private final JScrollPane DIALOG_PANE;
    private final JFrame MENU_FRAME;
    private final JTextArea DIALOG_TEXT;
    private final Border MENU_BORDER;

    public Menu(EnumSet<Options> menu_selection) {
        Dimension dialog_size = new Dimension(400, 300);
        JPanel menu_panel = new JPanel();
        JLabel menu_label = new JLabel("Select a choice?");

        this.lock_menu = true;
        this.lock_input_form = true;

        this.DIALOG_TEXT = new JTextArea();
        this.DIALOG_TEXT.setText("\nInitializing menu...");

        this.DIALOG_PANE = new JScrollPane();
        this.DIALOG_PANE.setViewportView(this.DIALOG_TEXT);
        this.DIALOG_PANE.createVerticalScrollBar();
        this.DIALOG_PANE.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.MENU_FRAME = new JFrame();
        this.MENU_BORDER = BorderFactory.createEmptyBorder(50, 500, 50, 500);

        menu_panel.add(menu_label);
        menu_panel.setBorder(MENU_BORDER);
        menu_panel.setLayout(new GridLayout(0, 1));

        for (Options option : menu_selection) {
            JButton option_button = new JButton(String.valueOf(option));
            option_button.addActionListener(this);
            menu_panel.add(option_button);
        }

        this.DIALOG_PANE.setPreferredSize(dialog_size);
        this.MENU_FRAME.add(menu_panel, BorderLayout.CENTER);
        this.MENU_FRAME.add(this.DIALOG_PANE, BorderLayout.SOUTH);
        this.MENU_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.MENU_FRAME.setTitle("GUI Menu Program");
        this.MENU_FRAME.pack();
        this.MENU_FRAME.setVisible(true);
    }

    //	***************************************************************************

    /**
     * Prompt the user for a correct option of the existing menu.
     *
     * @return the selected option to menu.
     */
    public Options getOption() {

        while (lock_menu) Thread.onSpinWait();
        lock_menu = true;
        return this.user_selection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user_input = e.getActionCommand();

        for (Options user_choice : Options.values()) {
            if (user_input.equals(String.valueOf(user_choice))) {
                this.user_selection = user_choice;
                lock_menu = false;
            }
        }
    }

    /**
     *  Clears the text dialog box with an empty string.
     */
    public void clearDialog() { this.DIALOG_TEXT.setText(""); }

    /**
     *  Closes the current menu to release visual components from memory, so runtime execution doesn't stall.
     */
    public void closeMenu() {
        clearDialog();
        printMessage("Closing menu...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.MENU_FRAME.dispose();
    }

    /**
     *  Appends the passed message on a new line at the bottom of the text area in the dialog box.
     * @param message   The text to print to the dialog box.
     */
    public void printMessage(String message) {
        String dialog_output = this.DIALOG_TEXT.getText() + "\n" + message;
        JScrollBar dialog_scrollbar = this.DIALOG_PANE.getVerticalScrollBar();

        this.DIALOG_TEXT.setText(dialog_output);
        dialog_scrollbar.setValue(dialog_scrollbar.getMaximum());
    }

    /**
     *  A form with a dynamic set of input fields for the user to fill out.
     *  Null inputs are not handled here.
     * @param title     The title of the form.
     * @param fields    The prompts of the input fields for the user to answer ie Date: 2020-01-24.
     * @return          A list of the user's answers, as initialized or uninitialized strings.
     *                  If the user does not answer a field, then uninitialized strings,
     *                  not initialized strings, are in the list.
     */
    public ArrayList<String> runTextReader(Options title, ArrayList<String> fields) {
        JFrame text_frame = new JFrame();
        JPanel form = new JPanel();
        JPanel text_panel = new JPanel();
        JPanel entry_panel = new JPanel();
        JPanel submission_panel = new JPanel();
        ArrayList<JTextArea> form_inputs = new ArrayList<>();

        form.setBorder(MENU_BORDER);
        form.setLayout(new GridLayout(2, 2));
        form.add(text_panel);
        form.add(entry_panel);

        text_panel.setLayout(new GridLayout(0, 1));
        entry_panel.setLayout(new GridLayout(0, 1));

        for (String field : fields) {
            text_panel.add(new JLabel(field));
            form_inputs.add(new JTextArea());
        }

        for (JTextArea text_area : form_inputs) {
            text_area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            entry_panel.add(text_area);
        }

        JButton submit = new JButton("SUBMIT");
        submit.addActionListener(this::submitPerformed);
        submission_panel.add(submit);
        form.add(submission_panel);
        text_frame.add(form, BorderLayout.CENTER);

        text_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        text_frame.setTitle("OPERATION: " + title);
        text_frame.pack();
        text_frame.setVisible(true);

        while (lock_input_form) Thread.onSpinWait();
        lock_input_form = true;

        ArrayList<String> responses = new ArrayList<>();
        for (String field : fields) {
            responses.add(form_inputs.get(fields.indexOf(field)).getText());
        }

        text_frame.dispose();
        return responses;
    }

    /**
     * Opens the file using the operating system's default application for the file format.
     * @param file_path An initialized File with a valid file path.
     */
    public void showFile(File file_path) {

        if (file_path.exists()) {

            try {
                Desktop.getDesktop().open(file_path);
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            printMessage("The file, " + file_path + ", was not found.");
        }
    }

    /**
     *  An event listener to pause runtime of menus with submit buttons, for user inputs and interactions.
     * @param s An unused ActionEvent that triggers the parent form of the submit button to continue execution.
     */
    public void submitPerformed(ActionEvent s) {
        lock_input_form = false;
    }
}