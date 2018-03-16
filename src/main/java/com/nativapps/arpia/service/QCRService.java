package com.nativapps.arpia.service;

import com.nativapps.arpia.database.entity.QCR;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.QCRRequest;
import com.nativapps.arpia.model.dto.QCRResponse;
import java.util.Calendar;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public interface QCRService extends Service {

    /**
     * Returns a list with the questions, complaints or requests by
     * provided Customer ID.
     *
     * @param start       the index of first element to get
     * @param size        list size
     * @param customerId  the customer ID
     * @param messengerId messenger ID to filter
     * @param from        filter since date speficied
     * @param to          filter until date specified
     * @param state QCR status
     * @return QCR list
     */
    ListResponse<QCRResponse> getAll(int start, int size, Long customerId, Long messengerId, Calendar from, Calendar to, QCR.Status state);

    /**
     * Returns a list with the questions, complaints or requests by ID and
     * customer ID
     *
     * @param customerId the customer ID
     * @param index      the INDEX of QCR
     * @return QCR entity
     */
    QCRResponse get(Long customerId, Integer index);

    /**
     * Add a new question, complaint or request inconvenient.
     *
     * @param data QCR inconvenient
     * @return Added QCR
     */
    QCRResponse add(QCRRequest data);

    /**
     * Add counterpart version to customer question, complaint or request inconvenient
     *
     * @param customerId the customer ID
     * @param index      the INDEX of QCR
     * @param data       QCR counterpart version
     * @return Update QCR
     */
    QCRResponse counterpart(Long customerId, Integer index, QCRRequest data);

    /**
     * Added admin evaluation and solution to customer question, complaint or request
     *
     * @param customerId the customerID
     * @param index      the INDEX of QCR
     * @param data       Admin evaluation and solution
     * @return Update QCR
     */
    QCRResponse solution(Long customerId, Integer index, QCRRequest data);

    /**
     * Cancel a QCR.
     *
     * @param customerId the customer ID
     * @param index      the INDEX of QCR
     * @return Cancel QCR
     */
    QCRResponse cancel(Long customerId, Integer index);
}
