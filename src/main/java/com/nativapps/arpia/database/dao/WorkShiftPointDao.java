package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.WorkShiftPoint;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface WorkShiftPointDao extends DataAccessObject<WorkShiftPoint, Long> {

    /**
     * Get all work shift point from the given parameters.
     *
     * @param mapPointId the map point ID
     * @param start      index of first element to get
     * @param size       list size
     * @return work shift point elements
     */
    List<WorkShiftPoint> findAll(Long mapPointId, int start, int size);

    /**
     * Get count of element to get from the given parameter.
     *
     * @param mapPointId the map point ID
     * @return count of elements
     */
    long count(Long mapPointId);

}
