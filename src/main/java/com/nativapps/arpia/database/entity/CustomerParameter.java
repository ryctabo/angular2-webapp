package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "CUSTOMER_PARAMETER")
@Inheritance(strategy = InheritanceType.JOINED)
public class CustomerParameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARAMETER_ID")
    protected long id;

    @Column(name = "URGENT_ORDERS")
    protected boolean urgentOrders;

    @Column(name = "ALWAYS_MONEY_BACK")
    protected boolean alwaysMoneyReturn;

    @Column(name = "SPECIAL_RATE_INFO", columnDefinition = "TEXT")
    protected String specialRateInfo;
    
    @Column(name = "DENIED")
    protected boolean denied;
    
    @Column(name = "`CONDITION`", columnDefinition = "TEXT")
    protected String condition;
    
    @OneToMany(mappedBy = "customerParam", cascade = CascadeType.ALL)
    protected List<MessengerFB> messengerFBList;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRACING")
    protected CustomerTracing tracing;

    public CustomerParameter() {
        this.tracing = new CustomerTracing();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isUrgentOrders() {
        return urgentOrders;
    }

    public void setUrgentOrders(boolean urgentOrders) {
        this.urgentOrders = urgentOrders;
    }

    public boolean isAlwaysMoneyReturn() {
        return alwaysMoneyReturn;
    }

    public void setAlwaysMoneyReturn(boolean alwaysMoneyReturn) {
        this.alwaysMoneyReturn = alwaysMoneyReturn;
    }

    public String getSpecialRateInfo() {
        return specialRateInfo;
    }

    public void setSpecialRateInfo(String specialRateInfo) {
        this.specialRateInfo = specialRateInfo;
    }

    public List<MessengerFB> getMessengerFBList() {
        return messengerFBList;
    }

    public void setMessengerFBList(List<MessengerFB> messengerFBList) {
        this.messengerFBList = messengerFBList;
    }
    
    public void addMessenger(MessengerFB messenger) {
        if (this.messengerFBList == null)
            this.messengerFBList = new ArrayList<>();
        
        if (messenger != null) {
            this.messengerFBList.add(messenger);
            
            if (messenger.getCustomerParam() != this)
                messenger.setCustomerParam(this);
        }
        
    }

    public boolean isDenied() {
        return denied;
    }

    public void setDenied(boolean denied) {
        this.denied = denied;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public CustomerTracing getTracing() {
        return tracing;
    }

    public void setTracing(CustomerTracing tracing) {
        this.tracing = tracing;
    }
}
