package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Month;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class FestiveDayData {

    protected String name;

    protected Integer dayOfMonth;

    protected Month month;

    public FestiveDayData() {
    }

    public FestiveDayData(String name, Integer dayOfMonth, Month month) {
        this.name = name;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

}
