package com.infermc.hosecraft.logging;

import com.mojang.minecraft.server.TimestampFormatter;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLogger {
    private Logger logger;
    private FileHandler fh = null;
    private ConsoleHandler ch = null;
    private TimestampFormatter formatter = new TimestampFormatter();

    public ServerLogger() {
        File logDir = new File("logs/");
        if (!logDir.exists()) logDir.mkdirs();
        try {
            fh = new FileHandler("logs/latest.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fh != null) {
            fh.setLevel(Level.ALL);
            fh.setFormatter(formatter);
        }

        ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        ch.setFormatter(formatter);

        this.logger = createLogger("HoseCraft");
    }

    public Logger createLogger(String name) {
        Logger l = Logger.getLogger(name);
        if (fh != null) l.addHandler(this.fh);
        if (ch != null) l.addHandler(this.ch);
        return l;
    }

    public Logger getLogger() {
        return this.logger;
    }
}
