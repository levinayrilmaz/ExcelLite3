package de.ayrilmaz.client.extra;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

public class Config {

    public static String used_font = "";
    public static String current_project_path = "";
    public static boolean autoSave = false;
    public static int countTables = 0;

    public static boolean changeDarkMode = false;

    public static boolean darkMode = false;
    public static Point position = new Point(0, 0);

    public Config() {
        if(!checkIfConfigExists()) {
            createConfig();
        }
        if(!checkIfKeyExists("username")) {
            this.set("username", "");
            this.set("password", "");
            this.set("current_project_path", "");
            this.set("auto_save", "false");
        }
    }

    private boolean checkIfConfigExists() {
        try {
            String config_path = "src/config.properties";
            FileInputStream fileInputStream = new FileInputStream(config_path);
            Properties properties = new Properties();
            properties.load(fileInputStream);
        }catch (FileNotFoundException e) {
            return false;
        }catch (Exception e) {
            System.out.println("Error while reading config file");
        }
        return true;
    }

    private void createConfig() {
        try {
            String config_path = "src/config.properties";
            Properties properties = new Properties();

            properties.setProperty("username", "");
            properties.setProperty("password", "");

            properties.store(new FileOutputStream(config_path), null);
        }catch (Exception e) {
            System.out.println("Error while creating config file");
        }
    }

    public void set(String key, String value) {
        try {
            String config_path = "src/config.properties";
            FileInputStream fileInputStream = new FileInputStream(config_path);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            properties.setProperty(key, value);
            properties.store(new FileOutputStream(config_path), null);
        }catch (Exception e) {
            System.out.println("Error while adding to config file");
        }
    }

    public boolean checkIfKeyExists(String key) {
        try {
            String config_path = "src/config.properties";
            FileInputStream fileInputStream = new FileInputStream(config_path);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            if(properties.containsKey(key)) {
                return true;
            }
        }catch (Exception e) {
            System.out.println("Error while checking if key exists in config file");
        }
        return false;
    }

    public String getValue(String key) {
        try {
            String config_path = "src/config.properties";
            FileInputStream fileInputStream = new FileInputStream(config_path);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties.getProperty(key);
        }catch (Exception e) {
            System.out.println("Error while getting value from config file");
        }
        return null;
    }

    public void removeFromConfig(String key) {
        try {
            String config_path = "src/config.properties";
            FileInputStream fileInputStream = new FileInputStream(config_path);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            properties.remove(key);
            properties.store(new FileOutputStream(config_path), null);
        }catch (Exception e) {
            System.out.println("Error while removing from config file");
        }
    }
}
