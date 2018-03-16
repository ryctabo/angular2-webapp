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
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "DISMISSAL_HISTORY")
@NamedQueries({
    @NamedQuery(name = "dismissalHistory.findByMessengerId",
            query = "select d from DismissalHistory d where d.messenger.id = :id")
})
public class DismissalHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "DISMISSAL")
    private boolean dismissal;

    @Column(name = "REASON", nullable = false, columnDefinition = "TEXT",
            updatable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    @Column(name = "REGISTER", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar register;

    public DismissalHistory() {
        this.register = Calendar.getInstance();
    }

    public DismissalHistory(boolean dismissal, String reason, Messenger messenger) {
        this.dismissal = dismissal;
        this.reason = reason;
        this.messenger = messenger;
        this.register = Calendar.getInstance();
    }

    public DismissalHistory(long id, boolean dismissal, String reason,
            Messenger messenger, Calendar register) {
        this.id = id;
        this.dismissal = dismissal;
        this.reason = reason;
        this.messenger = messenger;
        this.register = register;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDismissal() {
        return dismissal;
    }

    public void setDismissal(boolean dismissal) {
        this.dismissal = dismissal;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Calendar getRegister() {
        return register;
    }

    public void setRegister(Calendar register) {
        this.register = register;
    }

}
