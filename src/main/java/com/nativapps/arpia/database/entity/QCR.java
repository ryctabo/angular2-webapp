package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
@Entity
@Table(name = "QCR")
public class QCR implements Serializable {

    @Embeddable
    public static class QCRPK implements Serializable {

        @Column(name = "`INDEX`", nullable = false)
        private int index;

        @Column(name = "CUSTOMER_ID")
        private long customerId;

        public QCRPK() {
        }

        public QCRPK(int index, long customerId) {
            this.index = index;
            this.customerId = customerId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(long customerId) {
            this.customerId = customerId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            QCRPK qcrpk = (QCRPK) o;

            if (index != qcrpk.index) return false;
            return customerId == qcrpk.customerId;
        }

        @Override
        public int hashCode() {
            int result = index;
            result = 31 * result + (int) (customerId ^ (customerId >>> 32));
            return result;
        }
    }

    public enum Status {
        PENDING, IN_PROCESS, FINISHED, CANCELED
    }

    @EmbeddedId
    private QCRPK id;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private CustomerData customer;
    
    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID", nullable = false)
    private Messenger messenger;

    @Column(name = "INCONVENIENT", columnDefinition = "TEXT", nullable = false)
    private String inconvenient;

    @Column(name = "COUNTERPART_VERSION", columnDefinition = "TEXT")
    private String counterpartVersion;

    @Column(name = "ADMIN_EVAL", columnDefinition = "TEXT")
    private String adminEval;

    @Column(name = "ADMIN_SOLUTION", columnDefinition = "TEXT")
    private String adminSolution;

    @Column(name = "STATUS", nullable = false)
    private Status status;

    @Column(name = "OPENING_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar openingDate;

    @Column(name = "DEADLINE")
    @Temporal(TemporalType.DATE)
    private Calendar closingDate;

    public QCR() {
        this.id = new QCRPK();
    }

    public QCRPK getId() {
        return id;
    }

    public void setId(QCRPK id) {
        this.id = id;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerData customer) {
        this.customer = customer;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public String getInconvenient() {
        return inconvenient;
    }

    public void setInconvenient(String inconvenient) {
        this.inconvenient = inconvenient;
    }

    public String getCounterpartVersion() {
        return counterpartVersion;
    }

    public void setCounterpartVersion(String counterpartVersion) {
        this.counterpartVersion = counterpartVersion;
    }

    public String getAdminEval() {
        return adminEval;
    }

    public void setAdminEval(String adminEval) {
        this.adminEval = adminEval;
    }

    public String getAdminSolution() {
        return adminSolution;
    }

    public void setAdminSolution(String adminSolution) {
        this.adminSolution = adminSolution;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Calendar getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Calendar openingDate) {
        this.openingDate = openingDate;
    }

    public Calendar getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Calendar closingDate) {
        this.closingDate = closingDate;
    }
}
