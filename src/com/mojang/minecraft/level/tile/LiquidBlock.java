package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;
import com.mojang.minecraft.phys.AABB;

import java.util.Random;

public class LiquidBlock extends Block {

    protected LiquidType type;
    protected int ae;
    protected int af;


    protected LiquidBlock(int id, LiquidType liquidType) {
        super(id);
        this.type = liquidType;

        Block.liquid[id] = true;

        // MovingID
        this.af = id;

        // Stillid
        this.ae = id + 1;

        // Bounds stuff.
        float var4 = 0.01F;
        float var3 = 0.1F;

        // Bounds
        this.a(var4 + 0.0F, 0.0F - var3 + var4, var4 + 0.0F, var4 + 1.0F, 1.0F - var3 + var4, var4 + 1.0F);
        // Physics?
        this.a(true);

        // Unsure on this one.
        if (liquidType == LiquidType.lava) {
            //this.Block(16);
        }

    }

    public final boolean a() {
        return false;
    }

    public final void a(Level var1, int var2, int var3, int var4) {
        var1.addToTickNextTick(var2, var3, var4, this.af);
    }

    // Update
    public void a(Level var1, int var2, int var3, int var4, Random var5) {
        boolean var8 = false;

        boolean var6;
        do {
            --var3;
            if (var1.getTile(var2, var3, var4) != 0 || !this.d(var1, var2, var3, var4)) {
                break;
            }

            if (var6 = var1.setTile(var2, var3, var4, this.af)) {
                var8 = true;
            }
        } while (var6 && this.type != LiquidType.lava);

        ++var3;
        if (this.type == LiquidType.water || !var8) {
            var8 = var8 | this.e(var1, var2 - 1, var3, var4) | this.e(var1, var2 + 1, var3, var4) | this.e(var1, var2, var3, var4 - 1) | this.e(var1, var2, var3, var4 + 1);
        }

        if (!var8) {
            var1.setTileNoUpdate(var2, var3, var4, this.ae);
        } else {
            var1.addToTickNextTick(var2, var3, var4, this.af);
        }

    }

    // canFlow
    private boolean d(Level var1, int var2, int var3, int var4) {
        if (this.type == LiquidType.water) {
            for (int var7 = var2 - 2; var7 <= var2 + 2; ++var7) {
                for (int var5 = var3 - 2; var5 <= var3 + 2; ++var5) {
                    for (int var6 = var4 - 2; var6 <= var4 + 2; ++var6) {
                        if (var1.getTile(var7, var5, var6) == Block.SPONGE.id) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    // flow
    private boolean e(Level var1, int var2, int var3, int var4) {
        if (var1.getTile(var2, var3, var4) == 0) {
            if (!this.d(var1, var2, var3, var4)) {
                return false;
            }

            if (var1.setTile(var2, var3, var4, this.af)) {
                var1.addToTickNextTick(var2, var3, var4, this.af);
            }
        }

        return false;
    }

    public final AABB a(int var1, int var2, int var3) {
        return null;
    }

    public final boolean b() {
        return true;
    }

    public final boolean c() {
        return false;
    }

    public final LiquidType getLiquidType() {
        return this.type;
    }

    public void a(Level var1, int var2, int var3, int var4, int var5) {
        if (var5 != 0) {
            LiquidType var6 = Block.blocks[var5].getLiquidType();
            if (this.type == LiquidType.water && var6 == LiquidType.lava || var6 == LiquidType.water && this.type == LiquidType.lava) {
                var1.setTile(var2, var3, var4, Block.OBSIDIAN.id);
                return;
            }
        }

        var1.addToTickNextTick(var2, var3, var4, var5);
    }

    public final int e() {
        return this.type == LiquidType.lava ? 5 : 0;
    }

    public final void a(Level var1, float var2) {
    }

    public final int f() {
        return 0;
    }
}
