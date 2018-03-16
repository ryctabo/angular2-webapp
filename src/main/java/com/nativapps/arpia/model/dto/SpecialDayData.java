package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.TypeDay;
import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SpecialDayData {

    protected Calendar date;

    protected TypeDay typeDay;

    public SpecialDayData() {
    }

    public SpecialDayData(Calendar date, TypeDay typeDay) {
        this.date = date;
        this.typeDay = typeDay;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public TypeDay getTypeDay() {
        return typeDay;
    }

    public void setTypeDay(TypeDay typeDay) {
        this.typeDay = typeDay;
    }

}
