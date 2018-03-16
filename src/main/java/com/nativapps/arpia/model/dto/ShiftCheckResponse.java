package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftCheckResponse extends ShiftCheckData {

    private Calendar clockIn;
    
    private Calendar clockOut;
    
    private SimpleMessengerResponse messenger;

    public Calendar getClockIn() {
        return clockIn;
    }

    public void setClockIn(Calendar clockIn) {
        this.clockIn = clockIn;
    }

    public Calendar getClockOut() {
        return clockOut;
    }

    public void setClockOut(Calendar clockOut) {
        this.clockOut = clockOut;
    }

    public SimpleMessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(SimpleMessengerResponse messenger) {
        this.messenger = messenger;
    }
}
