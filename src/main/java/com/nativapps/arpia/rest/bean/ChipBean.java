package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.database.entity.ChipType;

import javax.ws.rs.QueryParam;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ChipBean {

    @QueryParam("messenger")
    private Long messengerId;

    @QueryParam("type")
    private ChipType type;

    @QueryParam("start")
    private int start;

    @QueryParam("size")
    private Integer size;

    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }

    public ChipType getType() {
        return type;
    }

    public void setType(ChipType type) {
        this.type = type;
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
