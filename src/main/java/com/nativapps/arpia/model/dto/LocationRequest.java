package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class LocationRequest extends LocationData {

    private Long neighborhood;

    public Long getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Long neighborhood) {
        this.neighborhood = neighborhood;
    }
}
