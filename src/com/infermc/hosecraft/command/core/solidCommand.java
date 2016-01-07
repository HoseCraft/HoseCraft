package com.infermc.hosecraft.command.core;

import com.infermc.hosecraft.command.CommandInterface;
import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.server.Player;
import com.infermc.hosecraft.server.Server;

// Toggles the players ability to place bedrock
public class solidCommand implements CommandInterface {
    private Server server;

    public solidCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean run(CommandSource source, String[] args) {
        if (source.isOperator()) {
            if (source instanceof Player) {
                Player p = (Player) source;
                p.getSocket().i = !p.getSocket().i;
                if (p.getSocket().i) {
                    p.sendMessage("Now placing unbreakable stone");
                } else {
                    p.sendMessage("Now placing normal stone");
                }
            } else {
                source.sendMessage("You cannot toggle bedrock placement from the console!");
            }
            return true;
        }
        return false;
    }
}
