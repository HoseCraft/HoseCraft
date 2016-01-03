package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;

import java.util.Random;

public final class SaplingBlock extends FlowerBlock {

   protected SaplingBlock(int var1, int var2) {
      super(6, 15);
      float var3 = 0.4F;
      this.a(0.5F - var3, 0.0F, 0.5F - var3, var3 + 0.5F, var3 * 2.0F, var3 + 0.5F);
   }

   public final void a(Level var1, int var2, int var3, int var4, Random var5) {
      if(var1.growTrees) {
         int var6 = var1.getTile(var2, var3 - 1, var4);
         if(var1.isLit(var2, var3, var4) && (var6 == DIRT.id || var6 == GRASS.id)) {
            if(var5.nextInt(5) == 0) {
               var1.setTileNoUpdate(var2, var3, var4, 0);
               if(!var1.maybeGrowTree(var2, var3, var4)) {
                  var1.setTileNoUpdate(var2, var3, var4, this.id);
               }
            }

         } else {
            var1.setTile(var2, var3, var4, 0);
         }
      }
   }
}
