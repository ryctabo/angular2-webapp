package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class SimpleMessengerData {

    private Long messengerId;

    public SimpleMessengerData() {
    }
    
    public SimpleMessengerData(Long messengerId) {
        this.messengerId = messengerId;
    }
    
    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }
}
