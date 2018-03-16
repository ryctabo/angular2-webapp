package com.nativapps.arpia.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftplanningResponse extends ShiftplanningData {
    
    private Long id;
    
    private OperationResponse operation;
    
    private List<ShiftResponse> shifts;

    public ShiftplanningResponse() {
        this.shifts = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationResponse getOperation() {
        return operation;
    }

    public void setOperation(OperationResponse operation) {
        this.operation = operation;
    }

    public List<ShiftResponse> getShifts() {
        return shifts;
    }

    public void setShifts(List<ShiftResponse> shifts) {
        this.shifts = shifts;
    }
}
