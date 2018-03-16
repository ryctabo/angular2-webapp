package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "FESTIVE_DAY")
public class FestiveDay implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, length = 70)
    private String name;

    @Column(name = "DAY_OF_MONTH", nullable = false)
    private int dayOfMonth;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "`MONTH`", nullable = false)
    private Month month;

    public FestiveDay() {
    }

    public FestiveDay(String name, int dayOfMonth, Month month) {
        this.name = name;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
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

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

}
