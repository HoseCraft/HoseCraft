package com.infermc.hosecraft.server;

import com.infermc.hosecraft.logging.ServerLogger;
import com.infermc.hosecraft.plugins.PluginManager;
import com.mojang.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Server {
    public PluginManager pluginManager = new PluginManager(this);
    public Logger log;
    public MinecraftServer MC;
    public ArrayList<Player> players = new ArrayList<Player>();
    public ServerLogger loggerManager = new ServerLogger();

    public Server(MinecraftServer instance) {
        MC = instance;
        log = loggerManager.getLogger();
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
}
