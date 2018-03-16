package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Domicile;
import com.nativapps.arpia.model.OrderType;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public interface DomicileDao extends DataAccessObject<Domicile, Long> {

    /**
     * Get all home services from the given ID.
     *
     * @param customerId customer ID
     * @return list of home services
     */
    List<Domicile> findByCustomerId(long customerId);

    /**
     * Get all home services that meet the given criteria.
     *
     * @param customerId  the customer ID
     * @param operationId the operation ID
     * @param date        register date
     * @param start       initial home service
     * @param size        size of list
     * @param orderBy     attribute of ordering
     * @param type        ASC or DESC
     * @return list of home services
     */
    List<Domicile> findAll(Long customerId, Long operationId, Calendar date,
                           int start, int size, String orderBy, OrderType type);

    /**
     * Get all domiciles from the given parameters with count of domiciles frequent.
     *
     * @param customerId the customer ID
     * @param start      index of first element to get
     * @param size       size of list
     * @return list of object array with domicile and frequent.
     */
    List<Object[]> frequent(Long customerId, int start, int size);

    /**
     * Get list size filtered.
     *
     * @param customerId  the customer ID
     * @param operationId the operation ID
     * @param date        register date
     * @return list size
     */
    Long findAllCount(Long customerId, Long operationId, Calendar date);

}
