package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.model.OrderType;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 1.2
 */
public class MobileNumberBean {

    @QueryParam("start")
    private int start;

    @QueryParam("size")
    private Integer size;

    @QueryParam("operation")
    private Long operation;

    @QueryParam("available")
    private Boolean available;

    @QueryParam("orderBy")
    private String orderBy;

    @QueryParam("orderType")
    private OrderType orderType;

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

    public Long getOperation() {
        return operation;
    }

    public void setOperation(Long operation) {
        this.operation = operation;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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
