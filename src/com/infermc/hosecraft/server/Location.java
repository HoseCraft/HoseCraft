package com.infermc.hosecraft.server;

import com.mojang.minecraft.level.Level;

/**
 * Created by Thomas on 03/01/2016.
 */
public class Location {
    private Level lvl;

    private float X;
    private float Y;
    private float Z;

    private float yaw;
    private float pitch;

    public Location(Level l, float x, float y, float z) {
        this.lvl = l;
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    public Location(Level l, float x, float y, float z, float yw, float p) {
        this.lvl = l;
        this.X = x;
        this.Y = y;
        this.Z = z;

        this.yaw = yw;
        this.pitch = p;
    }

    public Location(Level l, int x, int y, int z) {
        this.lvl = l;
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    public Location(Level l, int x, int y, int z, int yw, int p) {
        this.lvl = l;
        this.X = x;
        this.Y = y;
        this.Z = z;

        this.yaw = yw;
        this.pitch = p;
    }

    public float getX() {
        return this.X;
    }
    public float getY() {
        return this.Y;
    }
    public float getZ() {
        return this.Z;
    }
    public float getYaw() { return this.yaw; }
    public float getPitch() { return this.pitch; }

    public void setX(float x) {
        this.X = x;
    }
    public void setY(float y) {
        this.Y = y;
    }
    public void setZ(float z) {
        this.Z = z;
    }
    public void setYaw(float yw) {
        this.yaw = yw;
    }
    public void setPitch(float p) {
        this.pitch = p;
    }
}
