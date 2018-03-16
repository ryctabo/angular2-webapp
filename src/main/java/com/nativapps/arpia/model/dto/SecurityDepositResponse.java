package com.nativapps.arpia.model.dto;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SecurityDepositResponse {

    private UserData owner;

    private Double paidToDate;

    private Double depositAmount;

    public SecurityDepositResponse() {
    }

    public SecurityDepositResponse(UserData owner, Double paidToDate,
            Double depositAmount) {
        this.owner = owner;
        this.paidToDate = paidToDate;
        this.depositAmount = depositAmount;
    }

    public UserData getOwner() {
        return owner;
    }

    public void setOwner(UserData owner) {
        this.owner = owner;
    }

    public Double getPaidToDate() {
        return paidToDate;
    }

    public void setPaidToDate(Double paidToDate) {
        this.paidToDate = paidToDate;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

}
