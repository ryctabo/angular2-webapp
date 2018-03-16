package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Monitoring;
import com.nativapps.arpia.model.OrderType;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface MonitoringDao extends DataAccessObject<Monitoring, Long> {

    /**
     * Get all monitoring entities from the given data.
     *
     * @param startDate start date
     * @param endDate end date
     * @param domicileId domicile ID
     * @param start initial element to get
     * @param size size of list
     * @param orderBy property to ordering
     * @param type order type ASC or DESC
     *
     * @return list of monitoring
     */
    List<Monitoring> findAll(Calendar startDate, Calendar endDate,
            Long domicileId, int start, int size, String orderBy, OrderType type);

    /**
     * Get count all elements from the given data
     *
     * @param startDate start date
     * @param endDate end date
     * @param domicileId domicile ID
     * 
     * @return count of elements
     */
    long getCount(Calendar startDate, Calendar endDate, Long domicileId);

}
