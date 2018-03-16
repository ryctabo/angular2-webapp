package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "DAILY_OPERATION")
@NamedQueries({
    @NamedQuery(name = "dailyOp.findByOperationId", 
            query = "SELECT d FROM DailyOperation d "
                    + "WHERE d.operation.id = :operationId AND d.id = :id")
})
public class DailyOperation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "OPENING_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar openingDate;
    
    @Column(name = "CLOSING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar closingDate;
    
    @Column(name = "PRODUCED")
    private float produced;
    
    @Column(name = "ADVANCES")
    private float advances;
    
    @Column(name = "CREDITS")
    private float credits;
    
    @Column(name = "EGRESS")
    private float egress;
    
    @Column(name = "CASH_EXPECTED")
    private float cashExpected;
    
    @Column(name = "CASH_COUNTED")
    private float cashCounted;
    
    @Column(name = "INBALANCE")
    private float inbalance;
    
    @ManyToOne
    @JoinColumn(name = "USER_OPENING_ID", nullable = false)
    private User userOpening;
    
    @ManyToOne
    @JoinColumn(name = "USER_CLOSING_ID")
    private User userClosing;
    
    @ManyToOne
    @JoinColumn(name = "OPERATION_ID", nullable = false)
    private Operation operation;

    public DailyOperation() {
    }

    public DailyOperation(Calendar openingDate) {
        this.openingDate = openingDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Calendar openingDate) {
        this.openingDate = openingDate;
    }

    public Calendar getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Calendar closingDate) {
        this.closingDate = closingDate;
    }

    public float getProduced() {
        return produced;
    }

    public void setProduced(float produced) {
        this.produced = produced;
    }

    public float getAdvances() {
        return advances;
    }

    public void setAdvances(float advances) {
        this.advances = advances;
    }

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public float getCashExpected() {
        return cashExpected;
    }

    public void setCashExpected(float cashExpected) {
        this.cashExpected = cashExpected;
    }

    public float getCashCounted() {
        return cashCounted;
    }

    public void setCashCounted(float cashCounted) {
        this.cashCounted = cashCounted;
    }

    public float getInbalance() {
        return inbalance;
    }

    public void setInbalance(float inbalance) {
        this.inbalance = inbalance;
    }

    public float getEgress() {
        return egress;
    }

    public void setEgress(float egress) {
        this.egress = egress;
    }

    public User getUserOpening() {
        return userOpening;
    }

    public void setUserOpening(User userOpening) {
        this.userOpening = userOpening;
    }

    public User getUserClosing() {
        return userClosing;
    }

    public void setUserClosing(User userClosing) {
        this.userClosing = userClosing;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
