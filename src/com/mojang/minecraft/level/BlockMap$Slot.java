package com.mojang.minecraft.level;

import com.mojang.minecraft.Entity;

import java.io.Serializable;

class BlockMap$Slot implements Serializable {

   public static final long serialVersionUID = 0L;
   private int xSlot;
   private int ySlot;
   private int zSlot;
   // $FF: synthetic field
   final BlockMap this$0;


   private BlockMap$Slot(BlockMap var1) {
      this.this$0 = var1;
   }

   public BlockMap$Slot init(float var1, float var2, float var3) {
      this.xSlot = (int)(var1 / 16.0F);
      this.ySlot = (int)(var2 / 16.0F);
      this.zSlot = (int)(var3 / 16.0F);
      if(this.xSlot < 0) {
         this.xSlot = 0;
      }

      if(this.ySlot < 0) {
         this.ySlot = 0;
      }

      if(this.zSlot < 0) {
         this.zSlot = 0;
      }

      if(this.xSlot >= BlockMap.access$000(this.this$0)) {
         this.xSlot = BlockMap.access$000(this.this$0) - 1;
      }

      if(this.ySlot >= BlockMap.access$100(this.this$0)) {
         this.ySlot = BlockMap.access$100(this.this$0) - 1;
      }

      if(this.zSlot >= BlockMap.access$200(this.this$0)) {
         this.zSlot = BlockMap.access$200(this.this$0) - 1;
      }

      return this;
   }

   public void add(Entity var1) {
      if(this.xSlot >= 0 && this.ySlot >= 0 && this.zSlot >= 0) {
         this.this$0.entityGrid[(this.zSlot * BlockMap.access$100(this.this$0) + this.ySlot) * BlockMap.access$000(this.this$0) + this.xSlot].add(var1);
      }

   }

   public void remove(Entity var1) {
      if(this.xSlot >= 0 && this.ySlot >= 0 && this.zSlot >= 0) {
         this.this$0.entityGrid[(this.zSlot * BlockMap.access$100(this.this$0) + this.ySlot) * BlockMap.access$000(this.this$0) + this.xSlot].remove(var1);
      }

   }

   // $FF: synthetic method
   BlockMap$Slot(BlockMap var1, EmptyClass var2) {
      this(var1);
   }

   // $FF: synthetic method
   static int access$400(BlockMap$Slot var0) {
      return var0.xSlot;
   }

   // $FF: synthetic method
   static int access$500(BlockMap$Slot var0) {
      return var0.ySlot;
   }

   // $FF: synthetic method
   static int access$600(BlockMap$Slot var0) {
      return var0.zSlot;
   }
}
