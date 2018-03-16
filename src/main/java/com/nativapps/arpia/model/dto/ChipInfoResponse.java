package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ChipInfoResponse {

    private Integer busy;

    private Integer loans;

    private Integer returns;

    public ChipInfoResponse() {
    }

    public ChipInfoResponse(Integer loans, Integer returns) {
        this.busy = loans - returns;
        this.loans = loans;
        this.returns = returns;
    }

    public Integer getBusy() {
        return busy;
    }

    public void setBusy(Integer busy) {
        this.busy = busy;
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
