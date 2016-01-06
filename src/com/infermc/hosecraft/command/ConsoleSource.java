package com.infermc.hosecraft.command;

import com.infermc.hosecraft.server.Server;
import com.infermc.hosecraft.util.Chat;

public class ConsoleSource implements CommandSource {
    private Server server;

    public ConsoleSource(Server server) {
        this.server=server;
    }

    public void sendMessage(String msg) {
        System.out.println(Chat.stripColour(msg));
    }

    public Server getServer() {
        return this.server;
    }

    public String getName() {
        return "CONSOLE";
    }

    public boolean isOperator() {
        return true;
    }
}
