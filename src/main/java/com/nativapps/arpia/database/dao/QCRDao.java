package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.QCR;
import java.util.Calendar;

import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public interface QCRDao extends DataAccessObject<QCR, QCR.QCRPK> {

    /**
     * Return a list with the questions, complaints or requests from the given parameters.
     *
     * @param start      the index of first element to get
     * @param size       size of list
     * @param customerId the customer ID
     * @param messengerId messenger ID to filter
     * @param from       filter since date speficied
     * @param to         filter until date specified
     * @param state      status of QCR
     * @return list of QCR data
     */
    List<QCR> findAll(int start, int size, Long customerId, Long messengerId, Calendar from, Calendar to, QCR.Status state);

    /**
     * Get count of list from the given parameters.
     *
     * @param customerId the customer ID
     * @param messengerId messenger ID to filter
     * @param from       filter since date speficied
     * @param to         filter until date specified
     * @param state      status of QCR
     * @return count all element in the list
     */
    long count(Long customerId, Long messengerId, Calendar from, Calendar to, QCR.Status state);

}
