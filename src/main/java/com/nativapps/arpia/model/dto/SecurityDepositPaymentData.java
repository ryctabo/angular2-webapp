package com.nativapps.arpia.model.dto;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SecurityDepositPaymentData {

    protected Double payment;

    public SecurityDepositPaymentData() {
    }

    public SecurityDepositPaymentData(Double payment) {
        this.payment = payment;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

}
