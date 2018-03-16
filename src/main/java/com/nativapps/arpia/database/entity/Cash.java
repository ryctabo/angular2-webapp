package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "CASH")
@NamedQueries({
    @NamedQuery(name = "cash.lastRegister",
            query = "SELECT c FROM Cash c ORDER BY c.registerDate DESC")
})
public class Cash implements Serializable {
    
    public enum Type {
        IN, OUT
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "CASH", nullable = false)
    private float amount;
    
    @Column(name = "REASON", nullable = false, columnDefinition = "TEXT")
    private String reason;
    
    @Column(name = "TYPE", nullable = false)
    private Type registerType;
    
    @ManyToOne
    @JoinColumn(name = "OPERATION_ID", nullable = false)
    private Operation operation;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    
    @Column(name = "REGISTER_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;
    
    @Column(name = "TOTAL_CASH", nullable = false)
    private float totalCash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Type getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Type registerType) {
        this.registerType = registerType;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public float getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(float totalCash) {
        this.totalCash = totalCash;
    }
}
