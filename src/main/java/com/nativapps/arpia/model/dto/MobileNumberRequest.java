package com.nativapps.arpia.model.dto;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
public class MobileNumberRequest extends MobileNumberData {

    private Integer quantity;

    private Long messenger;

    private Long operationId;

    public MobileNumberRequest() {
    }

    public MobileNumberRequest(Integer quantity, Long messenger,
            Long operationId, Integer index) {
        super(index);
        this.quantity = quantity;
        this.messenger = messenger;
        this.operationId = operationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getMessenger() {
        return messenger;
    }

    public void setMessenger(Long messenger) {
        this.messenger = messenger;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

}
