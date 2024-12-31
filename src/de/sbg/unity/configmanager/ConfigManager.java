package de.sbg.unity.configmanager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import net.risingworld.api.Plugin;

public class ConfigManager extends Plugin {
    
    private HashMap<String, ConfigData> Configs;
    private cmConsole Console;
    private String PluginVersion;
    
    @Override
    public void onEnable() {
        this.Console = new cmConsole(this);
        this.PluginVersion = this.getDescription("version");
        Console.sendInfo("Enabled Version " + PluginVersion);
        this.Configs = new HashMap<>();
        Console.sendInfo("Check for Updates...");
        try {
            Update up = new Update(this, "https://www.sandboxgamer.de/Downloads/Plugins/risingworld/unity/ConfigManager/version.txt");
        } catch (IOException | URISyntaxException ioex) {
            Console.sendErr("Update", "Can not lock for new Version of the plugin!");
        }
    }

    @Override
    public void onDisable() {
        Console.sendInfo("Desabled");
    }
    /**
     * Create new ConfigData Object
     * (This Methode create only the ConfigData-Object, not the File)
     * @param pluginName - The name of the plugin;
     * @param Path - The Path of the Config
     * @return The new ConfigData-Object
     * @throws IOException
     */
    public ConfigData newConfig(String pluginName, String Path) throws IOException{
        ConfigData cd = new ConfigData(Path);
        Configs.put(pluginName, cd);
        return cd;
    }
    /**
     * Create new ConfigData Object
     * (This Methode create only the ConfigData-Object, not the File)
     * @param pluginName - The name of the plugin;
     * @param Path - The Path of the Config
     * @param name - The name of the Config-File
     * @return The new ConfigData-Object
     * @throws IOException
     */
    public ConfigData newConfig(String pluginName, String Path, String name) throws IOException {
        ConfigData cd = new ConfigData(Path, name);
        Configs.put(pluginName, cd);
        return cd;
    }
    
    /**
     * Create new ConfigData Object
     * (This Methode create only the ConfigData-Object, not the File)
     * @param pluginName - The name of the plugin;
     * @param Path - The Path of the Config
     * @param name - The name of the Config-File
     * @param extention The extention of the Config-File (.config, .properties)
     * @return The new ConfigData-Object
     * @throws IOException
     */
    public ConfigData newConfig(String pluginName, String Path, String name, String extention) throws IOException {
        ConfigData cd = new ConfigData(Path, name, extention);
        Configs.put(pluginName, cd);
        return cd;
    }
    
    /**
     * Get the Config of the Plugin
     * (ONLY USING IF CONFIG IS ALREADY LOADED)
     * @param pluginName The name of the plugin as String
     * @return Loaded Config as ConfigData
     */
    public ConfigData getLoadedConfig(String pluginName) {
        return Configs.get(pluginName);
    }
    
   
    

}
