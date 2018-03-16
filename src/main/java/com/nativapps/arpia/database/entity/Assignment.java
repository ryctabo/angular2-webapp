package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
@Entity
@Table(name = "ASSIGNMENT")
@NamedQueries({
        @NamedQuery(name = "assignment.findByDomicileId",
                query = "select a from Assignment a where a.domicile.id = :id"),
        @NamedQuery(name = "assignment.countByDomicileId",
                query = "select count(a) from Assignment a where a.domicile.id = :id")
})
public class Assignment implements Serializable {

    @EmbeddedId
    private AssignmentPK id;

    @MapsId("domicileId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "HOME_SERVICE_EXECUTE_ID", nullable = false)
    private DomicileExecute domicile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    @Column(name = "MESSENGER_ID", insertable = false, updatable = false)
    private long messengerId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "REGISTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;

    public Assignment() {
        this.id = new AssignmentPK();
        this.registerDate = Calendar.getInstance();
    }

    public Assignment(int index, DomicileExecute domicile, Messenger messenger, User user) {
        this.id = new AssignmentPK(index);
        this.domicile = domicile;
        this.messenger = messenger;
        this.user = user;
        this.registerDate = Calendar.getInstance();
    }

    public AssignmentPK getId() {
        return id;
    }

    public void setId(AssignmentPK id) {
        this.id = id;
    }

    public DomicileExecute getDomicile() {
        return domicile;
    }

    public void setDomicile(DomicileExecute domicile) {
        this.domicile = domicile;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(long messengerId) {
        this.messengerId = messengerId;
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

    @Embeddable
    public static class AssignmentPK implements Serializable {

        @Column(name = "`INDEX`", nullable = false)
        private int index;

        @Column(name = "HOME_SERVICE_EXECUTE_ID")
        private long domicileId;

        public AssignmentPK() {
        }

        public AssignmentPK(int index) {
            this.index = index;
        }

        public AssignmentPK(int index, long domicileId) {
            this.index = index;
            this.domicileId = domicileId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getDomicileId() {
            return domicileId;
        }

        public void setDomicileId(long domicileId) {
            this.domicileId = domicileId;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + this.index;
            hash = 53 * hash + (int) (this.domicileId ^ (this.domicileId >>> 32));
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final AssignmentPK other = (AssignmentPK) obj;
            if (this.index != other.index)
                return false;
            return this.domicileId == other.domicileId;
        }

    }

}
