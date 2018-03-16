package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MapPointRequest;
import com.nativapps.arpia.model.dto.MapPointResponse;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface MapPointService extends Service {

    /**
     * Get all map points from the given parameters.
     *
     * @param search  value to search
     * @param start   index of first item to get
     * @param size    size of list
     * @param orderBy property to ordering
     * @param type    ASC to order ascending and DESC to order descending
     * @return map point list
     */
    ListResponse<MapPointResponse> get(String search, int start, int size, String orderBy, OrderType type);

    /**
     * Get a map point from the given ID.
     *
     * @param id map point ID
     * @return map point data
     */
    MapPointResponse get(Long id);

    /**
     * Save a new map point.
     *
     * @param mapPoint map point information
     * @return map point saved
     */
    MapPointResponse add(MapPointRequest mapPoint);

    /**
     * Update a map point from the given ID and data provided.
     *
     * @param id       the map point ID
     * @param mapPoint map point data
     * @return map point updated
     */
    MapPointResponse update(Long id, MapPointRequest mapPoint);

    /**
     * Delete a map point from the given ID
     *
     * @param id the map point ID
     * @return map point deleted
     */
    MapPointResponse delete(Long id);

}
