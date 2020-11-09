package com.dreamteam.database;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.lang.Thread;
import java.util.Scanner;

public class Menu implements ActionListener {

    private volatile boolean lock_menu;
    private volatile boolean lock_text_box;
    private Options user_selection;
    private final JFrame MENU_FRAME;
    private final JTextArea DIALOG_TEXT;
    private final Border MENU_BORDER;

    public Menu(EnumSet<Options> menu_selection) {
        this.lock_menu = true;
        this.lock_text_box = true;
        this.DIALOG_TEXT = new JTextArea();
        Dimension dialog_size = new Dimension(200, 100);

        this.MENU_FRAME = new JFrame();
        this.MENU_BORDER = BorderFactory.createEmptyBorder(125, 300, 125, 300);

        JPanel menu_panel = new JPanel();
        JLabel menu_label = new JLabel("Select a choice?");

        menu_panel.add(menu_label);
        menu_panel.setBorder(MENU_BORDER);
        menu_panel.setLayout(new GridLayout(0, 1));

        for (Options option : menu_selection) {
            JButton option_button = new JButton(String.valueOf(option));
            option_button.addActionListener(this);
            menu_panel.add(option_button);
        }

        this.DIALOG_TEXT.setPreferredSize(dialog_size);
        this.DIALOG_TEXT.setText("Nothing to show.");

        this.MENU_FRAME.add(menu_panel, BorderLayout.CENTER);
        this.MENU_FRAME.add(this.DIALOG_TEXT, BorderLayout.SOUTH);
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

    public void closeMenu() {
        this.MENU_FRAME.dispose();
    }

    /**
     * This method reduces code to create new GUI windows
     * for all operations being done
     *
     * @param title Title to know what operation user is on
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

        while (lock_text_box) Thread.onSpinWait();
        lock_text_box = true;

        ArrayList<String> responses = new ArrayList<>();
        for (String field : fields) {
            responses.add(form_inputs.get(fields.indexOf(field)).getText());
        }

        text_frame.dispose();
        this.DIALOG_TEXT.setText("Nothing to show.");
        return responses;
    }

    public void submitPerformed(ActionEvent s) {
        lock_text_box = false;
    }

    public void printMessage(String message) {
        this.DIALOG_TEXT.setText(message);
    }

    public void showReport(String date) {

        File pdfFile = new File("files/reports/daily-report_" + date + ".pdf");
        if (pdfFile.exists()) {

            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}