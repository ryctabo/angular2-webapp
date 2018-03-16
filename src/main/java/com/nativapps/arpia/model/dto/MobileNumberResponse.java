package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
public class MobileNumberResponse extends MobileNumberData {

    private MessengerResponse messenger;

    private Calendar createDate;

    private Calendar updateDate;

    public MobileNumberResponse() {
    }

    public MobileNumberResponse(MessengerResponse messenger, Calendar createDate,
            Calendar updateDate, Integer index) {
        super(index);
        this.messenger = messenger;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

}
