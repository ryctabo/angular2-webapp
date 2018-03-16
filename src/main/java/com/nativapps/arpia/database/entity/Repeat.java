package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "HOME_SERVICE_REPEAT")
@Inheritance(strategy = InheritanceType.JOINED)
public class Repeat implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "`TIME`", nullable = false)
    @Temporal(TemporalType.TIME)
    protected Date time;

    @Column(name = "START_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    protected Calendar startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    protected Calendar endDate;

    public Repeat() {
    }

    public Repeat(Date time, Calendar startDate, Calendar endDate) {
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

}
