package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "BASE")
@NamedQueries({
    @NamedQuery(name = "base.findLastRegister",
            query = "SELECT b FROM Base b WHERE b.messenger.id = :messengerId "
                    + "ORDER BY b.id DESC")
})
public class Base implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID")
    private Messenger messenger;
    
    @ManyToOne
    @JoinColumn(name = "OPERATION_ID")
    private Operation operation;
    
    @Column(name = "DELIVERY")
    private int baseDelivery;
    
    @Column(name = "`RETURN`")
    private int baseReturn;
    
    @Column(name = "AMOUNT")
    private float amount;
    
    @Column(name = "REGISTER_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;
    
    @OneToMany(mappedBy = "base", fetch = FetchType.EAGER, 
            cascade = CascadeType.ALL)
    private List<BaseRecord> records;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getBaseDelivery() {
        return baseDelivery;
    }

    public void setBaseDelivery(int baseDelivery) {
        this.baseDelivery = baseDelivery;
    }

    public int getBaseReturn() {
        return baseReturn;
    }

    public void setBaseReturn(int baseReturn) {
        this.baseReturn = baseReturn;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public List<BaseRecord> getRecords() {
        return records;
    }

    public void setRecords(List<BaseRecord> records) {
        this.records = records;
    }
    
    public void addRecord(BaseRecord record) {
        if (this.records == null)
            this.records = new ArrayList<>();
        
        if (record != null) {
            this.records.add(record);
            
            if (record.getBase() != this)
                record.setBase(this);
        }
    }
}
