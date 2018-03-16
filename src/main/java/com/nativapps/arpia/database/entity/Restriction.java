package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
@Entity
@Table(name = "RESTRICTION")
@NamedQueries({
    @NamedQuery(name = "restriction.findByMessengerId",
            query = "SELECT r FROM Restriction r WHERE r.messenger.id = :messengerId")
}
)
public class Restriction implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    @Column(name = "BREAK_DAY")
    private boolean breakDay;

    @Column(name = "TRANSFER_BLOCK")
    private boolean transferBlocked;

    @Column(name = "SERVICE_BLOCK")
    private boolean serviceBlocked;

    @Column(name = "MONEY_LIMIT")
    private float moneyLimit;

    public Restriction() {
    }

    public Restriction(boolean breakDay, boolean transferBlocked,
            boolean serviceBlocked, float moneyLimit) {
        this.breakDay = breakDay;
        this.transferBlocked = transferBlocked;
        this.serviceBlocked = serviceBlocked;
        this.moneyLimit = moneyLimit;
    }

    public Restriction(long id, boolean breakDay, boolean transferBlocked,
            boolean serviceBlocked, float moneyLimit) {
        this.id = id;
        this.breakDay = breakDay;
        this.transferBlocked = transferBlocked;
        this.serviceBlocked = serviceBlocked;
        this.moneyLimit = moneyLimit;
    }

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

    public boolean isBreakDay() {
        return breakDay;
    }

    public void setBreakDay(boolean breakDay) {
        this.breakDay = breakDay;
    }

    public boolean isTransferBlocked() {
        return transferBlocked;
    }

    public void setTransferBlocked(boolean transferBlocked) {
        this.transferBlocked = transferBlocked;
    }

    public boolean isServiceBlocked() {
        return serviceBlocked;
    }

    public void setServiceBlocked(boolean serviceBlocked) {
        this.serviceBlocked = serviceBlocked;
    }

    public float getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(float moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

}
