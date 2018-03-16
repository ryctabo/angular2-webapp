package com.nativapps.arpia.model.adapter;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class DateRange {
    
    public enum Unit {
        DAYS, WEEKS, MONTHS, YEARS
    }
    
    private int count;
    
    private Unit unit;

    public DateRange() {
    }

    public DateRange(int count, Unit unit) {
        this.count = count;
        this.unit = unit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public Calendar getCalendar() {
        Calendar date = Calendar.getInstance();
        switch (unit) {
            case DAYS:
                date.add(Calendar.DATE, count * -1);
                break;
            case WEEKS:
                date.add(Calendar.WEEK_OF_MONTH, count * -1);
                break;
            case MONTHS:
                date.add(Calendar.MONTH, count * -1);
                break;
            case YEARS:
                date.add(Calendar.YEAR, count * -1);
                break;
        }
        return date;
    }

    @Override
    public String toString() {
        return count + "." + unit;
    }
}
