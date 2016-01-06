package com.infermc.hosecraft.events.chat;

import com.infermc.hosecraft.events.Cancellable;
import com.infermc.hosecraft.events.Event;

public class CommandEvent extends Event implements Cancellable {
    private boolean cancelled = false;
    public void setCancelled(boolean status) {
        this.cancelled = status;
    }
    public boolean isCancelled() {
        return this.cancelled;
    }

    private String command;
    private String[] args;
    private CommandSource source;

    public CommandEvent(CommandSource src,String cmd,String[] args) {
        this.source = src;
        this.command = cmd;
        this.args = args;
    }

    public String getCommand() {
        return this.command;
    }
    public String[] getArgs() {
        return this.args;
    }
    public CommandSource getSource(){
        return this.source;
    }
}
