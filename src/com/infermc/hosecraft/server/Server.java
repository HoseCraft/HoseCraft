package com.infermc.hosecraft.server;

import com.infermc.hosecraft.command.CommandRegistry;
import com.infermc.hosecraft.events.player.PlayerBannedEvent;
import com.infermc.hosecraft.events.player.PlayerKickEvent;
import com.infermc.hosecraft.logging.ServerLogger;
import com.infermc.hosecraft.permissions.PermissionProvider;
import com.infermc.hosecraft.plugins.PluginManager;
import com.infermc.hosecraft.wrappers.ConfigSection;
import com.infermc.hosecraft.wrappers.YamlConfiguration;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.generator.ClassicGenerator;
import com.mojang.minecraft.level.generator.LevelGenerator;
import com.mojang.minecraft.server.MinecraftServer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Server {
    public PluginManager pluginManager = new PluginManager(this);
    public Logger log;
    public MinecraftServer MC;
    public ArrayList<Player> players = new ArrayList<Player>();
    public ArrayList<LevelGenerator> levelGenerators = new ArrayList<LevelGenerator>();
    public ServerLogger loggerManager = new ServerLogger();
    private PermissionProvider permissionProvider = new PermissionProvider();
    private CommandRegistry commandRegistry = new CommandRegistry(this);

    public Double version;
    public String flavour;

    public Server(MinecraftServer instance) {
        MC = instance;
        log = loggerManager.getLogger();

        // Load hosecraft stuff
        InputStream input = getClass().getResourceAsStream("/hosecraft.yml");
        YamlConfiguration hosecraft = new YamlConfiguration();
        if (input != null) {
            hosecraft.load(input);
        }
        ConfigSection section = hosecraft.getRoot();
        this.version = (Double) section.get("version", 0.0);
        this.flavour = section.getString("flavour", "MISSINGNO");

        // Add some internal level generators.
        this.registerLevelGenerator(new ClassicGenerator(instance));

    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public Logger getLogger() {
        return log;
    }

    public Double getVersion() {
        return this.version;
    }

    public String getFlavour() {
        return this.flavour;
    }

    public PermissionProvider getPermissionProvider() {
        return this.permissionProvider;
    }

    public void setPermissionProvider(PermissionProvider permissionProvider) {
        this.permissionProvider = permissionProvider;
    }

    public CommandRegistry getCommandRegistry() {
        return this.commandRegistry;
    }

    public boolean isOnline(String name) {
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public boolean isOnline(Player player) {
        return players.contains(player);
    }

    public Player getPlayer(String name) {
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public boolean isBanned(String name) {
        return this.MC.h.c(name);
    }

    public boolean isBanned(Player player) {
        return isBanned(player.getName());
    }

    public boolean ban(Player p) {
        return ban(p.getName());
    }

    public boolean ban(String name) {
        return MC.ban(name, null);
    }

    public boolean unban(Player p) {
        return unban(p.getName());
    }

    public boolean unban(String name) {
        if (this.MC.h.c(name)) {
            this.MC.h.b(name);
            return true;
        }
        return false;
    }

    public boolean ban(Player p, String reason) {
        return ban(p.getName(), reason);
    }

    public boolean ban(String name, String reason) {
        PlayerBannedEvent ev = new PlayerBannedEvent(this.getPlayer(name), reason);
        this.getPluginManager().callEvent(ev);
        if (ev.isCancelled()) return false;
        reason = ev.getReason();

        return MC.ban(name, reason);
    }

    public boolean kick(Player p) {
        return kick(p.getName());
    }

    public boolean kick(String name) {
        return this.kick(name, null);
    }

    public boolean kick(Player p, String reason) {
        return kick(p.getName(), reason);
    }

    public boolean kick(String name, String reason) {
        PlayerKickEvent ev = new PlayerKickEvent(this.getPlayer(name), reason);
        this.getPluginManager().callEvent(ev);
        if (ev.isCancelled()) return false;
        reason = ev.getReason();

        return MC.kick(name, reason);
    }

    public void registerLevelGenerator(LevelGenerator lg) {
        this.levelGenerators.add(lg);
    }
    public LevelGenerator getLevelGenerator(String generator) {
        for (LevelGenerator lg : this.levelGenerators) {
            if (lg.getClass().getSimpleName().equalsIgnoreCase(generator)) return lg;
        }
        return null;
    }
}
