package com.nativapps.arpia.rest.bean;

import java.util.Calendar;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DomicileBean extends GenericFilterBean {

    @QueryParam("customer")
    private Long customerId;

    @QueryParam("operation")
    private Long operationId;

    @QueryParam("date")
    private Calendar date;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
