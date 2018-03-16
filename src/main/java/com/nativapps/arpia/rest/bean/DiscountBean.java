package com.nativapps.arpia.rest.bean;

import javax.ws.rs.QueryParam;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class DiscountBean extends GenericFilterBean {

    @QueryParam("active")
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
