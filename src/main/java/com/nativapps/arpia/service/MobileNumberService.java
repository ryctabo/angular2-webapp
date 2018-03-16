package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.MobileNumberRequest;
import com.nativapps.arpia.model.dto.MobileNumberResponse;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
public interface MobileNumberService extends Service {

    /**
     * Create new mobile number from the given data.
     *
     * @param mobileNumber
     */
    public void add(MobileNumberRequest mobileNumber);

    /**
     * Get all mobile numbers.
     *
     * @param operationId the operation ID
     * @param available true, if you need the mobile number is not assigned to a
     * messenger
     * @param start first element to get
     * @param size size of list
     * @param orderBy mobile number property to ordering
     * @param orderType order type
     *
     * @return mobile numbers list
     */
    public List<MobileNumberResponse> getAll(Long operationId, Boolean available,
            int start, int size, String orderBy, OrderType orderType);

    /**
     * Updates the corresponding mobile number.
     *
     * @param index index of mobile number
     * @param data mobile number data
     * @return mobile number updated.
     */
    public MobileNumberResponse update(Integer index, MobileNumberRequest data);

}
