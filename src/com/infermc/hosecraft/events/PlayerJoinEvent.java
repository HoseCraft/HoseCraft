package com.infermc.hosecraft.events;

import com.infermc.hosecraft.server.Player;

public final class PlayerJoinEvent extends Event {
    public PlayerJoinEvent(Player player) {
        System.out.println(player.getName()+" joined us! WHOOP!");
        player.sendMessage("&cWelcome!");
    }
}
