package com.nativapps.arpia.rest.bean;

import javax.ws.rs.QueryParam;
import java.util.Calendar;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class EgressBean extends GenericFilterBean {

    @QueryParam("from")
    private Calendar from;

    @QueryParam("to")
    private Calendar to;

    @QueryParam("user")
    private Long userId;

    @QueryParam("type")
    private EgressType type;

    @QueryParam("closured")
    private Boolean closured;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EgressType getType() {
        return type;
    }

    public void setType(EgressType type) {
        this.type = type;
    }

    public Boolean getClosured() {
        return closured;
    }

    public void setClosured(Boolean closured) {
        this.closured = closured;
    }
}
