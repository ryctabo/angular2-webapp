package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftResponse extends ShiftData {
    
    private Integer index;
    
    private Long shiftplanningId;

    public ShiftResponse() {
    }

    public ShiftResponse(Integer index, Long shiftplanningId, Calendar startTime, 
            Calendar endTime, Integer count) {
        super(startTime, endTime, count);
        this.index = index;
        this.shiftplanningId = shiftplanningId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getShiftplanningId() {
        return shiftplanningId;
    }

    public void setShiftplanningId(Long shiftplanningId) {
        this.shiftplanningId = shiftplanningId;
    }
}
