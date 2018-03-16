package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "SPECIAL_DAY")
public class SpecialDay implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "`DATE`", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar date;

    @Column(name = "TYPE_DAY", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeDay typeDay;

    public SpecialDay() {
    }

    public SpecialDay(Calendar date, TypeDay typeDay) {
        this.date = date;
        this.typeDay = typeDay;
    }

    public SpecialDay(long id, Calendar date, TypeDay typeDay) {
        this.id = id;
        this.date = date;
        this.typeDay = typeDay;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public TypeDay getTypeDay() {
        return typeDay;
    }

    public void setTypeDay(TypeDay typeDay) {
        this.typeDay = typeDay;
    }

}
