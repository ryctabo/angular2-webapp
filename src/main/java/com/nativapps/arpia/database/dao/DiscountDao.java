package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Discount;
import com.nativapps.arpia.database.entity.DiscountChangeLog;
import com.nativapps.arpia.model.OrderType;

import java.util.List;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.3.0
 */
public interface DiscountDao extends DataAccessObject<Discount, Long> {

    /**
     * Save discount and change log in the database.
     *
     * @param discount discount entity
     * @param log      change log entity
     * @return discount saved
     */
    Discount saveDiscountAndLog(Discount discount, DiscountChangeLog log);

    /**
     * Get all Discount entities from the given data.
     *
     * @param search  search param
     * @param active  true, if you need filtered list with discount active
     * @param start   initial element to get
     * @param size    size of list
     * @param orderBy property to ordering
     * @param type    order type ASC or DESC
     * @return list of discounts
     */
    List<Discount> findAll(String search, Boolean active, int start, int size, String orderBy, OrderType type);

    /**
     * Get count all elements from the given data.
     *
     * @param search search param
     * @param active true, if you need filtered list with discount active
     * @return total number of discounts that meet the conditions
     */
    long getCount(String search, Boolean active);


    /**
     * Get changes count of discount from the given ID.
     *
     * @param discountId the discount ID
     * @return count of changes
     */
    int getCountChanges(long discountId);

    /**
     * Get count used of discount from the given ID.
     *
     * @param discountId the discount ID
     * @return count used
     */
    int getCountUsed(long discountId);

}
