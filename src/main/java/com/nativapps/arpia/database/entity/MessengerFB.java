package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "MESSENGER_FB")
@NamedQueries({
    @NamedQuery(name = "messengerFB.findAllByType",
            query = "SELECT m FROM MessengerFB m "
            + "WHERE m.id.customerId = :customerId "
            + "AND m.type = :type")
    ,
    @NamedQuery(name = "messengerFB.find",
            query = "SELECT m FROM MessengerFB m "
            + "WHERE m.id.customerId = :customerId "
            + "AND m.id.messengerId = :messengerId "
            + "AND m.type = :type")
})
public class MessengerFB implements Serializable {

    public enum Type {
        FAVORITE_LIST, BLACKLIST
    }

    @EmbeddedId
    private MessengerFBPK id;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerParameter customerParam;

    @MapsId("messengerId")
    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID")
    private Messenger messenger;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

    public MessengerFB() {
        this.id = new MessengerFBPK();
    }

    public MessengerFB(CustomerParameter customerParam, Messenger messenger,
            Type type) {
        this.id = new MessengerFBPK();
        this.customerParam = customerParam;
        this.messenger = messenger;
        this.type = type;
    }

    public MessengerFBPK getId() {
        return id;
    }

    public void setId(MessengerFBPK id) {
        this.id = id;
    }

    public CustomerParameter getCustomerParam() {
        return customerParam;
    }

    public void setCustomerParam(CustomerParameter customerParam) {
        this.customerParam = customerParam;

        if (!customerParam.getMessengerFBList().contains(this))
            customerParam.addMessenger(this);
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Embeddable
    public static class MessengerFBPK implements Serializable {

        @Column(name = "CUSTOMER_ID")
        private long customerId;

        @Column(name = "MESSENGER_ID")
        private long messengerId;

        public MessengerFBPK() {
        }

        public MessengerFBPK(long customerId, long messengerId) {
            this.customerId = customerId;
            this.messengerId = messengerId;
        }

        public long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(long customerId) {
            this.customerId = customerId;
        }

        public long getMessengerId() {
            return messengerId;
        }

        public void setMessengerId(long messengerId) {
            this.messengerId = messengerId;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + (int) (this.customerId
                    ^ (this.customerId >>> 32));
            hash = 29 * hash + (int) (this.messengerId
                    ^ (this.messengerId >>> 32));
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
            final MessengerFBPK other = (MessengerFBPK) obj;
            if (this.customerId != other.customerId)
                return false;
            return this.messengerId == other.messengerId;
        }
    }
}
