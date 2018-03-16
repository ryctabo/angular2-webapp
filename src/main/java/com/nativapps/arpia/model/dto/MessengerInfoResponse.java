package com.nativapps.arpia.model.dto;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MessengerInfoResponse {

    private MessengerResponse messenger;

    private Integer loans;

    private Integer returns;

    public MessengerInfoResponse() {
    }

    public MessengerInfoResponse(MessengerResponse messenger, Integer loans,
            Integer returns) {
        this.messenger = messenger;
        this.loans = loans;
        this.returns = returns;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }

    public Integer getLoans() {
        return loans;
    }

    public void setLoans(Integer loans) {
        this.loans = loans;
    }

    public Integer getReturns() {
        return returns;
    }

    public void setReturns(Integer returns) {
        this.returns = returns;
    }

}
