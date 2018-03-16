package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class MessengerPlanningResponse {

    private MessengerResponse messenger;
    
    private ShiftResponse shift;

    public MessengerPlanningResponse() {
    }

    public MessengerPlanningResponse(MessengerResponse messenger, ShiftResponse shift) {
        this.messenger = messenger;
        this.shift = shift;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }

    public ShiftResponse getShift() {
        return shift;
    }

    public void setShift(ShiftResponse shift) {
        this.shift = shift;
    }
}
