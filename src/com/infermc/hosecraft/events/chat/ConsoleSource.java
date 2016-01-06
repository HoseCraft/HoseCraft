package com.infermc.hosecraft.events.chat;

import com.infermc.hosecraft.server.Server;
import com.infermc.hosecraft.util.Chat;

public class ConsoleSource implements CommandSource {
    private Server server;

    public ConsoleSource(Server server) {
        this.server=server;
    }

    @Override
    public void sendMessage(String msg) {
        System.out.println(Chat.stripColour(msg));
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    public String getName() {
        return "CONSOLE";
    }

    @Override
    public Boolean isOperator() {
        return true;
    }
}
