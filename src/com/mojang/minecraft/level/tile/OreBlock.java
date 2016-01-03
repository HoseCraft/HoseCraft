package com.mojang.minecraft.level.tile;

public final class OreBlock extends Block {

   public OreBlock(int var1, int var2) {
      super(var1, var2);
   }

   public final int f() {
      return a.nextInt(3) + 1;
   }
}
