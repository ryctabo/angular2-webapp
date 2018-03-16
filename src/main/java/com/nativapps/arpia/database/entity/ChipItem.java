package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "CHIP_ITEM")
public class ChipItem implements Serializable {

    @EmbeddedId
    private ChipItemPK id;

    @MapsId("inventoryId")
    @ManyToOne
    @JoinColumn(name = "INVENTORY_ID", nullable = false)
    private Inventory element;

    @MapsId("chipId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHIP_ID", nullable = false)
    private Chip chip;

    @Column(name = "COUNT", nullable = false)
    private int count;

    public ChipItem() {
        this.id = new ChipItemPK();
    }

    public ChipItem(Inventory element, int count) {
        this.id = new ChipItemPK();
        this.element = element;
        this.count = count;
    }

    public ChipItemPK getId() {
        return id;
    }

    public void setId(ChipItemPK id) {
        this.id = id;
    }

    public Inventory getElement() {
        return element;
    }

    public void setElement(Inventory element) {
        this.element = element;
    }

    public Chip getChip() {
        return chip;
    }

    public void setChip(Chip chip) {
        this.chip = chip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Embeddable
    public static class ChipItemPK implements Serializable {

        @Column(name = "CHIP_ID")
        private long chipId;

        @Column(name = "INVENTORY_ID")
        private long inventoryId;

        public ChipItemPK() { }

        public ChipItemPK(long chipId, long inventoryId) {
            this.chipId = chipId;
            this.inventoryId = inventoryId;
        }

        public long getChipId() {
            return chipId;
        }

        public void setChipId(long chipId) {
            this.chipId = chipId;
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

            ChipItemPK that = (ChipItemPK) o;

            if (chipId != that.chipId) return false;
            return inventoryId == that.inventoryId;
        }

        @Override
        public int hashCode() {
            int result = (int) (chipId ^ (chipId >>> 32));
            result = 31 * result + (int) (inventoryId ^ (inventoryId >>> 32));
            return result;
        }
    }

}
