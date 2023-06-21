package de.ayrilmaz.client.extra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

public class Database {

public Database() {
        if(!checkIfExists()) {
            create();
        }
    }

    private boolean checkIfExists() {
        try {
            String config_path = "src/db.properties";
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

    private void create() {
        try {
            String config_path = "src/db.properties";
            Properties properties = new Properties();


            properties.store(new FileOutputStream(config_path), null);
        }catch (Exception e) {
            System.out.println("Error while creating config file");
        }
    }

    public void set(String key, String value) {
        try {
            String config_path = "src/db.properties";
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
            String config_path = "src/db.properties";
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

    public String get(String key) {
        try {
            String config_path = "src/db.properties";
            FileInputStream fileInputStream = new FileInputStream(config_path);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties.getProperty(key);
        }catch (Exception e) {
            System.out.println("Error while getting value from config file");
        }
        return null;
    }

    public void remove(String key) {
        try {
            String config_path = "src/db.properties";
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
