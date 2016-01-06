package com.infermc.hosecraft.events.player;

import com.infermc.hosecraft.events.Cancellable;
import com.infermc.hosecraft.events.Event;
import com.infermc.hosecraft.server.Location;
import com.infermc.hosecraft.server.Player;
import com.mojang.minecraft.level.tile.Block;

public class PlayerMoveEvent extends Event implements Cancellable {

    // Event Template
    private boolean cancelled = false;
    public void setCancelled(boolean status) {
        this.cancelled=status;
    }
    public boolean isCancelled() {
        return this.cancelled;
    }


    private Player pl;
    private Location from;
    private Location to;

    public PlayerMoveEvent(Player pl, Location from, Location to) {
        this.pl = pl;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return this.pl;
    }
    public Location getFrom() {
        return this.from;
    }
    public Location getTo() {
        return this.to;
    }

    public void setFrom(Location from) {
        this.from = from;
    }
    public void setTo(Location to) {
        this.to = to;
    }
}
