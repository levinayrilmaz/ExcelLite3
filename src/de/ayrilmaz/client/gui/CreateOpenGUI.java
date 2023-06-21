package de.ayrilmaz.client.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateOpenGUI extends JFrame {
    private JPanel panel;
    private JButton createButton;
    private JButton openButton;
    private JTextField row_field;
    private JTextField column_field;
    private JTextField name_field;

    public CreateOpenGUI() {
        setTitle("Excel Lite");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        createButton = new JButton("Create");
        openButton = new JButton("Open");

        row_field = new JTextField();
        column_field = new JTextField();
        name_field = new JTextField();



        panel.add(row_field);
        panel.add(column_field);
        panel.add(name_field);
        panel.add(createButton);
        panel.add(openButton);


        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(row_field.getText().equals("") || column_field.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Zahl ein!");
                }else{
                    dispose();
                    new TableGUI(Integer.parseInt(row_field.getText()), Integer.parseInt(column_field.getText()), name_field.getText());
                }
            }
        });

        /*
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileHandler

            }
        }

         */

        add(panel);
        setVisible(true);
    }
}
