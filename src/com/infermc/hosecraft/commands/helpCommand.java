package com.infermc.hosecraft.commands;

import com.infermc.hosecraft.events.chat.CommandSource;
import com.infermc.hosecraft.plugins.Plugin;
import com.infermc.hosecraft.server.Server;
import com.infermc.hosecraft.util.Chat;

public class helpCommand implements CommandInterface {
    private Server server;
    public helpCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        CommandRegistry cmdReg = this.server.getCommandRegistry();
        Plugin pl = null;
        String plname = "HoseCraft";
        if (args.length >= 1) {
            pl = server.getPluginManager().getPlugin(args[0]);
            if (pl == null) {
                source.sendMessage("Unknown plugin name!");
                return true;
            }
            plname = pl.getName();
        }
        source.sendMessage(Chat.YELLOW+"There are "+cmdReg.getCommands().size()+" total known commands.");
        source.sendMessage(Chat.YELLOW+"There are "+cmdReg.getCommands(pl).size()+" "+plname+" commands.");
        for (Command cmd : cmdReg.getCommands(pl)) {
            source.sendMessage(Chat.YELLOW+"/"+cmd.getName()+" - "+cmd.getDescription());
        }
        source.sendMessage(Chat.YELLOW+"Use /help <pluginname> to get commands for a specific plugin.");
        return true;
    }
}
