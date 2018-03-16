package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Payment;
import com.nativapps.arpia.model.OrderType;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public interface PaymentDao extends DataAccessObject<Payment, Long> {

    /**
     * Sums all the payments to get the real value of the debt.
     *
     * @param debtId debt ID
     * @param value the debt value
     * @return The real value of the debt.
     */
    Double getRealValue(Long debtId, Double value);

    /**
     * Get all Payment entities from the given data.
     *
     * @param debtId debt ID
     * @return list of payments
     */
    List<Payment> findAll(Long debtId);

    /**
     * Get all Payment entities from the given data.
     *
     * @param debtId debt ID
     * @param from start date
     * @param to end date
     * @param start initial element to get
     * @param size size of list
     * @param orderBy property to ordering
     * @param type order type ASC or DESC
     *
     * @return list of payments
     */
    List<Payment> findAll(Long debtId, Calendar from, Calendar to, int start,
            int size, String orderBy, OrderType type);

    /**
     * Get count all elements from the given data.
     *
     * @param debtId debt ID
     * @param from start date
     * @param to end date
     *
     * @return the number of payments
     */
    long getCount(Long debtId, Calendar from, Calendar to);
}
