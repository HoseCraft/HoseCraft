package com.infermc.hosecraft.logging;

import java.util.logging.*;

public class ServerHandler extends Handler {
    @Override
    public void publish(LogRecord record)
    {
        if (getFormatter() == null)
        {
            setFormatter(new SimpleFormatter());
        }

        try {
            String message = getFormatter().format(record);
            if (record.getLevel().intValue() >= Level.WARNING.intValue())
            {
                System.err.write(message.getBytes());
            }
            else
            {
                System.out.write(message.getBytes());
            }
        } catch (Exception exception) {
            reportError(null, exception, ErrorManager.FORMAT_FAILURE);
        }

    }

    @Override
    public void close() throws SecurityException {}
    @Override
    public void flush(){}
}
