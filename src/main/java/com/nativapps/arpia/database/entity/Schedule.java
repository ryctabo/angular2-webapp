package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "SCHEDULE")
public class Schedule implements Serializable {

    @EmbeddedId
    private SchedulePK id;

    @MapsId("mapPointId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAP_POINT_ID", nullable = false)
    private MapPoint mapPoint;

    @Temporal(TemporalType.TIME)
    @Column(name = "OPENING", nullable = false)
    private Date opening;

    @Temporal(TemporalType.TIME)
    @Column(name = "CLOSING", nullable = false)
    private Date closing;

    @Temporal(TemporalType.TIME)
    @Column(name = "BREAK_OPENING")
    private Date breakOpening;

    @Temporal(TemporalType.TIME)
    @Column(name = "BREAK_CLOSING")
    private Date breakClosing;

    public Schedule() {
        this.id = new SchedulePK();
    }

    public Schedule(SchedulePK id) {
        this.id = id;
    }

    public Schedule(Date opening, Date closing, Date breakOpening, Date breakClosing) {
        this.id = new SchedulePK();
        this.opening = opening;
        this.closing = closing;
        this.breakOpening = breakOpening;
        this.breakClosing = breakClosing;
    }

    public SchedulePK getId() {
        return id;
    }

    public void setId(SchedulePK id) {
        this.id = id;
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
    }

    public Date getOpening() {
        return opening;
    }

    public void setOpening(Date opening) {
        this.opening = opening;
    }

    public Date getClosing() {
        return closing;
    }

    public void setClosing(Date closing) {
        this.closing = closing;
    }

    public Date getBreakOpening() {
        return breakOpening;
    }

    public void setBreakOpening(Date breakOpening) {
        this.breakOpening = breakOpening;
    }

    public Date getBreakClosing() {
        return breakClosing;
    }

    public void setBreakClosing(Date breakClosing) {
        this.breakClosing = breakClosing;
    }

    @Embeddable
    public static class SchedulePK implements Serializable {

        @Column(name = "WEEKDAY", nullable = false)
        @Enumerated(EnumType.ORDINAL)
        private Weekdays weekday;

        @Column(name = "MAP_POINT_ID")
        private long mapPointId;

        public SchedulePK() {
        }

        public SchedulePK(Weekdays weekday, long mapPointId) {
            this.weekday = weekday;
            this.mapPointId = mapPointId;
        }

        public Weekdays getWeekday() {
            return weekday;
        }

        public void setWeekday(Weekdays weekday) {
            this.weekday = weekday;
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

            SchedulePK that = (SchedulePK) o;

            if (mapPointId != that.mapPointId) return false;
            return weekday == that.weekday;
        }

        @Override
        public int hashCode() {
            int result = weekday != null ? weekday.hashCode() : 0;
            result = 31 * result + (int) (mapPointId ^ (mapPointId >>> 32));
            return result;
        }
    }

}
