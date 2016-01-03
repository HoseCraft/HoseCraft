package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;

import java.util.Random;

public final class StillLiquidBlock extends LiquidBlock {

   protected StillLiquidBlock(int var1, LiquidType var2) {
      super(var1, var2);
      this.af = var1 - 1;
      this.ae = var1;
      this.a(false);
   }

   public final void a(Level var1, int var2, int var3, int var4, Random var5) {}

   public final void a(Level var1, int var2, int var3, int var4, int var5) {
      boolean var6 = false;
      if(var1.getTile(var2 - 1, var3, var4) == 0) {
         var6 = true;
      }

      if(var1.getTile(var2 + 1, var3, var4) == 0) {
         var6 = true;
      }

      if(var1.getTile(var2, var3, var4 - 1) == 0) {
         var6 = true;
      }

      if(var1.getTile(var2, var3, var4 + 1) == 0) {
         var6 = true;
      }

      if(var1.getTile(var2, var3 - 1, var4) == 0) {
         var6 = true;
      }

      if(var5 != 0) {
         LiquidType var7 = Block.blocks[var5].getLiquidType();
         if(this.type == LiquidType.water && var7 == LiquidType.lava || var7 == LiquidType.water && this.type == LiquidType.lava) {
            var1.setTile(var2, var3, var4, Block.STONE.id);
            return;
         }
      }

      if(var6) {
         var1.setTileNoUpdate(var2, var3, var4, this.af);
         var1.addToTickNextTick(var2, var3, var4, this.af);
      }

   }
}
