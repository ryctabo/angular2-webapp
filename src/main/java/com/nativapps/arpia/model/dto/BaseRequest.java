package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class BaseRequest {
    
    private Long messenger;
    
    private Long operation;
    
    private Integer count;
    
    private String observations;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
