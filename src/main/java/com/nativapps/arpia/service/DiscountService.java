package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.DiscountRequest;
import com.nativapps.arpia.model.dto.DiscountResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public interface DiscountService extends Service {

    /**
     * Get all discount information from the given data.
     *
     * @param name    discount name
     * @param active  true, if you need actives discounts
     * @param start   first element to get
     * @param size    list size
     * @param orderBy property to ordering
     * @param type    order type ASC or DESC
     * @return list of discount
     */
    ListResponse<DiscountResponse> get(String name, Boolean active, int start, int size, String orderBy, OrderType type);

    /**
     * Get discount information from the given ID.
     *
     * @param id discount ID
     * @return discount information
     */
    DiscountResponse get(Long id);

    /**
     * Add a new discount.
     *
     * @param request  discount data
     * @param userInfo user information
     * @return discount saved
     */
    DiscountResponse add(DiscountRequest request, UserInfo userInfo);

    /**
     * Update information of discount from the given data and discount ID.
     *
     * @param id       discount ID
     * @param request  discount data
     * @param userInfo user information
     * @return discount updated
     */
    DiscountResponse update(Long id, DiscountRequest request, UserInfo userInfo);

    /**
     * Delete Discount from the given ID.
     *
     * @param id discount ID
     * @return discount deleted
     */
    DiscountResponse delete(Long id);
}
