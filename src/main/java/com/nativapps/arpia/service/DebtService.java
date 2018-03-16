package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ClosureData;
import com.nativapps.arpia.model.dto.DebtRequest;
import com.nativapps.arpia.model.dto.DebtResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;
import java.util.Calendar;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public interface DebtService extends Service {

    /**
     * Get all debt information from the given data.
     *
     * @param concept debt concept
     * @param from start date
     * @param to end data
     * @param start first element to get
     * @param size list size
     * @param orderBy property to ordering
     * @param type order type ASC or DESC
     * @param closureState the debt closure state
     *
     * @return list of debts
     */
    ListResponse<DebtResponse> get(String concept, Calendar from,
            Calendar to, int start, int size, String orderBy, OrderType type,
            Boolean closureState);

    /**
     * Get debt information from the given ID.
     *
     * @param id debt ID
     * @return debt information
     */
    DebtResponse get(Long id);

    /**
     * add a new debt.
     *
     * @param request debt data
     * @param userInfo user information
     * @return debt saved
     */
    DebtResponse add(DebtRequest request, UserInfo userInfo);

    /**
     * Update information of debt from the given data and debt ID.
     *
     * @param id debt ID
     * @param request debt data
     * @param userInfo user information
     * @return debt updated
     */
    DebtResponse update(Long id, DebtRequest request, UserInfo userInfo);

    /**
     * Update closure information of debt from the given data and debt ID.
     *
     * @param id debt ID
     * @param closureData closure data
     * @param userInfo user information
     * @return debt updated
     */
    DebtResponse updateDebtClosure(Long id, ClosureData closureData, UserInfo userInfo);

}
