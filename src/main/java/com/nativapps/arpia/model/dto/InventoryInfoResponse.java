package com.nativapps.arpia.model.dto;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class InventoryInfoResponse {

    private InventoryResponse inventory;

    private Integer returns;

    private Integer loans;

    public InventoryInfoResponse() {
    }

    public InventoryResponse getInventory() {
        return inventory;
    }

    public void setInventory(InventoryResponse inventory) {
        this.inventory = inventory;
    }

    public Integer getReturns() {
        return returns;
    }

    public void setReturns(Integer returns) {
        this.returns = returns;
    }

    public Integer getLoans() {
        return loans;
    }

    public void setLoans(Integer loans) {
        this.loans = loans;
    }

}
