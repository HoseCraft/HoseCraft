package com.infermc.hosecraft.command;

import com.infermc.hosecraft.server.Server;

public interface CommandSource {
    public abstract void sendMessage(String paramString);
    public abstract Server getServer();
    public abstract String getName();
    public abstract boolean isOperator();
}
