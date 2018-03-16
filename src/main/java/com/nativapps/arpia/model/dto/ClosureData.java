package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.ClosuredType;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import java.util.Calendar;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
@XmlDiscriminatorNode("cType")
public class ClosureData {

    private UserResponse closuredBy;

    private ClosuredType type;

    private String observation;

    private Calendar date;

    public UserResponse getClosuredBy() {
        return closuredBy;
    }

    public void setClosuredBy(UserResponse closuredBy) {
        this.closuredBy = closuredBy;
    }

    public ClosuredType getType() {
        return type;
    }

    public void setType(ClosuredType type) {
        this.type = type;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
