package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.SecurityDepositPaymentRequest;
import com.nativapps.arpia.model.dto.SecurityDepositPaymentResponse;
import java.util.Date;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface SecurityDepositPaymentService extends Service {

    /**
     * Create a new security deposit payment to user.
     *
     * @param request security deposit payment object
     * @param receivedId
     * @param ownerId User assigned to this payment.
     * @return security deposit payment data
     */
    SecurityDepositPaymentResponse add(SecurityDepositPaymentRequest request,
            Long receivedId);

    /**
     * Get security deposits payment list.
     *
     * @param search
     * @param start
     * @param size
     * @param from
     * @param to
     * @param orderBy
     * @param orderType
     * @return security deposits payment list
     */
    ListResponse<SecurityDepositPaymentResponse> getAll(String search,
            Integer start, Integer size, Date from, Date to, String orderBy,
            OrderType orderType);

    /**
     * Get security deposit payment from the given ID.
     *
     * @param id security deposit payment ID
     * @return security deposit payment data
     */
    SecurityDepositPaymentResponse get(Long id);
}
