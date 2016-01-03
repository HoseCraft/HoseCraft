package com.mojang.minecraft.net;


public final class PacketBuilder {

   public static final com.mojang.minecraft.net.PacketBuilder[] PacketBuilder = new PacketBuilder[256];
   public static final com.mojang.minecraft.net.PacketBuilder b = new PacketBuilder(new Class[]{Byte.TYPE, String.class, String.class, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder c = new PacketBuilder(new Class[0]);
   public static final com.mojang.minecraft.net.PacketBuilder d = new PacketBuilder(new Class[0]);
   public static final com.mojang.minecraft.net.PacketBuilder e = new PacketBuilder(new Class[]{Short.TYPE, byte[].class, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder f = new PacketBuilder(new Class[]{Short.TYPE, Short.TYPE, Short.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder g = new PacketBuilder(new Class[]{Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder h = new PacketBuilder(new Class[]{Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder i = new PacketBuilder(new Class[]{Byte.TYPE, String.class, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder j = new PacketBuilder(new Class[]{Byte.TYPE, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder k = new PacketBuilder(new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder l = new PacketBuilder(new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder m = new PacketBuilder(new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder n = new PacketBuilder(new Class[]{Byte.TYPE});
   public static final com.mojang.minecraft.net.PacketBuilder o = new PacketBuilder(new Class[]{Byte.TYPE, String.class});
   public static final com.mojang.minecraft.net.PacketBuilder p = new PacketBuilder(new Class[]{String.class});
   public static final com.mojang.minecraft.net.PacketBuilder q = new PacketBuilder(new Class[]{Byte.TYPE});
   public final int r;
   private static int u = 0;
   public final byte s;
   public Class[] t;


   private PacketBuilder(Class ... var1) {
      this.s = (byte)(u++);
      PacketBuilder[this.s] = this;
      this.t = new Class[var1.length];
      int var2 = 0;

      for(int var3 = 0; var3 < var1.length; ++var3) {
         Class var4 = var1[var3];
         this.t[var3] = var4;
         if(var4 == Long.TYPE) {
            var2 += 8;
         } else if(var4 == Integer.TYPE) {
            var2 += 4;
         } else if(var4 == Short.TYPE) {
            var2 += 2;
         } else if(var4 == Byte.TYPE) {
            ++var2;
         } else if(var4 == Float.TYPE) {
            var2 += 4;
         } else if(var4 == Double.TYPE) {
            var2 += 8;
         } else if(var4 == byte[].class) {
            var2 += 1024;
         } else if(var4 == String.class) {
            var2 += 64;
         }
      }

      this.r = var2;
   }

}
