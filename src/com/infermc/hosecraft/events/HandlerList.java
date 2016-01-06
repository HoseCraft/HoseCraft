package com.infermc.hosecraft.events;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

// Handles events.
public class HandlerList {
    private List<EventListener> listeners = new ArrayList<EventListener>();

    public void addListener(EventListener l) {
        listeners.add(l);
    }
    public boolean removeListener(EventListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
            return true;
        }
        return false;
    }
    public List<EventListener> getListeners() {
        return listeners;
    }
}
