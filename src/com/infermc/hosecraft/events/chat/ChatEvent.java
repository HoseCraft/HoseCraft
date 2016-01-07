package com.infermc.hosecraft.events.chat;

import com.infermc.hosecraft.events.Cancellable;
import com.infermc.hosecraft.events.Event;
import com.infermc.hosecraft.server.Player;

// Triggered when someone speaks
public class ChatEvent extends Event implements Cancellable {
    private boolean cancelled = false;

    public void setCancelled(boolean status) {
        this.cancelled = status;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    private String message;
    private Player source;

    public ChatEvent(Player src, String msg) {
        this.source = src;
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }

    public Player getSource() {
        return this.source;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
