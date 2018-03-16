package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class GoodStandingResponse extends GoodStandingData {

    private Long id;

    private Calendar registerDate;

    private OperationResponse operation;

    private MessengerResponse messenger;
    
    private Boolean approved;
    
    private Calendar approvalDate; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public OperationResponse getOperation() {
        return operation;
    }

    public void setOperation(OperationResponse operation) {
        this.operation = operation;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Calendar getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Calendar approvalDate) {
        this.approvalDate = approvalDate;
    }
}
