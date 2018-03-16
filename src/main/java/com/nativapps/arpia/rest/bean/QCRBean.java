package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.database.entity.QCR;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class QCRBean {

    @QueryParam("start")
    private int start;

    @QueryParam("size")
    private Integer size;
    
    @QueryParam("messengerId")
    private Long messengerId;
    
    @QueryParam("from")
    private Calendar from;
    
    @QueryParam("to")
    private Calendar to;

    @QueryParam("status")
    private QCR.Status status;

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

    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }

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

    public QCR.Status getStatus() {
        return status;
    }

    public void setStatus(QCR.Status status) {
        this.status = status;
    }
}
