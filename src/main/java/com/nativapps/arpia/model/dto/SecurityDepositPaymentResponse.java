package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SecurityDepositPaymentResponse extends SecurityDepositPaymentData {

    private Long id;

    private UserData owner;

    private Calendar created;

    private UserData receivedBy;

    public SecurityDepositPaymentResponse() {
    }

    public SecurityDepositPaymentResponse(Long id, UserData owner,
            Calendar created, UserData receivedBy, Double payment) {
        super(payment);
        this.id = id;
        this.owner = owner;
        this.created = created;
        this.receivedBy = receivedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserData getOwner() {
        return owner;
    }

    public void setOwner(UserData owner) {
        this.owner = owner;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public UserData getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(UserData receivedBy) {
        this.receivedBy = receivedBy;
    }

}
