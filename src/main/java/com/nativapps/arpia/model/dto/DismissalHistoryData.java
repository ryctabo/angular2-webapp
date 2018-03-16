package com.nativapps.arpia.model.dto;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DismissalHistoryData {

    private Boolean dismissal;

    private String reason;

    private Long messengerId;

    public DismissalHistoryData() {
    }

    public DismissalHistoryData(Boolean dismissal, String reason,
            Long messengerId) {
        this.dismissal = dismissal;
        this.reason = reason;
        this.messengerId = messengerId;
    }

    public Boolean getDismissal() {
        return dismissal;
    }

    public void setDismissal(Boolean dismissal) {
        this.dismissal = dismissal;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }

}
