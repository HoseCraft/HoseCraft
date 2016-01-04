package com.infermc.hosecraft.plugins;

import com.infermc.hosecraft.server.Player;
import com.infermc.hosecraft.server.Server;

import java.io.File;
import java.util.logging.Logger;

public class Plugin {
    private String name = "";
    private File dataFolder;
    private Logger logger;
    private Server serverinstance;

    private boolean enabled;

    public Plugin(Server serverinstance, String name) {
        this.serverinstance = serverinstance;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public File getDataFolder() {
        return getDataFolder();
    }
    public Logger getLogger() {
        return logger;
    }
    public Server getServer() {
        return this.serverinstance;
    }

    // Stuff that plugins can override

    public void onEnable() {
        // Called when plugin is enabled.
    }
    public void onDisable() {
        // Called when plugin is disabled
    }
}
