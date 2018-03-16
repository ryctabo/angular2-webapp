package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MonitoringRequest;
import com.nativapps.arpia.model.dto.MonitoringResponse;
import com.nativapps.arpia.rest.UserInfo;
import java.util.Calendar;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface MonitoringService extends Service {

    /**
     * Get all monitoring information from the given data.
     *
     * @param startDate start date
     * @param endDate end date
     * @param domicileId domicile ID
     * @param start first element to get
     * @param size list size
     * @param orderBy property to ordering
     * @param type order type ASC or DESC
     *
     * @return list of monitoring
     */
    ListResponse<MonitoringResponse> get(Calendar startDate, Calendar endDate,
            Long domicileId, int start, int size, String orderBy, OrderType type);

    /**
     * Get monitoring information from the given ID.
     *
     * @param id monitoring ID
     * @return monitoring information
     */
    MonitoringResponse get(Long id);

    /**
     * Add a new monitoring.
     *
     * @param request monitoring data
     * @param userInfo user information
     * @return monitoring saved
     */
    MonitoringResponse add(MonitoringRequest request, UserInfo userInfo);

    /**
     * Update information of monitoring from the given data and monitoring ID.
     *
     * @param id monitoring ID
     * @param request monitoring data
     * @param userInfo user information
     * @return monitoring updated
     */
    MonitoringResponse update(Long id, MonitoringRequest request, UserInfo userInfo);

    /**
     * Delete monitoring from the given ID.
     *
     * @param id monitoring ID
     * @return monitoring deleted
     */
    MonitoringResponse delete(Long id);

}
