package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class QCRResponse extends QCRData {

    private Integer index;

    private CustomerDataDto customer;
    
    private MessengerResponse messenger;

    private Calendar openingDate;

    private Calendar closingDate;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public CustomerDataDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDataDto customer) {
        this.customer = customer;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }

    public Calendar getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Calendar openingDate) {
        this.openingDate = openingDate;
    }

    public Calendar getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Calendar closingDate) {
        this.closingDate = closingDate;
    }
}
