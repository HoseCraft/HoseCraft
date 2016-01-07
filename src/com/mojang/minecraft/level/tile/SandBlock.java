package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;

public final class SandBlock extends Block {

    public SandBlock(int var1, int var2) {
        super(var1, var2);
    }

    public final void a(Level var1, int var2, int var3, int var4) {
        this.d(var1, var2, var3, var4);
    }

    public final void a(Level var1, int var2, int var3, int var4, int var5) {
        this.d(var1, var2, var3, var4);
    }

    private void d(Level var1, int var2, int var3, int var4) {
        int var11 = var2;
        int var5 = var3;
        int var6 = var4;

        while (true) {
            int var9 = var5 - 1;
            int var7;
            LiquidType var12;
            if (!((var7 = var1.getTile(var11, var9, var6)) == 0 ? true : ((var12 = blocks[var7].getLiquidType()) == LiquidType.water ? true : var12 == LiquidType.lava)) || var5 <= 0) {
                if (var5 != var3) {
                    if ((var7 = var1.getTile(var11, var5, var6)) > 0 && blocks[var7].getLiquidType() != LiquidType.notLiquid) {
                        var1.setTileNoUpdate(var11, var5, var6, 0);
                    }

                    var1.swap(var2, var3, var4, var11, var5, var6);
                }

                return;
            }

            --var5;
        }
    }
}
