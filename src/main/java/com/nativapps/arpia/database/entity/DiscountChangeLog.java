package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "DISCOUNT_CHANGE_LOG")
public class DiscountChangeLog implements Serializable {

    @EmbeddedId
    private DiscountChangeLogPK id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "REGISTER_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;

    @MapsId("discountId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISCOUNT_ID", nullable = false)
    private Discount discount;

    public DiscountChangeLog() {
        this.id = new DiscountChangeLogPK();
    }

    public DiscountChangeLog(User user, Calendar registerDate) {
        this.user = user;
        this.registerDate = registerDate;
        this.id = new DiscountChangeLogPK();
    }
    
    public DiscountChangeLog(DiscountChangeLogPK id, User user, Calendar registerDate, Discount discount) {
        this.id = id;
        this.user = user;
        this.registerDate = registerDate;
        this.discount = discount;
    }

    public DiscountChangeLogPK getId() {
        return id;
    }

    public void setId(DiscountChangeLogPK id) {
        this.id = id;
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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Embeddable
    public static class DiscountChangeLogPK implements Serializable {

        @Column(name = "`INDEX`")
        private int index;

        @Column(name = "DISCOUNT_ID")
        private long discountId;

        public DiscountChangeLogPK() {
        }

        public DiscountChangeLogPK(int index, long discountId) {
            this.index = index;
            this.discountId = discountId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getDiscountId() {
            return discountId;
        }

        public void setDiscountId(long discountId) {
            this.discountId = discountId;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + this.index;
            hash = 67 * hash + (int) (this.discountId ^ (this.discountId >>> 32));
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
            final DiscountChangeLogPK other = (DiscountChangeLogPK) obj;
            if (this.index != other.index)
                return false;
            return this.discountId != other.discountId;
        }

    }

}
