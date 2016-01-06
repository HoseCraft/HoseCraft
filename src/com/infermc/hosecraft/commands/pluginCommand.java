package com.infermc.hosecraft.commands;

import com.infermc.hosecraft.events.chat.CommandSource;
import com.infermc.hosecraft.plugins.Plugin;
import com.infermc.hosecraft.server.Server;

import java.util.List;

// Plugin Listing
public class pluginCommand implements CommandInterface {
    private Server server;
    public pluginCommand(Server server) {
        this.server = server;
    }
    @Override
    public boolean run(CommandSource source, String[] args) {
        List<Plugin> pluginList = server.getPluginManager().getPlugins();
        String cmd = "There are ("+pluginList.size()+") loaded plugins: ";
        for (Plugin pl : pluginList) {
            cmd = cmd + pl.getName() + " ";
        }
        source.sendMessage(cmd.trim());
        return true;
    }
}
