package com.infermc.hosecraft.commands;

import com.infermc.hosecraft.events.chat.CommandSource;

/**
 * Created by Thomas on 05/01/2016.
 */
public interface CommandInterface {
    public boolean run(CommandSource source, String[] args);
}
