package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ChipItemResponse extends ChipItemData {

    private InventoryResponse element;

    public InventoryResponse getElement() {
        return element;
    }

    public void setElement(InventoryResponse element) {
        this.element = element;
    }
}
