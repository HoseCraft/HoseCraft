package com.infermc.hosecraft.events.player;

import com.infermc.hosecraft.events.Cancellable;
import com.infermc.hosecraft.events.Event;
import com.infermc.hosecraft.server.Player;

public class PlayerKickEvent extends Event implements Cancellable {
    private Boolean cancelled = false;
    private Player player;
    private String reason;

    public PlayerKickEvent(Player player, String reason) {
        this.player = player;
        this.reason = reason;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public void setCancelled(boolean status) {
        this.cancelled = status;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
