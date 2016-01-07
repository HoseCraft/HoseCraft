package com.infermc.hosecraft.plugins;

import com.infermc.hosecraft.server.Server;

import java.io.File;
import java.util.logging.Logger;

public interface Plugin {
    String getName();

    File getDataFolder();

    Logger getLogger();

    Server getServer();

    void onLoad();

    void onEnable();

    void onDisable();

    boolean isEnabled();

    void setEnabled(boolean status);
}
