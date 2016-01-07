package com.infermc.hosecraft.events.block;

import com.infermc.hosecraft.events.Cancellable;
import com.infermc.hosecraft.events.Event;
import com.infermc.hosecraft.server.Player;
import com.mojang.minecraft.level.tile.Block;

public class BlockBreakEvent extends Event implements Cancellable {
    // Event Template
    private boolean cancelled = false;

    public void setCancelled(boolean status) {
        this.cancelled = status;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    private Player player;
    private Block block;

    public BlockBreakEvent(Block bk, Player pl) {
        this.player = pl;
        this.block = bk;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Block getBlock() {
        return this.block;
    }
}
