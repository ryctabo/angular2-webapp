package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class MarketingMessageRequest extends MarketingMessageData {
    
    private Long customerId;
    
    public MarketingMessageRequest() {
    }
    
    public MarketingMessageRequest(Long customerId, String observations, 
            Boolean visit, Boolean call) {
        super(observations, visit, call);
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
