package com.nativapps.arpia.model.dto;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SecurityDepositPaymentRequest extends SecurityDepositPaymentData {

    private Long ownerId;

    public SecurityDepositPaymentRequest() {
    }

    public SecurityDepositPaymentRequest(Long ownerId, Double payment) {
        super(payment);
        this.ownerId = ownerId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

}
