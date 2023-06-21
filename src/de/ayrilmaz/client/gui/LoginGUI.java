package de.ayrilmaz.client.gui;

import de.ayrilmaz.client.ExcelLite;
import de.ayrilmaz.client.extra.Config;
import de.ayrilmaz.client.extra.Database;
import de.ayrilmaz.client.extra.Encryption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LoginGUI extends JFrame{
    private JPanel loginPanel;
    private JPanel registrierungPanel;
    private JButton loginButton;
    private JButton registrierungsButton;

    public LoginGUI() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(checkIfLoggedIn()){
            dispose();
            new CreateOpenGUI();
        }else{
            setTitle("ExceLite");
            setSize(300, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            loginPanel = createLoginPanel();
            registrierungPanel = createRegistrierungPanel();

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Login", loginPanel);
            tabbedPane.addTab("Registrierung", registrierungPanel);

            add(tabbedPane);
            setVisible(true);
        }


    }

    private JPanel createLoginPanel() {
        Database db = new Database();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(loginLabel);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        loginButton = new JButton("Login");


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (usernameField.getText().equals("") || passwordField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Felder dürfen nicht leer sein!");
                    return;
                }

                String password_encrypted = Encryption.encrypt(passwordField.getText());
                //String db_password = db.get(usernameField.getText()).split(";")[2];

                if(db.checkIfKeyExists(usernameField.getText())) {
                    String db_password = db.get(usernameField.getText()).split(";")[2];
                    System.out.println(db_password);
                    System.out.println(password_encrypted);
                    if (db_password.equals(password_encrypted)) {
                        JOptionPane.showMessageDialog(null, "Login erfolgreich!");
                        ExcelLite.config.set("username", usernameField.getText());
                        ExcelLite.config.set("password", passwordField.getText());
                        dispose();
                        new TableGUI(100, 100, "unnamed");
                    } else {
                        JOptionPane.showMessageDialog(null, "Login fehlgeschlagen!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Login fehlgeschlagen!");
                }



            }


        });

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(loginButton);

        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRegistrierungPanel() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Database db = new Database();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));

        JLabel registrierungLabel = new JLabel("Registrierung");
        registrierungLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(registrierungLabel);

        JTextField emailField = new JTextField();
        JTextField registrierungUsernameField = new JTextField();
        JPasswordField registrierungPasswordField = new JPasswordField();
        JPasswordField registrierungPasswordRepeatField = new JPasswordField();
        registrierungsButton = new JButton("Registrieren");




        registrierungsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = Encryption.encrypt(registrierungPasswordField.getText());
                if (registrierungPasswordField.getText()=="" || registrierungPasswordRepeatField.getText()=="" || registrierungUsernameField.getText()=="" || emailField.getText()==""){
                    JOptionPane.showMessageDialog(null, "Felder dürfen nicht leer sein!");
                    return;
                }
                if(!registrierungPasswordField.getText().equals(registrierungPasswordRepeatField.getText())){
                    JOptionPane.showMessageDialog(null, "Passwörter stimmen nicht überein!");
                    return;
                }
                if (db.get(registrierungUsernameField.getText()) != null){
                    JOptionPane.showMessageDialog(null, "Benutzername bereits vergeben!");
                    return;
                }

                db.set(registrierungUsernameField.getText(), registrierungUsernameField.getText()+";"+emailField.getText()+";"+password);

            }
        });

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Benutzername:"));
        formPanel.add(registrierungUsernameField);
        formPanel.add(new JLabel("Passwort:"));
        formPanel.add(registrierungPasswordField);
        formPanel.add(new JLabel("Passwort wiederholen:"));
        formPanel.add(registrierungPasswordRepeatField);
        formPanel.add(registrierungsButton);

        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private boolean checkIfLoggedIn(){
        if(ExcelLite.config.checkIfKeyExists("username") && ExcelLite.config.checkIfKeyExists("password")){
            return !ExcelLite.config.getValue("username").equals("") && !ExcelLite.config.getValue("password").equals("");
        }else{
            return false;
        }
    }

}
