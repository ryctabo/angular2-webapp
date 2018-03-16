package com.nativapps.arpia.rest.bean;

import javax.ws.rs.QueryParam;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PenaltyBean extends GenericFilterBean {

    @QueryParam("messenger")
    private Long messenger;

    public Long getMessenger() {
        return messenger;
    }

    public void setMessenger(Long messenger) {
        this.messenger = messenger;
    }

}
