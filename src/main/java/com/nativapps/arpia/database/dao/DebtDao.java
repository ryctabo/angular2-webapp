package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Debt;
import com.nativapps.arpia.model.OrderType;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public interface DebtDao extends DataAccessObject<Debt, Long> {

    /**
     * Get all debt entities from the given data.
     *
     * @param search search param
     * @param from start date
     * @param to end date
     * @param start initial element to get
     * @param size size of list
     * @param orderBy property to ordering
     * @param type order type ASC or DESC
     * @param closureState the debts closure state
     *
     * @return list of debts
     */
    List<Debt> findAll(String search, Calendar from, Calendar to, int start,
            int size, String orderBy, OrderType type, Boolean closureState);

    /**
     * Get count all elements from the given data.
     *
     * @param search search param
     * @param from start date
     * @param to end date
     * @param closureState the debts closure state
     *
     * @return the number of debts
     */
    long getCount(String search, Calendar from, Calendar to,
            Boolean closureState);
}
