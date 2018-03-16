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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "PENALTY")
@NamedQueries({
    @NamedQuery(name = "penalty.findByMessenger",
            query = "SELECT p FROM Penalty p WHERE p.messenger.id = :id")
})
public class Penalty implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNED_BY", nullable = false)
    private User assigneBy;

    @Column(name = "created", nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar created;

    @Column(name = "updated")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar updated;

    @Column(name = "DESCRIPTION")
    private String description;

    public Penalty() {
    }

    public Penalty(String description) {
        this.description = description;
    }

    public Penalty(long id, User assignedBy, Calendar created, String description) {
        this.id = id;
        this.assigneBy = assignedBy;
        this.created = created;
        this.description = description;
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

    public User getAssigneBy() {
        return assigneBy;
    }

    public void setAssigneBy(User assigneBy) {
        this.assigneBy = assigneBy;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }

}
