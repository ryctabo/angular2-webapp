package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "LAT_LNG")
public class LatLng implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "LATITUDE", nullable = false)
    private float latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private float longitude;

    public LatLng() {
    }

    public LatLng(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LatLng(long id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
