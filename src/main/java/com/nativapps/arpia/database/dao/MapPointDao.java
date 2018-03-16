package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.MapPoint;
import com.nativapps.arpia.model.OrderType;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface MapPointDao extends DataAccessObject<MapPoint, Long> {

    /**
     * Get all map points from the given parameters.
     *
     * @param search  value to search
     * @param start   index of first element to get
     * @param size    size of list
     * @param orderBy property to ordering
     * @param type    ASC to order ascending and DESC to order descending
     * @return list of map points
     */
    List<MapPoint> findAll(String search, int start, int size, String orderBy, OrderType type);

    /**
     * Get total size of the filtered list.
     *
     * @param search value to search
     * @return total size
     */
    long count(String search);

}
