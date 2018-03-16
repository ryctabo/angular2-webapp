package com.nativapps.arpia.model.dto;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PenaltyRequest extends PenaltyData {

    private Long messengerId;

    public PenaltyRequest() {
    }

    public PenaltyRequest(Long messengerId, String description) {
        super(description);
        this.messengerId = messengerId;
    }

    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }
}
