/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.configmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class ConfigData {
    
    private final Properties newConfig;
    private final Properties oldConfig;
    private final String fullpath;
    private final String name;
    private final File File;
    private final ArrayList<Sender> newSenderList;
    private final ArrayList<String> oldSenderList;
    private final ArrayList<String> KeyList;
    private final String Extention;
    private final boolean oldExist;
    private final HashMap<String, Sender> SenderList;
    
    /**
     * Create a new ConfigManager (Default) (Output: Config.properties)
     *
     * @param path The path of the plugin
     * @throws IOException See Javadoc from Java
     */
    public ConfigData(String path) throws IOException {
        this.Extention = ".properties.";
        this.name = "/Config";
        this.newConfig = new Properties();
        this.oldConfig = new Properties();
        this.newSenderList = new ArrayList<>();
        this.oldSenderList = new ArrayList<>();
        this.fullpath = path + name + Extention;
        this.KeyList = new ArrayList<>();
        this.File = new File(fullpath);
        this.SenderList = new HashMap();
        this.oldExist = iniOld();
    }

    /**
     * Create new ConfigManager with own name (Example: Config.properties)
     *
     * @param path The path of the plugin.
     * @param name The name of the propertie file.
     * @throws IOException
     */
    public ConfigData(String path, String name) throws IOException {
        this.Extention = ".properties";
        this.name = "/" + name;
        this.newConfig = new Properties();
        this.oldConfig = new Properties();
        this.newSenderList = new ArrayList<>();
        this.oldSenderList = new ArrayList<>();
        this.KeyList = new ArrayList<>();
        this.fullpath = path + name + Extention;
        this.File = new File(fullpath);
        this.SenderList = new HashMap();
        this.oldExist = iniOld();
    }

    /**
     * Create new ConfigManager with own name and extention (Example:
     * Config.config)
     *
     * @param path The path of the plugin.
     * @param name The name of the properties file.
     * @param extention The extention of the properties file.
     * @throws IOException
     */
    public ConfigData(String path, String name, String extention) throws IOException {
        this.Extention = extention;
        this.name = "/" + name;
        this.newConfig = new Properties();
        this.oldConfig = new Properties();
        this.newSenderList = new ArrayList<>();
        this.oldSenderList = new ArrayList<>();
        this.KeyList = new ArrayList<>();
        this.fullpath = path + name + extention;
        this.File = new File(fullpath);
        this.SenderList = new HashMap();
        this.oldExist = iniOld();

    }

    private boolean iniOld() throws IOException {
        if (File.exists()) {
            FileInputStream fis = new FileInputStream(fullpath);
            oldConfig.load(fis);
            return true;
        }
        return false;
    }

    private void WriteToFile() {

        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new FileWriter(fullpath, false));
            if (!newSenderList.isEmpty()) {

                for (Sender send : newSenderList) {
                    switch (send.Typ) {
                        case Commend -> pWriter.println(send.key);
                        case Property -> pWriter.println(send.key + "=" + send.value);
                        default -> pWriter.println();
                    }
                }
            } else {
                System.out.println("[ConfigManager WARNING] No values for Config found!");
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            if (pWriter != null) {
                pWriter.flush();
                pWriter.close();
            }
        }
    }

    /**
     * Create a new properties file. If old settings are available, they will be
     * adopted.
     *
     * @throws IOException
     */
    public void CreateConfig() throws IOException {
        /*
        Prüfen, ob Datei existiert
        Wenn ja, alte Config mit neuer Config vergleichen
         */
        if (File.exists()) {
            BufferedReader in = null;
            try {
                //Datei wird eingelesen und in einzelne Zeilen aufgeteilt
                in = new BufferedReader(new FileReader(fullpath));
                String zeile = null;
                while ((zeile = in.readLine()) != null) {
                    oldSenderList.add(zeile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (hatChange()) {
                WriteToFile();
            }
        } else {
            WriteToFile();
        }

    }

    /**
     * Change a setting in the game and in the properties file
     *
     * @param key The key of the existing setting as String
     * @param value The new value of the setting
     */
    public void setSetting(String key, Object value) {
        String stValue = String.valueOf(value);
        newConfig.setProperty(key, stValue);
        SenderList.get(key).value = stValue;
        WriteToFile();
    }

    /**
     * Get the value of the setting
     *
     * @param key The key of the setting as String
     * @return Value returns as String
     */
    public String getSetting(String key) {
        return String.valueOf(newConfig.getProperty(key));
    }

    /**
     * Get a List of the existing keys
     *
     * @return A ArrayList of String of existing keys
     */
    public ArrayList<String> getKeys() {
        return KeyList;
    }

    /**
     *  Dose something change in the config
     * @return True, if something is changed, else if not.
     */
    public boolean hatChange() {
        return oldSenderList.size() != newSenderList.size();
    }

    /**
     * Add a commend line to the properties file
     *
     * @param command The command as String
     */
    public void addCommend(String command) {
        newSenderList.add(new Sender(command));
    }

    /**
     * Add a setting to the properties file
     *
     * @param key The key of the setting as String
     * @param value The value of the setting as Object 
     */
    public void addSetting(String key, Object value) {
        String sValue = String.valueOf(value);
        KeyList.add(key);
        Sender send;
        if (oldExist) {
            if (oldConfig.containsKey(key)) {
                send = new Sender(Typ.Property, key, oldConfig.getProperty(key));
                newSenderList.add(send);
                newConfig.setProperty(key, oldConfig.getProperty(key));
            } else {
                send = new Sender(Typ.Property, key, sValue);
                newSenderList.add(send);
                newConfig.setProperty(key, sValue);
            }
        } else {
            send = new Sender(Typ.Property, key, sValue);
            newSenderList.add(send);
            newConfig.setProperty(key, sValue);
        }
        SenderList.put(key, send);

    }

    /**
     * Add a empty line to the properties file
     */
    public void addEmptyLine() {
        newSenderList.add(new Sender());
    }
    
    public String getConfigName() {
        return name;
    }

    public String getExtention() {
        return Extention;
    }

    public File getFile() {
        return File;
    }

    public String getFullpath() {
        return fullpath;
    }   

    public boolean isOldExist() {
        return oldExist;
    }
    
    

    private class Sender {

        private Typ Typ;
        private String key;
        private String value;

        public Sender() {
            this.Typ = Typ.EmptyLine;
            this.key = null;
            this.value = null;

        }

        public Sender(String command) {
            this.Typ = Typ.Commend;
            this.key = command;
            this.value = null;
        }

        public Sender(Typ Typ, String key, String value) {
            this.Typ = Typ;
            this.key = key;
            this.value = value;
        }

    }

    private enum Typ {
        Property,
        EmptyLine,
        Commend
    }
    
}
