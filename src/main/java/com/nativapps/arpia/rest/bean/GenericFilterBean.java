package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.model.OrderType;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class GenericFilterBean {

    @QueryParam("search")
    protected String search;

    @QueryParam("start")
    protected int start;

    @QueryParam("size")
    protected Integer size;

    @QueryParam("orderBy")
    protected String orderBy;

    @QueryParam("orderType")
    protected OrderType orderType;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

}
