package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class GoodStandingData {
    
    protected Calendar refundDate;
    
    protected String reason;
    
    protected String observations;
    
    protected String message;

    public Calendar getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Calendar refundDate) {
        this.refundDate = refundDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
