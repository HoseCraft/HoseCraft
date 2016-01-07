package com.infermc.hosecraft.server;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;

public class Location {
    private Level lvl;

    private double X;
    private double Y;
    private double Z;

    private float yaw;
    private float pitch;

    public Location(Level l, double x, double y, double z) {
        this.lvl = l;
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    public Location(Level l, double x, double y, double z, float yw, float p) {
        this.lvl = l;
        this.X = x;
        this.Y = y;
        this.Z = z;

        this.yaw = yw;
        this.pitch = p;
    }

    public double getX() { return this.X/32; }
    public int getBlockX() { return (int) Math.floor(this.X/32); }

    public double getY() { return this.Y/32; }
    public int getBlockY() { return (int) Math.floor(this.Y/32); }

    public double getZ() { return this.Z/32; }
    public int getBlockZ() { return (int) Math.floor(this.Z/32); }

    public float getYaw() { return this.yaw; }
    public float getPitch() { return this.pitch; }

    public Level getLevel() { return this.lvl; }

    public Block getBlock() { return Block.blocks[this.lvl.getTile(this.getBlockX(),this.getBlockY(),this.getBlockZ())]; }
    public void setBlock(Block block) { this.lvl.setTile(this.getBlockX(),this.getBlockY(),this.getBlockZ(),block.id); }

    public Location clone() { return new Location(this.lvl,this.X,this.Y,this.Z,this.yaw,this.pitch); }

    public void setX(double x) { this.X = x; }
    public void setY(double y) { this.Y = y; }
    public void setZ(double z) {
        this.Z = z;
    }

    public void setYaw(float yw) {
        this.yaw = yw;
    }
    public void setPitch(float p) {
        this.pitch = p;
    }

    public void setLevel(Level lvl) { this.lvl = lvl; }
}
