package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class LocationResponse extends LocationData {

    private NeighborhoodResponse neighborhood;

    public NeighborhoodResponse getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(NeighborhoodResponse neighborhood) {
        this.neighborhood = neighborhood;
    }
}
