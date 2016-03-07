package com.infermc.hosecraft.events.player;

import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.events.Cancellable;
import com.infermc.hosecraft.events.Event;
import com.infermc.hosecraft.server.Player;

public class PlayerOppedEvent extends Event implements Cancellable {
    // Event Template
    private boolean cancelled = false;

    public void setCancelled(boolean status) {
        this.cancelled = status;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }


    private String pl;
    private CommandSource source;

    public PlayerOppedEvent(String pl, CommandSource source) {
        this.pl = pl;
        this.source = source;
    }

    public String getUsername() {
        return this.pl;
    }
    public CommandSource getSource() {
        return this.source;
    }
}
