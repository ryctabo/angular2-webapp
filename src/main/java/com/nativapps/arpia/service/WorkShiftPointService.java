package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.WorkShiftPointRequest;
import com.nativapps.arpia.model.dto.WorkShiftPointResponse;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface WorkShiftPointService extends Service {

    /**
     * Get all work shift points from the given parameters.
     *
     * @param mapPointId the map point ID
     * @param start      index of first element to get
     * @param size       list size
     * @return work shift points
     */
    ListResponse<WorkShiftPointResponse> get(Long mapPointId, int start, int size);

    /**
     * Get work shift point from the given ID.
     *
     * @param id the work shift point ID
     * @return work shift point
     */
    WorkShiftPointResponse get(Long id);

    /**
     * Create new work shift point from the given data.
     *
     * @param workShiftPoint data
     * @return work shift point created
     */
    WorkShiftPointResponse add(WorkShiftPointRequest workShiftPoint);

    /**
     * Update work shift point from the given data and ID.
     *
     * @param id             the work shift point ID
     * @param workShiftPoint data
     * @return work shift point updated
     */
    WorkShiftPointResponse update(Long id, WorkShiftPointRequest workShiftPoint);

}
