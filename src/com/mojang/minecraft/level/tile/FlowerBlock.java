package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.phys.AABB;

import java.util.Random;

public class FlowerBlock extends Block {

    protected FlowerBlock(int var1, int var2) {
        super(var1);
        this.a(true);
        float var3 = 0.2F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, var3 + 0.5F, var3 * 3.0F, var3 + 0.5F);
    }

    public void a(Level var1, int var2, int var3, int var4, Random var5) {
        int var6 = var1.getTile(var2, var3 - 1, var4);
        if (!var1.isLit(var2, var3, var4) || var6 != DIRT.id && var6 != GRASS.id) {
            var1.setTile(var2, var3, var4, 0);
        }

    }

    public final AABB a(int var1, int var2, int var3) {
        return null;
    }

    public final boolean b() {
        return false;
    }

    public final boolean c() {
        return false;
    }

    public final boolean a() {
        return false;
    }
}
