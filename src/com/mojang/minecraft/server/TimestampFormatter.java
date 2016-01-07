package com.mojang.minecraft.server;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class TimestampFormatter extends Formatter {

   public final String format(LogRecord var1) {
      String loggerName = var1.getLoggerName();
      Level var3 = var1.getLevel();
      String var2 = "";
      if(var3 == Level.WARNING) {
         var2 = "  [WARN]";
      } else if (var3 == Level.SEVERE) {
         var2 = "  [FATAL]";
      } else if (var3 == Level.INFO) {
         var2 = "  [INFO]";
      }

      String loggerPrefix = "";
      if (loggerName != "MinecraftServer" && loggerName != "HoseCraft") {
         loggerPrefix = "["+loggerName+"]  ";
      }

      return "  "+MinecraftServer.b.format(Long.valueOf(var1.getMillis())) + var2 + "  "+loggerPrefix + var1.getMessage() + "\n";
   }
}
