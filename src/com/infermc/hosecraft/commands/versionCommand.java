package com.infermc.hosecraft.commands;

import com.infermc.hosecraft.events.chat.CommandSource;
import com.infermc.hosecraft.server.Server;

public class versionCommand implements CommandInterface {
    private Server server;

    public versionCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        source.sendMessage("MC Classic running HoseCraft v"+this.server.getVersion()+"-"+this.server.getFlavour());
        return true;
    }
}
