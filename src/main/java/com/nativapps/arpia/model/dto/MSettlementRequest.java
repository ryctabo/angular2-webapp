package com.nativapps.arpia.model.dto;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class MSettlementRequest extends MSettlementData {

    private Long shiftplanningId;

    private Integer shiftIndex;

    private Long messengerId;

    public Long getShiftplanningId() {
        return shiftplanningId;
    }

    public void setShiftplanningId(Long shiftplanningId) {
        this.shiftplanningId = shiftplanningId;
    }

    public Integer getShiftIndex() {
        return shiftIndex;
    }

    public void setShiftIndex(Integer shiftIndex) {
        this.shiftIndex = shiftIndex;
    }

    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }
}
