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
@Table(name = "MONTHLY_REPEAT")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class MonthlyRepeat extends Repeat {

    @Column(name = "`EVERY`", nullable = false)
    private int every;

    @Column(name = "DAYS_OF_MONTH", nullable = false)
    private int dayOfMonth;

    public MonthlyRepeat() {
    }

    public MonthlyRepeat(int every, int dayOfMonth, Date time,
            Calendar startDate, Calendar endDate) {
        super(time, startDate, endDate);
        this.every = every;
        this.dayOfMonth = dayOfMonth;
    }

    public int getEvery() {
        return every;
    }

    public void setEvery(int every) {
        this.every = every;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

}
