package de.sbg.unity.configmanager;

/**
 * Send text to the Console
 * @hidden Only for dev
 * @author pbronke
 */
public class cmConsole {
    
    private final ConfigManager plugin;

    /**
     * Create new asConsole-Object
     * @param plugin
     */
    public cmConsole(ConfigManager plugin) {
        this.plugin = plugin;
    }

    /**
     * Send Info to Console
     * @param msg
     */
    public void sendInfo(Object msg) {
        System.out.println("[" + plugin.getDescription("name") + "-Info] " + msg);
    }

    /**
     * Send Info to Console
     * @param ClassName
     * @param msg
     */
    public void sendInfo(String ClassName, Object msg) {
        System.out.println("[" + plugin.getDescription("name") + "-Info] [" + ClassName + "] " + msg);
    }
        
    /**
     * Send Error to Console
     * @param msg
     */
    public void sendErr(Object msg) {
        System.err.println("[" + plugin.getDescription("name") + "-ERROR] " + msg);
    }

    /**
     * Send Error to Console
     * @param ClassName
     * @param msg
     */
    public void sendErr(String ClassName, Object msg) {
        System.err.println("[" + plugin.getDescription("name") + "-ERROR] [" + ClassName + "] " + msg);
    }
    
    /**
     * Send Warning to Console
     * @param msg
     */
    public void sendWarning(Object msg) {
        System.out.println("[" + plugin.getDescription("name") + "-Warning] " + msg);
    }

    /**
     * Send Warning to Console
     * @param ClassName
     * @param msg
     */
    public void sendWarning(String ClassName, Object msg) {
        System.out.println("[" + plugin.getDescription("name") + "-Warning] [" + ClassName + "] " + msg);
    }
    
    /**
     * Send Debug to Console
     * @param msg
     */
    public void sendDebug(Object msg) {
        System.out.println("[" + plugin.getDescription("name") + "-Debug] " + msg);
    }

    /**
     * Send Debug to Console
     * @param ClassName
     * @param msg
     */
    public void sendDebug(String ClassName, Object msg) {
        System.out.println("[" + plugin.getDescription("name") + "-Debug] [" + ClassName + "] " + msg);
    }
    
}
