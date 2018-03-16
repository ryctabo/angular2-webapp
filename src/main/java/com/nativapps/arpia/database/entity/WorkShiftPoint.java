package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "WORK_SHIFT_POINT")
public class WorkShiftPoint implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "MAP_POINT_ID", nullable = false)
    private MapPoint mapPoint;

    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DAT", nullable = false)
    private Calendar startDateAndTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DAT", nullable = false)
    private Calendar endDateAndTime;

    public WorkShiftPoint() { }

    public WorkShiftPoint(MapPoint mapPoint, Messenger messenger,
                          Calendar startDateAndTime, Calendar endDateAndTime) {
        this.mapPoint = mapPoint;
        this.messenger = messenger;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Calendar getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(Calendar startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    public Calendar getEndDateAndTime() {
        return endDateAndTime;
    }

    public void setEndDateAndTime(Calendar endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }
}
