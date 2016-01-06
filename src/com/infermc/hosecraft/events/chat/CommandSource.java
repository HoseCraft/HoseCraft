package com.infermc.hosecraft.events.chat;

import com.infermc.hosecraft.server.Server;

/**
 * Created by Thomas on 05/01/2016.
 */
public interface CommandSource {
    public abstract void sendMessage(String paramString);
    public abstract Server getServer();
    public abstract String getName();
}
