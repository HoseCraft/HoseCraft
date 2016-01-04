package com.infermc.hosecraft.events;

// Handles events.
public class HanderList {
    BlockBreakEvent test = new BlockBreakEvent();
    public void testEvent(BlockBreakEvent ev) {
        ev.isCancelled();
    }
    public void nonCancel(PlayerJoinEvent ev) {
       // ev.setCancelled(false);
    }
}
