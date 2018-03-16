package com.nativapps.arpia.model.dto;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class DomicileRequest extends DomicileData {

    private List<LocationRequest> locations;

    public List<LocationRequest> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationRequest> locations) {
        this.locations = locations;
    }
}
