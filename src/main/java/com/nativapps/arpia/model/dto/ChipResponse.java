package com.nativapps.arpia.model.dto;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ChipResponse extends ChipData {

    private Long id;

    private MessengerResponse messenger;

    private List<ChipItemResponse> items;

    private Calendar created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }

    public List<ChipItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ChipItemResponse> items) {
        this.items = items;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
