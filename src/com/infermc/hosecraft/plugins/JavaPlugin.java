package com.infermc.hosecraft.plugins;

import com.infermc.hosecraft.server.Server;

import java.io.File;
import java.util.logging.Logger;

public class JavaPlugin implements Plugin {
    private String name;
    private File dataFolder;
    private Logger logger;
    private Server serverinstance;
    private boolean enabled;

    public JavaPlugin() {
        ClassLoader classLoader = getClass().getClassLoader();
        if (!(classLoader instanceof PluginClassLoader)) {
            throw new IllegalStateException("JavaPlugin requires " + PluginClassLoader.class.getName() + " but got " + classLoader.getClass().getName());
        }
        ((PluginClassLoader) classLoader).initialize(this);
    }

    public String getName() {
        return name;
    }

    public File getDataFolder() {
        return this.dataFolder;
    }

    public Logger getLogger() {
        return logger;
    }

    public Server getServer() {
        return this.serverinstance;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean status) throws Exception {
        if (status) {
            try {
                onEnable();
                this.enabled = status;
            } catch (Exception e) {
                serverinstance.getLogger().warning("Error enabling.");
            }
        } else {
            try {
                onDisable();
                this.enabled = status;
            } catch (Exception e) {
                serverinstance.getLogger().warning("Error enabling.");
            }
        }
    }

    // Stuff that plugins can override
    public void onLoad() {
        // Called when the plugin is loaded.
    }

    public void onEnable() {
        // Called when plugin is enabled.
    }

    public void onDisable() {
        // Called when plugin is disabled
    }

    protected final void init(String name, Server server) {
        this.name = name;
        this.serverinstance = server;
        this.logger = server.loggerManager.createLogger(name);
        this.dataFolder = new File(serverinstance.MC.workingDirectory + "/plugins/" + this.name + "/");
    }
}
