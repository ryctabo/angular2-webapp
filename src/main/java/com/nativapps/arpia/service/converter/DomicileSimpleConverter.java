package com.nativapps.arpia.service.converter;

import com.nativapps.arpia.database.entity.Domicile;
import com.nativapps.arpia.database.entity.LatLng;
import com.nativapps.arpia.database.entity.Location;
import com.nativapps.arpia.database.entity.Neighborhood;
import com.nativapps.arpia.model.dto.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.2
 */
public class DomicileSimpleConverter implements DtoConverter<Domicile, DomicileRequest, DomicileResponse> {

    private static DomicileSimpleConverter INSTANCE = new DomicileSimpleConverter();

    /**
     * Don't let anyone instantiate this class.
     */
    private DomicileSimpleConverter() { }

    public static DomicileSimpleConverter instance() {
        return INSTANCE;
    }

    @Override
    public Domicile convertToEntity(DomicileRequest data) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public DomicileResponse convertToDto(Domicile domicile) {
        DomicileResponse response = new DomicileResponse();
        fill(response, domicile);
        return response;
    }

    /**
     * Take the data that has the object of <strong>Domicile</strong>
     * and place them inside the object <strong>DomicileResponse</strong>.
     *
     * @param response domicile response
     * @param domicile domicile data
     */
    public void fill(DomicileResponse response, Domicile domicile) {
        response.setId(domicile.getId());
        response.setType(domicile.getType());
        response.setPrice(domicile.getPrice());
        response.setMessengerGain(domicile.getMessengerGain());
        response.setCreateDate(domicile.getCreateDate());

        response.setCustomer(domicile.getCustomerId());
        response.setOperation(domicile.getOperationId());

        response.setUser(UserSimpleConverter.instance()
                .convertToDto(domicile.getUser()));

        List<LocationResponse> locations = new ArrayList<>();
        for (Location l : domicile.getLocations()) {
            LatLngData coordinate = null;
            if (l.getCoordinates() != null) {
                LatLng c = l.getCoordinates();
                coordinate = new LatLngData(c.getLatitude(), c.getLongitude());
            }
            NeighborhoodResponse neighborhood = null;
            if (l.getNeighborhood() != null) {
                Neighborhood n = l.getNeighborhood();
                neighborhood = new NeighborhoodResponse(n.getId(), n.getName());
            }

            LocationResponse location = new LocationResponse();
            location.setIndex(l.getId().getIndex());
            location.setAddress(l.getAddress());
            location.setReference(l.getReference());
            location.setCoordinates(coordinate);
            location.setTask(l.getTask());
            location.setNeighborhood(neighborhood);

            locations.add(location);
        }
        response.setLocations(locations);
    }
}
