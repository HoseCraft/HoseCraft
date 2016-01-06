package com.infermc.hosecraft.events.player;

import com.infermc.hosecraft.events.Event;
import com.infermc.hosecraft.events.HandlerList;
import com.infermc.hosecraft.server.Player;

public final class PlayerJoinEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    public HandlerList getHandlers() { return handlers; }

    private String message;
    private Player player;

    public PlayerJoinEvent(Player player) {
        this.player = player;
        this.message = player.getName()+" joined the game";
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
    public Player getPlayer() { return this.player; }
}
