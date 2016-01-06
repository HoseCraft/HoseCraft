package com.infermc.hosecraft.events.player;

import com.infermc.hosecraft.events.Event;
import com.infermc.hosecraft.server.Player;

public class PlayerQuitEvent extends Event {

    private Player player;
    private String quitMessage;

    public PlayerQuitEvent(Player player, String message) {
        this.player = player;
        this.quitMessage = message;
    }
    public Player getPlayer() {
        return this.player;
    }
    public String getMessage() {
        return this.quitMessage;
    }
    public void setMessage(String msg) {
        this.quitMessage = msg;
    }
}
