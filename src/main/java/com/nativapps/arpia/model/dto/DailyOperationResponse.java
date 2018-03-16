package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class DailyOperationResponse {

    private Long id;

    private Calendar openingDate;

    private Calendar closingDate;

    private Float produced;

    private Float advances;

    private Float credits;

    private Float egress;

    private Float cashExcepted;
    
    private Float cashCounted;

    private OperationResponse operation;

    private UserResponse userOpening;

    private UserResponse userClosing;

    public DailyOperationResponse() {
    }

    public DailyOperationResponse(Long id, Calendar openingDate, 
            Calendar closingDate, Float produced, Float advances, Float credits,
            Float egress, Float cashExcepted, Float cashCounted) {
        this.id = id;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.produced = produced;
        this.advances = advances;
        this.credits = credits;
        this.egress = egress;
        this.cashExcepted = cashExcepted;
        this.cashCounted = cashCounted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getProduced() {
        return produced;
    }

    public void setProduced(Float produced) {
        this.produced = produced;
    }

    public Float getAdvances() {
        return advances;
    }

    public void setAdvances(Float advances) {
        this.advances = advances;
    }

    public Float getCredits() {
        return credits;
    }

    public void setCredits(Float credits) {
        this.credits = credits;
    }

    public Float getCashExcepted() {
        return cashExcepted;
    }

    public void setCashExcepted(Float cashExcepted) {
        this.cashExcepted = cashExcepted;
    }

    public Float getCashCounted() {
        return cashCounted;
    }

    public void setCashCounted(Float cashCounted) {
        this.cashCounted = cashCounted;
    }

    public Float getEgress() {
        return egress;
    }

    public void setEgress(Float egress) {
        this.egress = egress;
    }

    public OperationResponse getOperation() {
        return operation;
    }

    public void setOperation(OperationResponse operation) {
        this.operation = operation;
    }

    public UserResponse getUserOpening() {
        return userOpening;
    }

    public void setUserOpening(UserResponse userOpening) {
        this.userOpening = userOpening;
    }

    public UserResponse getUserClosing() {
        return userClosing;
    }

    public void setUserClosing(UserResponse userClosing) {
        this.userClosing = userClosing;
    }
}
