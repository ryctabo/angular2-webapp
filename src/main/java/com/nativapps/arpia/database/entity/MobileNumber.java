package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.1.0
 */
@Entity
@Table(name = "MOBILE_NUMBER")
@NamedQueries({
    @NamedQuery(name = "mobileNumber.lastByOperationId",
            query = "select m from MobileNumber m where m.operation.id = :id "
            + "order by m.id.index desc")
})
public class MobileNumber implements Serializable {

    @EmbeddedId
    private MobileNumberPK id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "MESSENGER_ID")
    private Messenger messenger;

    @Column(name = "MESSENGER_ID", insertable = false, updatable = false)
    private Long messengerId;

    @MapsId("operationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATION_ID", nullable = false)
    private Operation operation;

    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createDate;

    @Column(name = "UPDATE_DATE", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateDate;

    public MobileNumber() {
        this.createDate = Calendar.getInstance();
    }

    public MobileNumber(int index) {
        this.id = new MobileNumberPK(index);
        this.createDate = Calendar.getInstance();
    }

    public MobileNumber(int index, Operation operation) {
        this.id = new MobileNumberPK(index);
        this.operation = operation;
        this.createDate = Calendar.getInstance();
    }

    public MobileNumber(MobileNumberPK id, Messenger messenger,
            Operation operation, Calendar createDate, Calendar updateDate) {
        this.id = id;
        this.messenger = messenger;
        this.operation = operation;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public MobileNumberPK getId() {
        return id;
    }

    public void setId(MobileNumberPK id) {
        this.id = id;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    @Embeddable
    public static class MobileNumberPK implements Serializable {

        @Column(name = "`INDEX`", nullable = false)
        private int index;

        @Column(name = "OPERATION_ID")
        private long operationId;

        public MobileNumberPK() {
        }

        public MobileNumberPK(int index) {
            this.index = index;
        }

        public MobileNumberPK(int index, long operationId) {
            this.index = index;
            this.operationId = operationId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getOperationId() {
            return operationId;
        }

        public void setOperationId(long operationId) {
            this.operationId = operationId;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + this.index;
            hash = 37 * hash + (int) (this.operationId ^ (this.operationId >>> 32));
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
            final MobileNumberPK other = (MobileNumberPK) obj;
            if (this.index != other.index)
                return false;
            return this.operationId == other.operationId;
        }

    }

}
