package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.DomicileExecute;
import com.nativapps.arpia.database.entity.DomicileStatus;
import com.nativapps.arpia.database.entity.Gender;
import com.nativapps.arpia.model.OrderType;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.5.0
 */
public interface DomicileExecuteDao extends DataAccessObject<DomicileExecute, Long> {

    /**
     * Get all executions of home service from the given customer ID.
     *
     * @param customerId customer ID
     * @return list of home service executions
     */
    List<DomicileExecute> findByCustomerId(long customerId);

    /**
     * Get all domicile executes from the given parameters.
     *
     * @param state          domicile status
     * @param startDate      start date
     * @param endDate        end date
     * @param customerId     the customer ID
     * @param customerGender the customer gender
     * @param auto           true, if you need get all domiciles assigned automatically
     * @param operatorId     the ID of user operator
     * @param dispatcherId   the ID of user dispatcher
     * @param operationId    the operation ID
     * @param canceled       true, if you need get all domiciles canceled
     * @param start          index of first element to get
     * @param size           size of list
     * @param orderBy        property to ordering
     * @param orderType      ASC to order ascending and DESC to order descending
     * @return list of domicile executes
     */
    List<DomicileExecute> findAll(DomicileStatus state, Calendar startDate, Calendar endDate, Long customerId,
                                  Gender customerGender, Boolean auto, Long operatorId, Long dispatcherId,
                                  Long operationId, Boolean canceled, int start, int size, String orderBy,
                                  OrderType orderType);

    /**
     * Get count all domicile executes that comply with the given params.
     *
     * @param state          domicile status
     * @param startDate      start date
     * @param endDate        end date
     * @param customerId     the customer ID
     * @param customerGender the customer gender
     * @param auto           true, if you need get all domiciles assigned automatically
     * @param operatorId     the ID of user operator
     * @param dispatcherId   the ID of user dispatcher
     * @param operationId    the operation ID
     * @param canceled       true, if you need get all domiciles canceled
     * @return count of domiciles executes
     */
    long count(DomicileStatus state, Calendar startDate, Calendar endDate, Long customerId, Gender customerGender,
               Boolean auto, Long operatorId, Long dispatcherId, Long operationId, Boolean canceled);

    /**
     * Returns all the domiciles execute by messenger ID in a time range
     *
     * @param startDate   initial date
     * @param endDate     final date
     * @param messengerId messenger ID to filter
     * @return domicile execute list
     */
    List<DomicileExecute> findAll(Calendar startDate, Calendar endDate, Long messengerId);

    /**
     * Get domicile executes from the given neighborhood ID parameter.
     *
     * @param neighborhood the neighborhood ID
     * @param start        index of first element to get
     * @param size         list size
     * @return the list of domicile executes
     */
    List<DomicileExecute> findByNeighborhood(long neighborhood, int start, int size);

    /**
     * Get count all domicile execute that comply with the neighborhood ID parameter.
     *
     * @param neighborhood the neighborhood ID
     * @return count
     */
    long countByNeighborhood(long neighborhood);
}
