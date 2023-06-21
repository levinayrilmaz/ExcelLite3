package de.ayrilmaz.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame{
    private JPanel contentPane;
    private JButton toggleDarkModeButton;

    public Test() {
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setTitle("Dark Mode Example");

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        toggleDarkModeButton = new JButton("Toggle Dark Mode");
        contentPane.add(toggleDarkModeButton, BorderLayout.CENTER);

        setContentPane(contentPane);
    }

    private void setupListeners() {
        toggleDarkModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleDarkMode();
            }
        });
    }

    private void toggleDarkMode() {
        boolean isDarkMode = isDarkModeEnabled();

        if (isDarkMode) {
            // Deaktiviere Dark Mode
            contentPane.setBackground(UIManager.getColor("Panel.background"));
            toggleDarkModeButton.setText("Toggle Dark Mode");
        } else {
            // Aktiviere Dark Mode
            contentPane.setBackground(Color.DARK_GRAY);
            toggleDarkModeButton.setText("Toggle Light Mode");
        }
    }

    private boolean isDarkModeEnabled() {
        // Hier kannst du die Logik implementieren, um den Dark Mode-Status abzufragen.
        // Dies kann z.B. eine Einstellung in einer Konfigurationsdatei oder in den Benutzereinstellungen sein.
        return false; // Hier muss deine Logik eingefügt werden
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Test frame = new Test();
                frame.setVisible(true);
            }
        });
    }
}
