package com.nativapps.arpia.model.dto;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
@XmlDiscriminatorNode("type")
public class LocationData {

    private Integer index;

    private String address;

    private String reference;

    private LatLngData coordinates;

    private String task;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LatLngData getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLngData coordinates) {
        this.coordinates = coordinates;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

}
