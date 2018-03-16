package com.nativapps.arpia.model.dto;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class EvaluationRequest extends EvaluationData {

    private Long messenger;

    public Long getMessenger() {
        return messenger;
    }

    public void setMessenger(Long messenger) {
        this.messenger = messenger;
    }
}
