package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.PaymentData;
import com.nativapps.arpia.rest.UserInfo;
import java.util.Calendar;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public interface PaymentService extends Service {

    /**
     * Get all payment information from the given data.
     *
     * @param debtId debt ID
     * @param from start date
     * @param to end data
     * @param start first element to get
     * @param size list size
     * @param orderBy property to ordering
     * @param type order type ASC or DESC
     * @return list of payments
     */
    ListResponse<PaymentData> get(Long debtId, Calendar from, Calendar to,
            int start, int size, String orderBy, OrderType type);

    /**
     * add a new payment to the debt.
     *
     * @param debtId debt ID
     * @param request payment data
     * @param userInfo user information
     * @return payment saved
     */
    PaymentData addPayment(Long debtId, PaymentData request, UserInfo userInfo);

    /**
     * Get payment information from the given ID.
     *
     * @param debtId debt ID
     * @param index payment index
     * @return payment information
     */
    PaymentData get(Long debtId, Integer index);
}
