package com.infermc.hosecraft.server;

import com.mojang.minecraft.net.PacketType;
import com.mojang.minecraft.server.HandleClient;

public class Player {
    private Server server;
    private String name;
    private String displayname;
    private Location location;
    private HandleClient socket;

    public Player(Server srv, HandleClient sck, String uname) {
        this.server = srv;
        this.socket = sck;
        this.name = uname;
        this.displayname = uname;
    }

    public String getName() {
        return this.name;
    }
    public String getDisplayname(){
        return this.displayname;
    }
    public Server getServer() {
        return this.server;
    }
    public HandleClient getSocket() {
        return this.socket;
    }
    public Location getLocation() { return this.location; }

    public void setDisplayname(String display) {
        this.displayname = display;
    }
    public void teleport(Location loc) {
        this.location = loc;
    }

    public void sendMessage(String msg) {
        socket.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), msg});
    }
}
