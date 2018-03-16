package com.nativapps.arpia.model.dto;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ChipRequest extends ChipData {

    private Long messenger;

    private List<ChipItemRequest> items;

    public Long getMessenger() {
        return messenger;
    }

    public void setMessenger(Long messenger) {
        this.messenger = messenger;
    }

    public List<ChipItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ChipItemRequest> items) {
        this.items = items;
    }
}
