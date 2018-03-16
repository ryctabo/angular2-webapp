package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Weekdays;
import com.nativapps.arpia.model.adapter.SimpleTimeAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ScheduleData {

    private Weekdays weekday;

    @XmlJavaTypeAdapter(SimpleTimeAdapter.class)
    private Date opening;

    @XmlJavaTypeAdapter(SimpleTimeAdapter.class)
    private Date closing;

    @XmlJavaTypeAdapter(SimpleTimeAdapter.class)
    private Date breakOpening;

    @XmlJavaTypeAdapter(SimpleTimeAdapter.class)
    private Date breakClosing;

    public Weekdays getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekdays weekday) {
        this.weekday = weekday;
    }

    public Date getOpening() {
        return opening;
    }

    public void setOpening(Date opening) {
        this.opening = opening;
    }

    public Date getClosing() {
        return closing;
    }

    public void setClosing(Date closing) {
        this.closing = closing;
    }

    public Date getBreakOpening() {
        return breakOpening;
    }

    public void setBreakOpening(Date breakOpening) {
        this.breakOpening = breakOpening;
    }

    public Date getBreakClosing() {
        return breakClosing;
    }

    public void setBreakClosing(Date breakClosing) {
        this.breakClosing = breakClosing;
    }
}
