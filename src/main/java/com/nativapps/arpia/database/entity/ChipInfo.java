package com.nativapps.arpia.database.entity;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ChipInfo {

    private int loans;

    private int returns;

    public ChipInfo(int loans, int returns) {
        this.loans = loans;
        this.returns = returns;
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
