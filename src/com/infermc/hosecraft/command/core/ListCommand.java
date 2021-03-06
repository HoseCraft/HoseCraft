package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.server.Server;

public class ListCommand implements CommandInterface {

    private Server server;

    public ListCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        source.sendMessage("There are " + this.server.getPlayers().size() + " players online.");
        return true;
    }
}
