package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "FEE_INFO")
public class FeeInfo implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "FIRST_PAYMENT", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar firstPayment;

    @Column(name = "NUMBER_OF_FEES", nullable = false)
    private int numberOfFees;

    @Column(name = "PERIOD", nullable = false)
    @Enumerated(EnumType.STRING)
    private Period period;

    public FeeInfo() { }

    public FeeInfo(Calendar firstPayment, int numberOfFees, Period period) {
        this.firstPayment = firstPayment;
        this.numberOfFees = numberOfFees;
        this.period = period;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getFirstPayment() {
        return firstPayment;
    }

    public void setFirstPayment(Calendar firstPayment) {
        this.firstPayment = firstPayment;
    }

    public int getNumberOfFees() {
        return numberOfFees;
    }

    public void setNumberOfFees(int numberOfFees) {
        this.numberOfFees = numberOfFees;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
