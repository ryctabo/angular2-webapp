package com.nativapps.arpia.database.entity;

import com.nativapps.arpia.model.Denomination;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "CASH_REGISTER")
public class CashRegister implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "COUNT")
    private int count;
    
    @Column(name = "`VALUE`")
    private float value;
    
    @Column(name = "CASH_TYPE")
    @Enumerated(EnumType.STRING)
    private Denomination.Type cashType;
    
    @ManyToOne
    @JoinColumn(name = "CASH_TALLYING_ID")
    private CashTallying cashTallying;

    public CashRegister() {
    }

    public CashRegister(int count, float value, Denomination.Type cashType) {
        this.count = count;
        this.value = value;
        this.cashType = cashType;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Denomination.Type getCashType() {
        return cashType;
    }

    public void setCashType(Denomination.Type cashType) {
        this.cashType = cashType;
    }

    public CashTallying getCashTallying() {
        return cashTallying;
    }

    public void setCashTallying(CashTallying cashTallying) {
        this.cashTallying = cashTallying;
    }
}
