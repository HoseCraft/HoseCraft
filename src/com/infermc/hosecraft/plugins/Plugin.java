package com.infermc.hosecraft.plugins;

import com.infermc.hosecraft.server.Player;
import com.infermc.hosecraft.server.Server;

import java.io.File;
import java.util.logging.Logger;

public interface Plugin {
    public String getName();
    public File getDataFolder();
    public Logger getLogger();
    public Server getServer();
    public void onEnable();
    public void onDisable();
}
