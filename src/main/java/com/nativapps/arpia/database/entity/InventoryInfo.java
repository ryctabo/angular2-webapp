package com.nativapps.arpia.database.entity;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class InventoryInfo {

    private Inventory inventory;

    private int loans;

    private int returns;

    public InventoryInfo() {
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getLoans() {
        return loans;
    }

    public void setLoans(int loans) {
        this.loans = loans;
    }

    public int getReturns() {
        return returns;
    }

    public void setReturns(int returns) {
        this.returns = returns;
    }

}
