package com.infermc.hosecraft.commands;

import com.infermc.hosecraft.events.chat.CommandSource;
import com.infermc.hosecraft.server.Server;

public class listCommand implements CommandInterface {

    private Server server;
    public listCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        source.sendMessage("There are "+this.server.getPlayers().size()+" players online.");
        return true;
    }
}
