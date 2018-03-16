package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class PeaceAndSafetyData {

    protected Calendar refund;

    protected String groundsDismissal;

    protected String observations;

    protected String message;

    public PeaceAndSafetyData() {
    }

    public PeaceAndSafetyData(Calendar refund,
            String groundsDismissal, String observations, String message) {
        this.refund = refund;
        this.groundsDismissal = groundsDismissal;
        this.observations = observations;
        this.message = message;
    }

    public Calendar getRefund() {
        return refund;
    }

    public void setRefund(Calendar refund) {
        this.refund = refund;
    }

    public String getGroundsDismissal() {
        return groundsDismissal;
    }

    public void setGroundsDismissal(String groundsDismissal) {
        this.groundsDismissal = groundsDismissal;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
