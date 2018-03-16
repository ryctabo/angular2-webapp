package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.TypeDay;
import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SpecialDayResponse extends SpecialDayData {

    private Long id;

    private String day;

    public SpecialDayResponse() {
    }

    public SpecialDayResponse(Long id, String day, Calendar date,
            TypeDay typeDay) {
        super(date, typeDay);
        this.id = id;
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
