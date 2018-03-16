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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "MARKETING_MESSAGE")
public class MarketingMessage implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private CustomerData customer;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    
    @Column(name = "VISIT")
    private boolean visit;
    
    @Column(name = "`CALL`")
    private boolean call;
    
    @Column(name = "REGISTER_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;
    
    @Column(name = "OBSERVATIONS", nullable = false, columnDefinition = "TEXT")
    private String observations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerData customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public boolean isCall() {
        return call;
    }

    public void setCall(boolean call) {
        this.call = call;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
