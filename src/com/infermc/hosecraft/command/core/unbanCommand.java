package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.server.Server;
import com.infermc.hosecraft.util.Chat;

public class unbanCommand implements CommandInterface {
    private Server server;

    public unbanCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        if (source.isOperator()) {
            if (args.length >= 1) {
                boolean res = server.unban(args[0]);
                // Log to console.
                if (res) {
                    server.getLogger().info(source.getName() + " unbanned " + args[0] + " from the server.");
                    source.sendMessage("Successfully unbanned "+args[0]);
                } else {
                    source.sendMessage(args[0]+" isn't banned!");
                }
            } else {
                source.sendMessage(Chat.YELLOW+"Syntax: /unban <username>");
            }
            return true;
        }
        return false;
    }
}
