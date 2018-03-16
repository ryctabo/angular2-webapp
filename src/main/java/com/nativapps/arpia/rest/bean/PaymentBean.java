package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.model.OrderType;
import java.util.Calendar;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class PaymentBean{

    @QueryParam("from")
    private Calendar from;

    @QueryParam("to")
    private Calendar to;

    @QueryParam("start")
    private int start;

    @QueryParam("size")
    private Integer size;

    @QueryParam("orderBy")
    private String orderBy;

    @QueryParam("orderType")
    private OrderType orderType;

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
