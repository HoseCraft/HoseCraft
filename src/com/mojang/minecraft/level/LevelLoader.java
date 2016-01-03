package com.mojang.minecraft.level;

import com.mojang.minecraft.server.MinecraftServer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class LevelLoader {

   private MinecraftServer a;


   public LevelLoader(MinecraftServer var1) {
      this.a = var1;
   }

   public final Level a(InputStream var1) {
      if(this.a != null) {
         this.a.a("Loading level");
      }

      if(this.a != null) {
         this.a.b("Reading..");
      }

      try {
         DataInputStream var10;
         if((var10 = new DataInputStream(new GZIPInputStream(var1))).readInt() != 656127880) {
            return null;
         } else {
            byte var12;
            if((var12 = var10.readByte()) > 2) {
               return null;
            } else if(var12 <= 1) {
               String var14 = var10.readUTF();
               String var15 = var10.readUTF();
               long var7 = var10.readLong();
               short var3 = var10.readShort();
               short var4 = var10.readShort();
               short var5 = var10.readShort();
               byte[] var6 = new byte[var3 * var4 * var5];
               var10.readFully(var6);
               var10.close();
               Level var11;
               (var11 = new Level()).setData(var3, var5, var4, var6);
               var11.name = var14;
               var11.creator = var15;
               var11.createTime = var7;
               return var11;
            } else {
               Level var2;
               LevelObjectInputStream var13;
               (var2 = (Level)(var13 = new LevelObjectInputStream(var10)).readObject()).initTransient();
               var13.close();
               return var2;
            }
         }
      } catch (Exception var9) {
         var9.printStackTrace();
         this.a.c("Failed to load level: " + var9.toString());
         return null;
      }
   }

   public static void a(Level var0, OutputStream var1) {
      try {
         DataOutputStream var3;
         (var3 = new DataOutputStream(new GZIPOutputStream(var1))).writeInt(656127880);
         var3.writeByte(2);
         ObjectOutputStream var4;
         (var4 = new ObjectOutputStream(var3)).writeObject(var0);
         var4.close();
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }
}
