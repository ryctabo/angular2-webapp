package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftData {
    
    protected Calendar startTime;
    
    protected Calendar endTime;
    
    protected Integer count;

    public ShiftData() {
    }

    public ShiftData(Calendar startTime, Calendar endTime, Integer count) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.count = count;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
