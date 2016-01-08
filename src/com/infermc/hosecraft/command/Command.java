package com.infermc.hosecraft.command;

public class Command {
    private String name;
    private String description;
    private CommandInterface listener;

    public Command(String name) {
        this.name = name;
    }

    public Command(String name, CommandInterface listener) {
        this.name = name;
        this.listener = listener;
    }

    public boolean runner(CommandSource source, String[] args) {
        if (listener != null) return listener.run(source, args);
        return false;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Command setDescription(String desc) {
        this.description = desc;
        return this;
    }

    public void setListener(CommandInterface listener) {
        this.listener = listener;
    }

    public CommandInterface getListener() {
        return this.listener;
    }
}
