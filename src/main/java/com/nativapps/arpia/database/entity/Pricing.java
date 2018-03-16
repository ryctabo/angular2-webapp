package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "PRICING")
public class Pricing implements Serializable {

    @EmbeddedId
    private PricingPK id;

    @MapsId("mapPointId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAP_POINT_ID", nullable = false)
    private MapPoint mapPoint;

    @Column(name = "DISTANCE", nullable = false)
    private int distance;

    @Column(name = "PRICE", nullable = false)
    private float price;

    public Pricing() {
        this.id = new PricingPK();
    }

    public Pricing(PricingPK id) {
        this.id = id;
    }

    public Pricing(int distance, float price) {
        this.id = new PricingPK();
        this.distance = distance;
        this.price = price;
    }

    public PricingPK getId() {
        return id;
    }

    public void setId(PricingPK id) {
        this.id = id;
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Embeddable
    public static class PricingPK implements Serializable {

        @Column(name = "`INDEX`", nullable = false)
        private int index;

        @Column(name = "MAP_POINT_ID")
        private long mapPointId;

        public PricingPK() { }

        public PricingPK(int index, long mapPointId) {
            this.index = index;
            this.mapPointId = mapPointId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getMapPointId() {
            return mapPointId;
        }

        public void setMapPointId(long mapPointId) {
            this.mapPointId = mapPointId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PricingPK pricingPK = (PricingPK) o;

            if (index != pricingPK.index) return false;
            return mapPointId == pricingPK.mapPointId;
        }

        @Override
        public int hashCode() {
            int result = index;
            result = 31 * result + (int) (mapPointId ^ (mapPointId >>> 32));
            return result;
        }
    }

}
