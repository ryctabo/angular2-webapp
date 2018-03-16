package com.nativapps.arpia.rest.bean;

import javax.ws.rs.QueryParam;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class FrequentBean {

    @QueryParam("customer")
    private Long customerId;

    @QueryParam("start")
    private int start;

    @QueryParam("size")
    private Integer size;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
