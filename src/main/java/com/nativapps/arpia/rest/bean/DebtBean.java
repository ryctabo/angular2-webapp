package com.nativapps.arpia.rest.bean;

import java.util.Calendar;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DebtBean extends GenericFilterBean{
    @QueryParam("from")
    private Calendar from;

    @QueryParam("to")
    private Calendar to;
    
    @QueryParam("closureState")
    private boolean closureState;

    public Calendar getFrom() {
        return from;
    }

    public void setFrom(Calendar from) {
        this.from = from;
    }

    public Calendar getTo() {
        return to;
    }

    public void setTo(Calendar to) {
        this.to = to;
    }

    public boolean isClosureState() {
        return closureState;
    }

    public void setClosureState(boolean closureState) {
        this.closureState = closureState;
    }
    
}
