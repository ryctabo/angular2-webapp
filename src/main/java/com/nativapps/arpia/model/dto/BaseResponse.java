package com.nativapps.arpia.model.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class BaseResponse {

    private Long id;
    
    private Integer baseDelivery;
    
    private Integer baseReturn;
    
    private Float amount;
    
    private Calendar registerDate;
    
    private MessengerResponse messenger;
    
    private OperationResponse operation;
    
    private List<BaseRecordData> records;

    public BaseResponse() {
        this.records = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BaseRecordData> getRecords() {
        return records;
    }

    public void setRecords(List<BaseRecordData> records) {
        this.records = records;
    }

    public Integer getBaseDelivery() {
        return baseDelivery;
    }

    public void setBaseDelivery(Integer baseDelivery) {
        this.baseDelivery = baseDelivery;
    }

    public Integer getBaseReturn() {
        return baseReturn;
    }

    public void setBaseReturn(Integer baseReturn) {
        this.baseReturn = baseReturn;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }

    public OperationResponse getOperation() {
        return operation;
    }

    public void setOperation(OperationResponse operation) {
        this.operation = operation;
    }
}
