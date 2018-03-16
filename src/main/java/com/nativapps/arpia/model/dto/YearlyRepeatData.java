package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.model.adapter.DateAdapter;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlRootElement
@XmlDiscriminatorValue("YearlyRepeat")
public class YearlyRepeatData extends RepeatData {

    private Integer every;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Calendar dateOfYear;

    public YearlyRepeatData() {
    }

    public YearlyRepeatData(Integer every, Calendar dateOfYear, Date time,
                            Calendar startDate, Calendar endDate) {
        super(time, startDate, endDate);
        this.every = every;
        this.dateOfYear = dateOfYear;
    }

    public Integer getEvery() {
        return every;
    }

    public void setEvery(Integer every) {
        this.every = every;
    }

    public Calendar getDateOfYear() {
        return dateOfYear;
    }

    public void setDateOfYear(Calendar dateOfYear) {
        this.dateOfYear = dateOfYear;
    }

}
