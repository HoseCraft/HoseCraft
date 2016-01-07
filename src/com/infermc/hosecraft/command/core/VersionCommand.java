package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.server.Server;

public class VersionCommand implements CommandInterface {
    private Server server;

    public VersionCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        source.sendMessage("MC Classic running HoseCraft v" + this.server.getVersion() + "-" + this.server.getFlavour());
        return true;
    }
}
