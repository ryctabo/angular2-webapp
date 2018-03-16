package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class WorkShiftPointRequest extends WorkShiftPointData {

    private Long mapPoint;

    private Long messenger;

    public Long getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(Long mapPoint) {
        this.mapPoint = mapPoint;
    }

    public Long getMessenger() {
        return messenger;
    }

    public void setMessenger(Long messenger) {
        this.messenger = messenger;
    }
}
