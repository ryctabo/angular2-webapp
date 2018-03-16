package com.nativapps.arpia.model.dto;

import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftplanningRequest extends ShiftplanningData {

    private Long operationId;
    
    private List<ShiftRequest> shifts;

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public List<ShiftRequest> getShifts() {
        return shifts;
    }

    public void setShifts(List<ShiftRequest> shifts) {
        this.shifts = shifts;
    }
}
