package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.model.adapter.DateAdapter;
import com.nativapps.arpia.model.adapter.SimpleTimeAdapter;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlRootElement
@XmlDiscriminatorNode("@@type")
@XmlSeeAlso({
        DailyRepeatData.class,
        WeeklyRepeatData.class,
        MonthlyRepeatData.class,
        YearlyRepeatData.class
})
public class RepeatData {

    @XmlJavaTypeAdapter(SimpleTimeAdapter.class)
    protected Date time;

    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Calendar startDate;

    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Calendar endDate;

    public RepeatData() {
    }

    public RepeatData(Date time, Calendar startDate, Calendar endDate) {
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

}
