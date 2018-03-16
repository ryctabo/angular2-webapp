package com.nativapps.arpia.model.dto;

import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class MSettlementResponse extends MSettlementData {

    private Long id;

    private MessengerResponse messenger;

    private List<DomicileExecuteResponse> domiciles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessengerResponse getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerResponse messenger) {
        this.messenger = messenger;
    }

    public List<DomicileExecuteResponse> getDomiciles() {
        return domiciles;
    }

    public void setDomiciles(List<DomicileExecuteResponse> domiciles) {
        this.domiciles = domiciles;
    }
}
