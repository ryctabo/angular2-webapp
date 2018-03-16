package com.nativapps.arpia.database.entity;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class MessengerPlanning {

    private Messenger messenger;
    
    private Shift shift;

    public MessengerPlanning() {
    }

    public MessengerPlanning(Messenger messenger, Shift shift) {
        this.messenger = messenger;
        this.shift = shift;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
