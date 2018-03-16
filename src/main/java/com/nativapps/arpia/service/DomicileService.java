package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.DomicileFrequent;
import com.nativapps.arpia.model.dto.DomicileRequest;
import com.nativapps.arpia.model.dto.DomicileResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public interface DomicileService extends Service {

    /**
     * Get all domiciles.
     *
     * @param customerId  the customer ID
     * @param operationId the operation ID
     * @param date        register date
     * @param start       initial index
     * @param size        list size
     * @param orderBy     attribute to ordering
     * @param type        ASC or DESC
     * @return domiciles
     */
    ListResponse<DomicileResponse> getAll(Long customerId, Long operationId, Calendar date,
                                          int start, int size, String orderBy, OrderType type);

    /**
     * Returns the domicile data from the given ID.
     *
     * @param id the domicile ID
     * @return domicile data
     */
    DomicileResponse get(Long id);

    /**
     * Get all domiciles frequent from the given parameters.
     *
     * @param customerId the customer ID
     * @param start      the index of first element to get
     * @param size       list size
     * @return domiciles frequent
     */
    List<DomicileFrequent> getDomicilesFrequent(Long customerId, int start, int size);

    /**
     * Save the domicile from the given data.
     *
     * @param domicile data
     * @param userInfo user information
     * @return domicile saved
     */
    DomicileResponse add(DomicileRequest domicile, UserInfo userInfo);

    /**
     * Delete a domicile from the given ID.
     *
     * @param id the domicile ID
     * @return the domicile deleted
     */
    DomicileResponse delete(Long id);

}
