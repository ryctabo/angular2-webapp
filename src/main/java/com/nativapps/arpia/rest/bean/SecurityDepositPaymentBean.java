package com.nativapps.arpia.rest.bean;

import java.util.Date;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SecurityDepositPaymentBean extends GenericFilterBean {

    @QueryParam("from")
    private Date from;
    
    @QueryParam("to")
    private Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

}
