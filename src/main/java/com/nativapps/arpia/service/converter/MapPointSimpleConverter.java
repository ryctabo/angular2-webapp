package com.nativapps.arpia.service.converter;

import com.nativapps.arpia.database.entity.LatLng;
import com.nativapps.arpia.database.entity.MapPoint;
import com.nativapps.arpia.model.dto.LatLngData;
import com.nativapps.arpia.model.dto.MapPointResponse;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MapPointSimpleConverter implements DtoConverter<MapPoint, Void, MapPointResponse> {

    private static final MapPointSimpleConverter INSTANCE = new MapPointSimpleConverter();

    /**
     * Don't let anyone instantiate this class.
     */
    private MapPointSimpleConverter() { }

    public static MapPointSimpleConverter instance() {
        return INSTANCE;
    }

    @Override
    public MapPoint convertToEntity(Void data) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public MapPointResponse convertToDto(MapPoint entity) {
        MapPointResponse response = new MapPointResponse();

        response.setId(entity.getId());
        response.setName(entity.getName());

        LatLng co = entity.getCoordinates();
        response.setCoordinates(new LatLngData(co.getLatitude(), co.getLongitude()));

        response.setCoverage(entity.getCoverage());
        response.setType(entity.getType());

        return response;
    }

}
