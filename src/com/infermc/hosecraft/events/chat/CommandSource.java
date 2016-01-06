package com.infermc.hosecraft.events.chat;

import com.infermc.hosecraft.server.Server;

public interface CommandSource {
    public abstract void sendMessage(String paramString);
    public abstract Server getServer();
    public abstract String getName();
    public abstract Boolean isOperator();
}
