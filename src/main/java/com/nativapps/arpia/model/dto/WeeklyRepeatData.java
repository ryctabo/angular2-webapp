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
@XmlDiscriminatorValue("WeeklyRepeat")
public class WeeklyRepeatData extends RepeatData {

    private Integer every;

    private Boolean monday;

    private Boolean tuesday;

    private Boolean wednesday;

    private Boolean thursday;

    private Boolean friday;

    private Boolean saturday;

    private Boolean sunday;

    public WeeklyRepeatData() {
    }

    public WeeklyRepeatData(Integer every, Boolean monday, Boolean tuesday,
                            Boolean wednesday, Boolean thursday, Boolean friday,
                            Boolean saturday, Boolean sunday, Date time, Calendar startDate,
                            Calendar endDate) {
        super(time, startDate, endDate);
        this.every = every;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public Integer getEvery() {
        return every;
    }

    public void setEvery(Integer every) {
        this.every = every;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

}
