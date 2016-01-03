package com.mojang.minecraft.server;

import com.mojang.minecraft.server.MinecraftServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

final class ConsoleInput extends Thread {

   // $FF: synthetic field
   private MinecraftServer a;


   ConsoleInput(MinecraftServer var1) {
      super();
      this.a = var1;
   }

   public final void run() {
      try {
         BufferedReader var1 = new BufferedReader(new InputStreamReader(System.in));
         String var2 = null;

         while((var2 = var1.readLine()) != null) {
            synchronized(MinecraftServer.a(this.a)) {
               MinecraftServer.a(this.a).add(var2);
            }
         }

         MinecraftServer.a.warning("stdin: end of file! No more direct console input is possible.");
      } catch (IOException var5) {
         MinecraftServer.a.warning("stdin: ioexception! No more direct console input is possible.");
         var5.printStackTrace();
      }
   }
}
