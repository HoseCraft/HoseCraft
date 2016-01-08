package com.infermc.hosecraft.command;

import com.infermc.hosecraft.server.Server;

public interface CommandSource {
    void sendMessage(String paramString);

    Server getServer();

    String getName();

    boolean isOperator();
}
