package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class WorkShiftPointResponse extends WorkShiftPointData {

    private Long id;

    private MapPointResponse mapPoint;

    private MessengerResponse messenger;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MapPointResponse getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(MapPointResponse mapPoint) {
        this.mapPoint = mapPoint;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }
}
