package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.DiscountChangeLog;

import java.util.List;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public interface DiscountChangeLogDao extends DataAccessObject<DiscountChangeLog, Long> {

    /**
     * Get all Discount entities from the given data.
     *
     * @param discountId discount id
     * @return list of changes in discount
     */
    List<DiscountChangeLog> findAll(Long discountId);

    /**
     * Get count all elements from the given data.
     *
     * @param discountId discount id
     * @return count of changes in discount
     */
    long getCount(Long discountId);
}
