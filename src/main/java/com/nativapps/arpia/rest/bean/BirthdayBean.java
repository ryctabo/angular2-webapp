package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.model.adapter.BirthdayPeriod;

import javax.ws.rs.QueryParam;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class BirthdayBean {

    @QueryParam("to")
    private BirthdayPeriod to;

    @QueryParam("start")
    protected int start;

    @QueryParam("size")
    protected Integer size;

    public BirthdayPeriod getTo() {
        return to;
    }

    public void setTo(BirthdayPeriod to) {
        this.to = to;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
