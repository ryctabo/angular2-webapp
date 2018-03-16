package com.nativapps.arpia.database.entity;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "WEEKLY_REPEAT")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class WeeklyRepeat extends Repeat {

    @Column(name = "`EVERY`", nullable = false)
    private int every;

    @Column(name = "MONDAY", nullable = false)
    private boolean monday;

    @Column(name = "TUESDAY", nullable = false)
    private boolean tuesday;

    @Column(name = "WEDNESDAY", nullable = false)
    private boolean wednesday;

    @Column(name = "THURSDAY", nullable = false)
    private boolean thursday;

    @Column(name = "FRIDAY", nullable = false)
    private boolean friday;

    @Column(name = "SATURDAY", nullable = false)
    private boolean saturday;

    @Column(name = "SUNDAY", nullable = false)
    private boolean sunday;

    public WeeklyRepeat() {
    }

    public WeeklyRepeat(int every, boolean monday, boolean tuesday,
            boolean wednesday, boolean thursday, boolean friday,
            boolean saturday, boolean sunday, Date time, Calendar startDate,
            Calendar endDate) {
        super(time, startDate, endDate);
        this.every = every;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public int getEvery() {
        return every;
    }

    public void setEvery(int every) {
        this.every = every;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

}
