package com.mojang.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.zip.GZIPOutputStream;

final class ClientStream extends Thread {

   // $FF: synthetic field
   private byte[] a;
   // $FF: synthetic field
   private HandleClient b;


   ClientStream(HandleClient var1, byte[] var2) {
      super();
      this.b = var1;
      this.a = var2;
   }

   public final void run() {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         Thread.sleep(500L);
         ByteArrayOutputStream var3 = var1;
         byte[] var2 = this.a;

         try {
            DataOutputStream var6;
            (var6 = new DataOutputStream(new GZIPOutputStream(var3))).writeInt(var2.length);
            var6.write(var2);
            var6.close();
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }

         Thread.sleep(500L);
         this.b.a(var1.toByteArray());
      } catch (InterruptedException var5) {
         ;
      }
   }
}
