package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.server.Location;
import com.infermc.hosecraft.server.Player;
import com.infermc.hosecraft.server.Server;
import com.infermc.hosecraft.util.Chat;

public class tpCommand implements CommandInterface {
    private Server server;

    public tpCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        if (source.isOperator()) {
            if (source instanceof Player) {
                Player src = (Player) source;
                if (args.length > 0 && args.length <= 4) {
                    if (args.length == 1) {
                        // Teleporting to player
                        String dest = args[0];
                        Player p = this.server.getPlayer(dest);
                        if (p != null) {
                            src.teleport(p.getLocation());
                        } else {
                            source.sendMessage("No such player");
                        }
                    } else if (args.length == 2) {
                        // Teleport a player to another player.
                        String srcPlayer = args[0];
                        Player sp = this.server.getPlayer(srcPlayer);
                        String destPlayer = args[1];
                        Player dp = this.server.getPlayer(destPlayer);
                        if (dp != null && sp != null) {
                            sp.teleport(dp.getLocation());
                        } else {
                            if (dp == null) source.sendMessage("No such player "+destPlayer);
                            if (sp == null) source.sendMessage("No such player "+srcPlayer);
                        }
                    } else if (args.length == 3) {
                        // Teleport to XYZ
                        double x = Double.valueOf(args[0]);
                        double y = Double.valueOf(args[1]);
                        double z = Double.valueOf(args[2]);
                        Location destination = new Location(src.getLocation().getLevel(),x,y,z);
                        src.teleport(destination);
                    } else if (args.length == 4) {
                        // Teleport a player to an XYZ.
                        Player subject = this.server.getPlayer(args[0]);
                        double x = Double.valueOf(args[1]);
                        double y = Double.valueOf(args[2]);
                        double z = Double.valueOf(args[3]);

                        if (subject != null) {
                            Location destination = new Location(src.getLocation().getLevel(), x, y, z);
                            subject.teleport(destination);
                        }
                    }
                } else {
                    source.sendMessage(Chat.YELLOW+"Syntax: /teleport <player|X> [destPlayer|destY|destX] [destZ|destY] [destZ]");
                }
            } else {
                source.sendMessage("You can't teleport from the console!");
            }
            return true;
        }
        return false;
    }
}
