package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.MapPointType;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlRootElement
@XmlDiscriminatorNode("otype")
public class MapPointData {

    protected String name;

    protected LatLngData coordinates;

    protected Integer coverage;

    protected MapPointType type;

    protected List<ScheduleData> schedules;

    protected List<PricingData> pricings;

    public MapPointData() {
        this.schedules = new ArrayList<>();
        this.pricings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLngData getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLngData coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getCoverage() {
        return coverage;
    }

    public void setCoverage(Integer coverage) {
        this.coverage = coverage;
    }

    public MapPointType getType() {
        return type;
    }

    public void setType(MapPointType type) {
        this.type = type;
    }

    public List<ScheduleData> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleData> schedules) {
        this.schedules = schedules;
    }

    public List<PricingData> getPricings() {
        return pricings;
    }

    public void setPricings(List<PricingData> pricings) {
        this.pricings = pricings;
    }
}
