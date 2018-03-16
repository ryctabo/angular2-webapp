package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.MobileNumber;
import com.nativapps.arpia.model.OrderType;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
public interface MobileNumberDao extends DataAccessObject<MobileNumber, MobileNumber.MobileNumberPK> {

    /**
     * Get the last registered mobile number from the given operation ID.
     *
     * @param operationId operation ID
     * @return mobile number data
     */
    MobileNumber findLast(long operationId);

    /**
     * Get mobile numbers list.
     *
     * @param operationId operationId
     * @param available true, if you need the mobile number is not assigned to a
     * messenger
     * @param start initial index
     * @param size list size
     * @param orderBy property to order
     * @param orderType asc or desc
     *
     * @return mobile number list.
     */
    List<MobileNumber> findAll(Long operationId, Boolean available, int start,
            int size, String orderBy, OrderType orderType);

    /**
     * Add mobile numbers to database.
     *
     * @param mobileNumbers mobile numbers to save
     * @return true, if saved all mobile numbers
     */
    boolean save(MobileNumber... mobileNumbers);

}
