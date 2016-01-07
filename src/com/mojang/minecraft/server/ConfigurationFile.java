package com.mojang.minecraft.server;

import com.mojang.minecraft.server.MinecraftServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public final class ConfigurationFile {

   private static Logger a = MinecraftServer.a;
   private String b;
   private File c;
   private Set d = new HashSet();


   public ConfigurationFile(String var1, File var2) {
      this.b = var1;
      this.c = var2;
      this.a();
   }

   // Adds line.
   public final void a(String var1) {
      var1 = var1.toLowerCase();
      this.d.add(var1);
      this.b();
   }

   // Removes line
   public final void b(String var1) {
      var1 = var1.toLowerCase();
      this.d.remove(var1);
      this.b();
   }
   // Checks for the existence
   public final boolean c(String var1) {
      var1 = var1.toLowerCase();
      return this.d.contains(var1);
   }

   private void a() {
      try {
         BufferedReader var1 = new BufferedReader(new FileReader(this.c));
         String var2 = null;

         while((var2 = var1.readLine()) != null) {
            var2 = var2.toLowerCase();
            this.d.add(var2);
         }

         var1.close();
      } catch (IOException var4) {
         try {
            this.c.createNewFile();
         } catch (IOException var3) {
            var3.printStackTrace();
         }

         a.warning("Failed to load player list \"" + this.b + "\". (" + var4 + ")");
      }
   }

   private void b() {
      try {
         PrintWriter var1 = new PrintWriter(new FileWriter(this.c));
         Iterator var2 = this.d.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.println(var3);
         }

         var1.close();
      } catch (IOException var4) {
         a.warning("Failed to save player list \"" + this.b + "\". (" + var4 + ")");
      }
   }

}
