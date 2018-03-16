package com.nativapps.arpia.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class LatLngData {

    private Float latitude;

    private Float longitude;

    public LatLngData() {
    }

    public LatLngData(Float latitude, Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
