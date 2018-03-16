package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "MAP_POINT")
public class MapPoint implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COORDINATES_ID", nullable = false)
    private LatLng coordinates;

    @Column(name = "`COVERAGE`")
    private int coverage;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MapPointType type;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "mapPoint")
    private Set<Schedule> schedules;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "mapPoint")
    private Set<Pricing> pricings;

    public MapPoint() {
        this.schedules = new HashSet<>();
        this.pricings = new HashSet<>();
    }

    public MapPoint(String name, LatLng coordinates, int coverage, MapPointType type) {
        this.name = name;
        this.coordinates = coordinates;
        this.coverage = coverage;
        this.type = type;
        this.schedules = new HashSet<>();
        this.pricings = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }

    public MapPointType getType() {
        return type;
    }

    public void setType(MapPointType type) {
        this.type = type;
    }

    public void addSchedule(Schedule schedule) {
        if (schedule == null)
            return;
        if (schedule.getMapPoint() != this)
            schedule.setMapPoint(this);
        schedules.add(schedule);
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void addPricing(Pricing pricing) {
        if (pricing == null)
            return;
        if (pricing.getMapPoint() != this)
            pricing.setMapPoint(this);
        this.pricings.add(pricing);
    }

    public Set<Pricing> getPricings() {
        return pricings;
    }

    public void setPricings(Set<Pricing> pricings) {
        this.pricings = pricings;
    }
}
