package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "SETTLEMENT")
public class Settlement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "PRODUCED")
    private float produced;
    
    @Column(name = "CREDITS")
    private float credits;
    
    @Column(name = "ADVANCES")
    private float advances;
    
    @Column(name = "EXPENSES")
    private float expenses;
    
    @OneToOne
    @JoinColumn(name = "CASH_TALLYING_ID")
    private CashTallying cashTallying;

    public Settlement() {
    }

    public Settlement(float produced, float credits, float advances, 
            float expenses) {
        this.produced = produced;
        this.credits = credits;
        this.advances = advances;
        this.expenses = expenses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getProduced() {
        return produced;
    }

    public void setProduced(float produced) {
        this.produced = produced;
    }

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public float getAdvances() {
        return advances;
    }

    public void setAdvances(float advances) {
        this.advances = advances;
    }

    public float getExpenses() {
        return expenses;
    }

    public void setExpenses(float expenses) {
        this.expenses = expenses;
    }

    public CashTallying getCashTallying() {
        return cashTallying;
    }

    public void setCashTallying(CashTallying cashTallying) {
        this.cashTallying = cashTallying;
    }
}
