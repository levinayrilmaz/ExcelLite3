package de.ayrilmaz.client;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import de.ayrilmaz.client.extra.Config;
import de.ayrilmaz.client.gui.LoginGUI;
import de.ayrilmaz.client.gui.TableGUI;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class ExcelLite {

    public static Config config = new Config();


    public static void main(String[] args) {

        FlatMacLightLaf.setup();
/*
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginGUI();
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });

 */

        new TableGUI(100, 100, "Tabelle 1");


     }

}
