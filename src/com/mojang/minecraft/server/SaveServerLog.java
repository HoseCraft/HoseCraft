package com.mojang.minecraft.server;

import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

final class SaveServerLog extends StreamHandler {

    SaveServerLog(OutputStream var1, Formatter var2) {
        super(var1, var2);
    }

    public final synchronized void publish(LogRecord var1) {
        super.publish(var1);
        this.flush();
    }
}
