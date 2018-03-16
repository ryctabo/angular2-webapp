package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
@Entity
@Table(name = "LOCATION")
public class Location implements Serializable {

    @EmbeddedId
    private LocationPK id;

    @MapsId("domicileId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOME_SERVICE_ID")
    private Domicile domicile;

    @Column(name = "ADDRESS", nullable = false, length = 30)
    private String address;

    @ManyToOne
    @JoinColumn(name = "NEIGHBORHOOD_ID")
    private Neighborhood neighborhood;

    @Column(name = "REFERENCE", length = 200)
    private String reference;

    @OneToOne
    @JoinColumn(name = "COORDINATES_ID")
    private LatLng coordinates;

    @Column(name = "TASK", columnDefinition = "TEXT", nullable = false)
    private String task;

    public Location() {
        this.id = new LocationPK();
    }

    public LocationPK getId() {
        return id;
    }

    public void setId(LocationPK id) {
        this.id = id;
    }

    public Domicile getDomicile() {
        return domicile;
    }

    public void setDomicile(Domicile domicile) {
        this.domicile = domicile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Embeddable
    public static class LocationPK implements Serializable {

        @Column(name = "`INDEX`", nullable = false)
        private int index;

        @Column(name = "HOME_SERVICE_ID")
        private long domicileId;

        public LocationPK() {
        }

        public LocationPK(int index, long domicileId) {
            this.index = index;
            this.domicileId = domicileId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getDomicileId() {
            return domicileId;
        }

        public void setDomicileId(long domicileId) {
            this.domicileId = domicileId;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 29 * hash + this.index;
            hash = 29 * hash + (int) (this.domicileId ^ (this.domicileId >>> 32));
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final LocationPK other = (LocationPK) obj;
            if (this.index != other.index)
                return false;
            return this.domicileId == other.domicileId;
        }

    }

}
