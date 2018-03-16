package com.nativapps.arpia.model.dto;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlRootElement
@XmlDiscriminatorValue("MonthlyRepeat")
public class MonthlyRepeatData extends RepeatData {

    private Integer every;

    private Integer dayOfMonth;

    public MonthlyRepeatData() {
    }

    public MonthlyRepeatData(Integer every, Integer dayOfMonth, Date time,
                             Calendar startDate, Calendar endDate) {
        super(time, startDate, endDate);
        this.every = every;
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getEvery() {
        return every;
    }

    public void setEvery(Integer every) {
        this.every = every;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

}
