package com.infermc.hosecraft.events;

import com.infermc.hosecraft.plugins.Plugin;

import java.lang.reflect.Method;

public class EventListener {
    private Method method;
    private Plugin plugin;
    private Listener parentClass;

    public EventListener(Plugin plugin,Method method,Listener parent) {
        this.method = method;
        this.plugin = plugin;
        this.parentClass = parent;
    }

    public Method getMethod() {
        return this.method;
    }
    public Plugin getPlugin() {
        return this.plugin;
    }
    public Listener getListener() { return this.parentClass; }
}
