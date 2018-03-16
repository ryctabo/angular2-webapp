package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.database.entity.StockType;
import java.util.Calendar;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class StockBean {

    @QueryParam("start")
    private int start;

    @QueryParam("size")
    private Integer size;

    @QueryParam("type")
    private StockType type;

    @QueryParam("from")
    private Calendar startDate;

    @QueryParam("to")
    private Calendar endDate;

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

    public StockType getType() {
        return type;
    }

    public void setType(StockType type) {
        this.type = type;
    }

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

}
