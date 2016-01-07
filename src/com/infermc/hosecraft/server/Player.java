package com.infermc.hosecraft.server;

import com.infermc.hosecraft.command.CommandSource;
import com.mojang.minecraft.net.PacketType;
import com.mojang.minecraft.server.HandleClient;

public class Player implements CommandSource {
    private Server server;
    private String name;
    private String displayname;
    private Location location;
    private HandleClient socket;
    private boolean supportsExt;

    public Player(Server srv, HandleClient sck, String uname, boolean supportsExt) {
        this.server = srv;
        this.socket = sck;
        this.name = uname;
        this.displayname = uname;
        this.supportsExt = supportsExt;
    }

    public boolean supportsCPE() {
        return this.supportsExt;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public Server getServer() {
        return this.server;
    }

    public HandleClient getSocket() {
        return this.socket;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isOperator() {
        return this.server.MC.g.c(this.name);
    }

    public void setDisplayname(String display) {
        this.displayname = display;
    }

    public void teleport(Location dest) {
        // XYZ, Yaw, Pitch
        if (dest == null) {
            System.out.println("Dest Location is null");
            return;
        }
        if (this.location == null) {
            this.location = dest;
            return;
        }

        // Change their Location
        this.location = dest;


        // Tell the player themselves.
        int X = (int) (this.location.getX() * 32);
        int Y = (int) (this.location.getY() * 32) + 51;
        int Z = (int) (this.location.getZ() * 32);

        socket.b(PacketType.POSITION_ROTATION, new Object[]{Integer.valueOf(-1), Integer.valueOf(X), Integer.valueOf(Y), Integer.valueOf(Z), Byte.valueOf((byte) dest.getYaw()), Byte.valueOf((byte) dest.getPitch())});
        this.server.MC.a(this.getSocket(), PacketType.POSITION_ROTATION, new Object[]{Integer.valueOf(this.socket.c), Integer.valueOf(X), Integer.valueOf(Y), Integer.valueOf(Z), Byte.valueOf((byte) dest.getYaw()), Byte.valueOf((byte) dest.getPitch())});
    }

    public void sendMessage(String msg) {
        socket.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), msg});
    }

    public boolean hasPermission(String permission) {
        return this.server.getPermissionProvider().hasPermission(this, permission);
    }

    public boolean isBanned() {
        return this.server.isBanned(this.name);
    }

    public boolean ban() {
        return this.server.ban(this.name, null);
    }

    public boolean ban(String reason) {
        return this.server.ban(this.name, reason);
    }

    public boolean kick() {
        return this.server.kick(this.name, null);
    }

    public boolean kick(String reason) {
        return this.server.kick(this.name, reason);
    }
}
