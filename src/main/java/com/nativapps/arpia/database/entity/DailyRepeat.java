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
@Table(name = "DAILY_REPEAT")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class DailyRepeat extends Repeat {

    @Column(name = "`EVERY`", nullable = false)
    private int every;

    public DailyRepeat() {
    }

    public DailyRepeat(int every, Date time, Calendar startDate, Calendar endDate) {
        super(time, startDate, endDate);
        this.every = every;
    }

    public int getEvery() {
        return every;
    }

    public void setEvery(int every) {
        this.every = every;
    }

}
