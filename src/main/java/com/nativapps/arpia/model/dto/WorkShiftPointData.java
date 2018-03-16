package com.nativapps.arpia.model.dto;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlDiscriminatorNode("type")
public class WorkShiftPointData {

    protected Calendar start;

    protected Calendar end;

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }
}
