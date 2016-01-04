package com.infermc.hosecraft.events;

public class BlockBreakEvent extends Event implements Cancellable {
    // Event Template
    private boolean cancelled = false;

    public void setCancelled(boolean status) {
        this.cancelled=status;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

}