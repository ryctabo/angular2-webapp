package com.nativapps.arpia.rest.bean;

import javax.ws.rs.QueryParam;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
public class MessengerBean extends GenericFilterBean {

    @QueryParam("dismissal")
    private Boolean dismissal;

    @QueryParam("category")
    private Integer category;
    
    @QueryParam("retire")
    private Boolean retire;

    public Boolean getDismissal() {
        return dismissal;
    }

    public void setDismissal(Boolean dismissal) {
        this.dismissal = dismissal;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getRetire() {
        return retire;
    }

    public void setRetire(Boolean retire) {
        this.retire = retire;
    }

}
