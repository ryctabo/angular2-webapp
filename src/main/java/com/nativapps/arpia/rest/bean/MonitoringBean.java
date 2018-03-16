package com.nativapps.arpia.rest.bean;

import java.util.Calendar;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MonitoringBean extends GenericFilterBean {

    @QueryParam("from")
    private Calendar startDate;

    @QueryParam("to")
    private Calendar endDate;

    @QueryParam("domicile")
    private Long domicileId;

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Long getDomicileId() {
        return domicileId;
    }

    public void setDomicileId(Long domicileId) {
        this.domicileId = domicileId;
    }

}
