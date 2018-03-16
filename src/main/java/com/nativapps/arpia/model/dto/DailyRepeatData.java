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
@XmlDiscriminatorValue("DailyRepeat")
public class DailyRepeatData extends RepeatData {

    private Integer every;

    public DailyRepeatData() {
    }

    public DailyRepeatData(Integer every, Date time, Calendar startDate,
                           Calendar endDate) {
        super(time, startDate, endDate);
        this.every = every;
    }

    public Integer getEvery() {
        return every;
    }

    public void setEvery(Integer every) {
        this.every = every;
    }

}
