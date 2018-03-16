package com.nativapps.arpia.database.entity;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "YEARLY_REPEAT")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class YearlyRepeat extends Repeat {

    @Column(name = "`EVERY`", nullable = false)
    private int every;

    @Column(name = "DATE_OF_YEAR")
    @Temporal(TemporalType.DATE)
    private Calendar dateOfYear;

    public YearlyRepeat() {
    }

    public YearlyRepeat(int every, Calendar dateOfYear, Date time,
            Calendar startDate, Calendar endDate) {
        super(time, startDate, endDate);
        this.every = every;
        this.dateOfYear = dateOfYear;
    }

    public int getEvery() {
        return every;
    }

    public void setEvery(int every) {
        this.every = every;
    }

    public Calendar getDateOfYear() {
        return dateOfYear;
    }

    public void setDateOfYear(Calendar dateOfYear) {
        this.dateOfYear = dateOfYear;
    }

}
