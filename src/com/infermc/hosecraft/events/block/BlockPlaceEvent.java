package com.infermc.hosecraft.events.block;

import com.infermc.hosecraft.events.Cancellable;
import com.infermc.hosecraft.events.Event;
import com.infermc.hosecraft.server.Location;
import com.infermc.hosecraft.server.Player;
import com.mojang.minecraft.level.tile.Block;

public class BlockPlaceEvent extends Event implements Cancellable {
    // Event Template
    private boolean cancelled = false;
    private Location location;

    public void setCancelled(boolean status) {
        this.cancelled = status;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    private Player player;
    private Block block;

    public BlockPlaceEvent(Block bk, Location loc, Player pl) {
        this.player = pl;
        this.block = bk;
        this.location = loc;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Location getLocation() { return this.location; }
    public Block getBlock() {
        return this.block;
    }
}
