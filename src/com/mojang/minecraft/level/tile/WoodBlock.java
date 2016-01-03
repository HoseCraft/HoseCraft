package com.mojang.minecraft.level.tile;

public final class WoodBlock extends Block {

   protected WoodBlock(int var1) {
      super(17);
   }

   public final int f() {
      return a.nextInt(3) + 3;
   }
}
