package com.infermc.hosecraft.server;

import com.infermc.hosecraft.command.CommandRegistry;
import com.infermc.hosecraft.logging.ServerLogger;
import com.infermc.hosecraft.permissions.PermissionProvider;
import com.infermc.hosecraft.plugins.PluginManager;
import com.infermc.hosecraft.wrappers.ConfigSection;
import com.infermc.hosecraft.wrappers.YamlConfiguration;
import com.mojang.minecraft.server.MinecraftServer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Server {
    public PluginManager pluginManager = new PluginManager(this);
    public Logger log;
    public MinecraftServer MC;
    public ArrayList<Player> players = new ArrayList<Player>();
    public ServerLogger loggerManager = new ServerLogger();
    private PermissionProvider permissionProvider = new PermissionProvider();
    private CommandRegistry commandRegistry = new CommandRegistry(this);

    public Double version;
    public String flavour;

    public Server(MinecraftServer instance) {
        MC = instance;
        log = loggerManager.getLogger();

        // Load hosecraft stuff
        InputStream input = getClass().getResourceAsStream("/hosecraft.yml");
        YamlConfiguration hosecraft = new YamlConfiguration();
        if (input != null) {
            hosecraft.load(input);
        }
        ConfigSection section = hosecraft.getRoot();
        this.version = (Double) section.get("version",0.0);
        this.flavour = section.getString("flavour","MISSINGNO");

    }
    public ArrayList<Player> getPlayers() {
        return this.players;
    }
    public PluginManager getPluginManager(){
        return pluginManager;
    }
    public Logger getLogger() {
        return log;
    }
    public Double getVersion() { return this.version; }
    public String getFlavour() { return this.flavour; }
    public PermissionProvider getPermissionProvider() {
        return this.permissionProvider;
    }
    public void setPermissionProvider(PermissionProvider permissionProvider) {
        this.permissionProvider = permissionProvider;
    }
    public CommandRegistry getCommandRegistry() {
        return this.commandRegistry;
    }
}
