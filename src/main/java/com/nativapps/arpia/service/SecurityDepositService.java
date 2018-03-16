package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.SecurityDepositResponse;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface SecurityDepositService extends Service {

    /**
     * Get security deposits list.
     *
     * @param search
     * @param start
     * @param size
     * @param orderBy
     * @param orderType
     * @return security deposits list
     */
    List<SecurityDepositResponse> getAll(String search,
            Integer start, Integer size, String orderBy, OrderType orderType);

    /**
     * Get security deposit from the given ID.
     *
     * @param id security deposit ID
     * @return security deposit data
     */
    SecurityDepositResponse get(Long id);

}
