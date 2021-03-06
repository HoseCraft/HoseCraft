package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.plugins.Plugin;
import com.infermc.hosecraft.server.Server;

import java.util.List;

// Plugin Listing
public class PluginCommand implements CommandInterface {
    private Server server;

    public PluginCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        List<Plugin> pluginList = server.getPluginManager().getPlugins();
        String cmd = "There are (" + pluginList.size() + ") loaded plugins: ";
        for (Plugin pl : pluginList) {
            if (pl.isEnabled()) {
                cmd = cmd + "&a" + pl.getName() + " ";
            } else {
                cmd = cmd + "&c"+pl.getName() + " ";
            }
        }
        source.sendMessage(cmd.trim());
        return true;
    }
}
