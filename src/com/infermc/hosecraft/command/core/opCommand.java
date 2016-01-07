package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.server.Player;
import com.infermc.hosecraft.server.Server;
import com.infermc.hosecraft.util.Chat;

// Ops a player.
public class opCommand implements CommandInterface {
    private Server server;

    public opCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        if (source.isOperator()) {
            if (args.length >= 1) {
                this.server.MC.opPlayer(args[0]);
                for (Player p : this.server.getPlayers()) {
                    if (p.isOperator()) p.sendMessage(Chat.GRAY+source.getName()+" opped "+args[0]);
                }
            } else {
                source.sendMessage(Chat.YELLOW+"Syntax: /op <username>");
            }
            return true;
        }
        return false;
    }
}
