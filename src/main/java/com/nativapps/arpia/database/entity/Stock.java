package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 3.0
 */
@Entity
@Table(name = "STOCK")
public class Stock implements Serializable {

    @EmbeddedId
    private StockPK id;

    @MapsId("inventoryId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "INVENTORY_ID", nullable = false)
    private Inventory inventory;

    @Column(name = "TYPE", updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private StockType type;

    @Column(name = "COUNT", updatable = false)
    private int count;

    @Column(name = "REASON", updatable = false, length = 200)
    private String reason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REGISTER_DATE", updatable = false)
    private Calendar registerDate;

    public Stock() {
        this.id = new StockPK();
    }

    public Stock(StockType type, int count, String reason, Calendar registerDate) {
        this.id = new StockPK();
        this.type = type;
        this.count = count;
        this.reason = reason;
        this.registerDate = registerDate;
    }

    public StockPK getId() {
        return id;
    }

    public void setId(StockPK id) {
        this.id = id;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public StockType getType() {
        return type;
    }

    public void setType(StockType type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    @Embeddable
    public static class StockPK implements Serializable {

        @Column(name = "`INDEX`", nullable = false)
        private int index;

        @Column(name = "INVENTORY_ID")
        private long inventoryId;

        public StockPK() {}

        public StockPK(int index, long inventoryId) {
            this.index = index;
            this.inventoryId = inventoryId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getInventoryId() {
            return inventoryId;
        }

        public void setInventoryId(long inventoryId) {
            this.inventoryId = inventoryId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StockPK stockPK = (StockPK) o;

            if (index != stockPK.index) return false;
            return inventoryId == stockPK.inventoryId;
        }

        @Override
        public int hashCode() {
            int result = index;
            result = 31 * result + (int) (inventoryId ^ (inventoryId >>> 32));
            return result;
        }
    }

}
