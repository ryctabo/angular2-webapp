package com.nativapps.arpia.rest.bean;

import javax.ws.rs.QueryParam;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class ShiftPlanningBean {

    @QueryParam("start")
    private int start;

    @QueryParam("size")
    private int size;

    @QueryParam("type")
    private Type type;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        OPENED, CLOSED
    }
}
