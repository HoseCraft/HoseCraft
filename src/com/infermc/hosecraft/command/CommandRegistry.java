package com.infermc.hosecraft.command;

import com.infermc.hosecraft.command.core.*;
import com.infermc.hosecraft.plugins.Plugin;
import com.infermc.hosecraft.server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandRegistry {

    private HashMap<Plugin, List<Command>> commands = new HashMap<Plugin, List<Command>>();
    private Server server;

    public CommandRegistry(Server server) {
        this.server = server;

        // Register our own commands.
        this.registerCommand(null, new Command("version", new VersionCommand(this.server)).setDescription("Returns the Server version."));
        this.registerCommand(null, new Command("list", new ListCommand(this.server)).setDescription("Lists all online players."));
        this.registerCommand(null, new Command("help", new HelpCommand(this.server)).setDescription("Lists all known command."));

        this.registerCommand(null, new Command("pl", new PluginCommand(this.server)).setDescription("Lists all loaded plugins."));
        this.registerCommand(null, new Command("plugins", new PluginCommand(this.server)).setDescription("Lists all loaded plugins."));

        // Register original classic commands.
        this.registerCommand(null, new Command("ban", new BanCommand(this.server)).setDescription("Bans a player from the server."));
        this.registerCommand(null, new Command("kick", new KickCommand(this.server)).setDescription("Kicks a player from the server."));
        this.registerCommand(null, new Command("unban", new UnbanCommand(this.server)).setDescription("Unbans a player from the server."));

        this.registerCommand(null, new Command("op", new OpCommand(this.server)).setDescription("Gives Operator status to a player."));
        this.registerCommand(null, new Command("deop", new DeopCommand(this.server)).setDescription("Takes Operator status from a player."));

        this.registerCommand(null, new Command("solid", new SolidCommand(this.server)).setDescription("Toggles placing bedrock instead of stone."));

        this.registerCommand(null, new Command("broadcast", new BroadcastCommand("broadcast", this.server)).setDescription("Broadcasts a message."));
        this.registerCommand(null, new Command("say", new BroadcastCommand("say", this.server)).setDescription("Broadcasts a message."));

        this.registerCommand(null, new Command("tp", new TpCommand(this.server)).setDescription("Teleports you or a player to another player or position."));
        this.registerCommand(null, new Command("teleport", new TpCommand(this.server)).setDescription("Teleports you or a player to another player or position."));
    }

    public void registerCommand(Plugin pl, Command cmd) {
        if (cmd == null) return;
        List<Command> list = new ArrayList<Command>();
        if (commands.containsKey(pl)) list = commands.get(pl);
        list.add(cmd);
        commands.put(pl, list);
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
                    boolean res = command.runner(source, args);
                    if (res) hit = true;
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

    public void runCommand(Plugin pl, CommandSource source, String cmd, String[] args) {
        boolean hit = false;
        if (commands.containsKey(pl)) {
            for (Command command : commands.get(pl)) {
                if (command.getName().equalsIgnoreCase(cmd)) {
                    boolean res = command.runner(source, args);
                    if (res) return;
                    hit = true;
                }
            }
        }
        if (!hit) source.sendMessage("Unknown command!");
    }

    public Server getServer() {
        return this.server;
    }
}
