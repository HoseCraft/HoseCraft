package com.infermc.hosecraft.commands;

import com.infermc.hosecraft.events.chat.CommandSource;
import com.infermc.hosecraft.plugins.Plugin;
import com.infermc.hosecraft.server.Server;

import java.util.*;

public class CommandRegistry {

    private HashMap<Plugin,List<Command>> commands = new HashMap<Plugin, List<Command>>();
    private Server server;

    public CommandRegistry(Server server) {
        this.server = server;

        // Register our own commands.
        this.registerCommand(null,new Command("version",new versionCommand(this.server)).setDescription("Returns the Server version."));
        this.registerCommand(null,new Command("list",new listCommand(this.server)).setDescription("Lists all online players."));
        this.registerCommand(null,new Command("help",new helpCommand(this.server)).setDescription("Lists all known commands."));
        this.registerCommand(null,new Command("pl",new pluginCommand(this.server)).setDescription("Lists all loaded plugins."));
        this.registerCommand(null,new Command("plugins",new pluginCommand(this.server)).setDescription("Lists all loaded plugins."));
    }

    public void registerCommand(Plugin pl,Command cmd) {
        if(cmd==null) return;
        List<Command> list = new ArrayList<Command>();
        if (commands.containsKey(pl)) list = commands.get(pl);
        list.add(cmd);
        commands.put(pl,list);
    }

    // Irrelevant of plugins
    public Command getCommand(String name) {
        for (List<Command> list : commands.values()) {
            for (Command command : list) {
                if (command.getName().equalsIgnoreCase(name)) {
                    return command;
                }
            }
        }
        return null;
    }
    public List<Command> getCommands() {
        List<Command> list = new ArrayList<Command>();
        for (List<Command> cmdlist : commands.values()) {
            list.addAll(cmdlist);
        }
        return list;
    }
    public void runCommand(CommandSource source, String cmd, String[] args) {
        boolean hit = false;
        for (List<Command> list : commands.values()) {
            for (Command command : list) {
                if (command.getName().equalsIgnoreCase(cmd)) {
                    hit = true;
                    boolean res = command.runner(source, args);
                    if (res) return;
                }
            }
        }
        if (!hit) source.sendMessage("Unknown command!");
    }

    // Plugin Specific.
    public Command getCommand(Plugin pl, String name) {
        if (commands.containsKey(pl)) {
            for (Command cmd : commands.get(pl)) {
                if (cmd.getName().equalsIgnoreCase(name)) {
                    return cmd;
                }
            }
        }
        return null;
    }
    public List<Command> getCommands(Plugin pl) {
        if (commands.containsKey(pl)) {
            return commands.get(pl);
        }
        return new ArrayList<Command>();
    }
    public void runCommand(Plugin pl,CommandSource source, String cmd, String[] args) {
        boolean hit = false;
        if (commands.containsKey(pl)) {
            for (Command command : commands.get(pl)) {
                if (command.getName().equalsIgnoreCase(cmd)) {
                    hit = true;
                    boolean res = command.runner(source, args);
                    if (res) return;
                }
            }
        }
        if (!hit) source.sendMessage("Unknown command!");
    }

    public Server getServer() { return this.server; }
}
