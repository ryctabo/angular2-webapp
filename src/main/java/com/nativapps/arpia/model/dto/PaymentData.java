package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class PaymentData {

    private Integer index;

    private Double payment;

    private UserResponse authorizedBy;

    private Calendar date;

    private Long operationId;

    public PaymentData() {
    }

    public PaymentData(Integer index, Double payment, UserResponse authorizedBy,
            Calendar date, Long operationId) {
        this.index = index;
        this.payment = payment;
        this.authorizedBy = authorizedBy;
        this.date = date;
        this.operationId = operationId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public UserResponse getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(UserResponse authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

}
