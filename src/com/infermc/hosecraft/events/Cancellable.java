package com.infermc.hosecraft.events;

public interface Cancellable {

    // Event Template
    boolean cancelled = false;

    /*
    * Set plugin cancel state
    * @param
    * State of Event
     */
    void setCancelled(boolean status);

    /*
    * Check if the event has already been cancelled
    * @return
    * Current State of the Event
    */
    boolean isCancelled();
}