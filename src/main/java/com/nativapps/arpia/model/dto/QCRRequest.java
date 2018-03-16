package com.nativapps.arpia.model.dto;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class QCRRequest extends QCRData {

    private Long customer;
    
    private Long messenger;

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Long getMessenger() {
        return messenger;
    }

    public void setMessenger(Long messenger) {
        this.messenger = messenger;
    }
}
