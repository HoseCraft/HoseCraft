package com.mojang.minecraft.server;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class TimestampFormatter extends Formatter {

   public final String format(LogRecord var1) {
      Level var3 = var1.getLevel();
      String var2 = "   ";
      if(var3 == Level.WARNING) {
         var2 = "  !";
      }

      if(var3 == Level.SEVERE) {
         var2 = "***";
      }

      return var2 + "  " + MinecraftServer.b.format(Long.valueOf(var1.getMillis())) + "  " + var1.getMessage() + "\n";
   }
}
