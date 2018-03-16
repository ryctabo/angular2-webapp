package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PenaltyResponse extends PenaltyData {

    private Long id;

    private MessengerResponse messenger;

    private Calendar created;

    private Calendar updated;

    public PenaltyResponse() {
    }

    public PenaltyResponse(Long id, MessengerResponse messenger,
            Calendar created, Calendar updated, String description) {
        super(description);
        this.id = id;
        this.messenger = messenger;
        this.created = created;
        this.updated = updated;
    }

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

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }

}
