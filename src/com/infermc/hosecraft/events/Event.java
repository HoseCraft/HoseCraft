package com.infermc.hosecraft.events;

public abstract class Event {

    /**
     * Gets the Event's name
     *
     * @return name
     */
    private String getName() {
        return this.getClass().getSimpleName();
    }
}
