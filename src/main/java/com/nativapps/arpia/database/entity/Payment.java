package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "PAYMENT")
@NamedQueries({
        @NamedQuery(name = "payment.paidOut",
                query = "SELECT SUM(p.payment) FROM Payment p WHERE p.debt.id = :debtId"),
        @NamedQuery(name = "payment.findByDebts",
                query = "SELECT p FROM Payment p WHERE p.debt.id = :debtId")
})
public class Payment implements Serializable {

    @Embeddable
    public static class PaymentPK implements Serializable {

        @Column(name = "`INDEX`", nullable = false)
        private int index;

        @Column(name = "DEBT_ID")
        private long debtId;

        public PaymentPK() {
        }

        public PaymentPK(int index, long debtId) {
            this.index = index;
            this.debtId = debtId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getDebtId() {
            return debtId;
        }

        public void setDebtId(long debtId) {
            this.debtId = debtId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PaymentPK paymentPK = (PaymentPK) o;

            if (index != paymentPK.index) return false;
            return debtId == paymentPK.debtId;
        }

        @Override
        public int hashCode() {
            int result = index;
            result = 31 * result + (int) (debtId ^ (debtId >>> 32));
            return result;
        }
    }

    @EmbeddedId
    private PaymentPK id;

    @Column(name = "PAYMENT")
    private double payment;

    @Column(name = "`DATE`", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    @ManyToOne
    @JoinColumn(name = "AUTHORIZED_BY", nullable = false)
    private User authorizedBy;

    @MapsId("debtId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEBT_ID", nullable = false)
    private Debt debt;

    public Payment() {
        this.id = new PaymentPK();
    }

    public Payment(PaymentPK id) {
        this.id = id;
    }

    public Payment(int index, double payment, Calendar date, User authorizedBy, Debt debt) {
        this.id = new PaymentPK();
        this.id.setIndex(index);
        this.payment = payment;
        this.date = date;
        this.authorizedBy = authorizedBy;
        this.debt = debt;
    }

    public PaymentPK getId() {
        return id;
    }

    public void setId(PaymentPK id) {
        this.id = id;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public User getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(User authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }
}
