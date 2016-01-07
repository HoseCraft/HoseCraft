package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.server.Player;
import com.infermc.hosecraft.server.Server;
import com.infermc.hosecraft.util.Chat;

public class sayCommand implements CommandInterface {
    private Server server;
    public sayCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        if (source.isOperator()) {
            if (args.length > 0) {
                for (Player p : server.getPlayers()) {
                    p.sendMessage(String.join(" ",args));
                }
            } else {
                source.sendMessage(Chat.YELLOW+"Syntax: /broadcast <message>");
            }
            return true;
        }
        return false;
    }
}