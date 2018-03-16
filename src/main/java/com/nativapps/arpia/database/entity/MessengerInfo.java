package com.nativapps.arpia.database.entity;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MessengerInfo {

    private Messenger messenger;

    private int loans;

    private int returns;

    public MessengerInfo() {
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
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
