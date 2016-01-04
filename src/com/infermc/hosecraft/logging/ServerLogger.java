package com.infermc.hosecraft.logging;

import com.mojang.minecraft.server.TimestampFormatter;

import java.io.IOException;
import java.util.logging.*;

public class ServerLogger {
    private Logger logger;
    private FileHandler fh = null;

    public ServerLogger() {
        TimestampFormatter formatter = new TimestampFormatter();

        try {
            fh = new FileHandler("latest.log",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerHandler ch = new ServerHandler();

        fh.setLevel(Level.ALL);
        fh.setFormatter(formatter);

        ch.setLevel(Level.ALL);
        ch.setFormatter(formatter);

        this.logger = createLogger("HoseCraft");
    }

    public Logger createLogger(String name) {
        Logger l = Logger.getLogger(name);
        if (fh != null) l.addHandler(this.fh);
        return l;
    }

    public Logger getLogger() {
        return this.logger;
    }
}
