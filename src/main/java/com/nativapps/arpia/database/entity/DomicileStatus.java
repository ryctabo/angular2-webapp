package com.nativapps.arpia.database.entity;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public enum DomicileStatus {

    /**
     * Waiting to be dispatched to a messenger.
     */
    WAITING,
    /**
     * Dispatched to a messenger, they are currently performing the service.s
     */
    DISPATCHED,
    /**
     * The home service to be finalized.
     */
    FINALIZED,
    /**
     * The home service to be cancelled.
     */
    CANCELLED

}
