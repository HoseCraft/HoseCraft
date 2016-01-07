package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.server.Server;
import com.infermc.hosecraft.util.Chat;

public class banCommand implements CommandInterface {
    private Server server;

    public banCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        if (source.isOperator()) {
            if (args.length >= 1) {
                boolean res;
                String scentence = "";

                // Handle the command.
                if (args.length == 1) {
                    res = this.server.ban(args[0]);
                } else {
                    for (int i=1;i<args.length; i++) {
                        scentence += " "+args[i];
                    }
                    res = this.server.ban(args[0],scentence.trim());
                }

                // Log to console.
                if (res) {
                    if (scentence == "") {
                        server.getLogger().info(source.getName() + " banned " + args[0] + " from the server.");
                    } else {
                        server.getLogger().info(source.getName() + " banned " + args[0] + " from the server for "+scentence);
                    }
                }
            } else {
                source.sendMessage(Chat.YELLOW+"Syntax: /ban <username> [reason]");
            }
            return true;
        }
        return false;
    }
}
