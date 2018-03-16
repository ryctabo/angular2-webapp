package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class PeaceAndSafetyRequest extends PeaceAndSafetyData {

    private Long messenger;

    private Long operation;

    public PeaceAndSafetyRequest() {
    }

    public PeaceAndSafetyRequest(Long messenger, Long operation,
            Calendar refund, String groundsDismissal,
            String observations, String message) {
        super(refund, groundsDismissal, observations, message);
        this.messenger = messenger;
        this.operation = operation;
    }

    public Long getMessenger() {
        return messenger;
    }

    public void setMessenger(Long messenger) {
        this.messenger = messenger;
    }

    public Long getOperation() {
        return operation;
    }

    public void setOperation(Long operation) {
        this.operation = operation;
    }

}
