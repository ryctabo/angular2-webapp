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
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "SECURITY_DEPOSIT")
@NamedQueries({
    @NamedQuery(name = "securityDeposit.paidOut",
            query = "SELECT SUM(sd.payment) FROM SecurityDeposit sd "
            + "WHERE sd.owner.id = :ownerId")
    ,
    @NamedQuery(name = "securityDeposit.count",
            query = "SELECT COUNT(sd) FROM SecurityDeposit sd")
})
public class SecurityDeposit implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "PAYMENT")
    private double payment;

    @Column(name = "CREATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created;

    @ManyToOne
    @JoinColumn(name = "RECEIVED_BY", nullable = false)
    private User receivedBy;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private User owner;

    public SecurityDeposit() {
    }

    public SecurityDeposit(double payment) {
        this.payment = payment;
    }

    public SecurityDeposit(double payment, Calendar paymentDay,
            User authorizedBy) {
        this.payment = payment;
        this.created = paymentDay;
        this.receivedBy = authorizedBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public User getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(User receivedBy) {
        this.receivedBy = receivedBy;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
