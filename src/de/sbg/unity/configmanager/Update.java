package de.sbg.unity.configmanager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import net.risingworld.api.Internals;
import net.risingworld.api.Plugin;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerSpawnEvent;
import net.risingworld.api.objects.Player;

/**
 * Ceck for Update
 */
public final class Update implements Listener {

    private final URI url;
    private Scanner Scanner;
    private String resultString;
    private final String currentVersion;
    private final Plugin plugin;

    /**
     * Create new Update-Object
     *
     * @param plugin
     * @param URL
     * @throws IOException
     * @hidden Only for DEV
     */
    public Update(Plugin plugin, String URL) throws IOException, URISyntaxException {
        this.plugin = plugin;
        this.currentVersion = plugin.getDescription("version");
        this.url = new URI(URL);
        Scanner = new Scanner(url.toURL().openStream());
        if (Scanner.hasNext()) {
            resultString = Scanner.nextLine();
        } else {
            resultString = null;
        }
        Scanner.close();
        DoScann(false, true);
    }

    public void DoScann(boolean newScann, boolean console) throws IOException {
        if (newScann) {
            newScann();
        }
        String Plugin = plugin.getDescription("name");
        if (hasUpdate()) {
            plugin.registerEventListener(this);
            if (console) {

                Internals.println("[" + Plugin + "-Warning] [Update] -------- New Update --------", 14);
                Internals.println("[" + Plugin + "-Warning] [Update] Plugin is NOT up to date", 14);
                Internals.println("[" + Plugin + "-Warning] [Update] Current Version: " + currentVersion, 14);
                Internals.println("[" + Plugin + "-Warning] [Update]     New Version: " + resultString, 14);
                Internals.println("[" + Plugin + "-Warning] [Update] ----------------------------", 14);
            }
        } else {
            Internals.println("[" + Plugin + "-Info] [Update] Plugin is up to date!", 10);
        }
    }

    private void newScann() throws IOException {
        Scanner = new Scanner(url.toURL().openStream());
        if (Scanner.hasNext()) {
            resultString = Scanner.nextLine();
        } else {
            resultString = null;
        }
        Scanner.close();
    }

    public boolean hasUpdate() {
        if (resultString != null) {
            return !currentVersion.equals(resultString);
        }
        return false;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getOnlineVersion() {
        return resultString;
    }

    @EventMethod
    public void onPlayerConnectEvent(PlayerSpawnEvent event) {
        Player player = event.getPlayer();
        if (hasUpdate()) {
            if (Server.getType() == Server.Type.Singleplayer) {
                player.sendTextMessage(Color("Orange", "[" + plugin.getDescription("name") + "] Plugin has an update!"));
            } else {
                if (player.isAdmin()) {
                    player.sendTextMessage(Color("Orange", "[" + plugin.getDescription("name") + "] Plugin has an update!"));
                }
            }
        }
    }
    
    private String Color(String color, String text) {
        return "<color=" + color + ">" + text + "</color>";
    }

}
