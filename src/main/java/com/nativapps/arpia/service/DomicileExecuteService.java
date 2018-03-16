package com.nativapps.arpia.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.nativapps.arpia.database.entity.DomicileStatus;
import com.nativapps.arpia.database.entity.Gender;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.CancelData;
import com.nativapps.arpia.model.dto.DomicileExecuteRequest;
import com.nativapps.arpia.model.dto.DomicileExecuteResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;

import java.io.FileNotFoundException;
import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.2.0
 */
public interface DomicileExecuteService extends Service {

    /**
     * Get all home services executes from the given parameters.
     *
     * @param state          domicile status
     * @param startDate      start date
     * @param endDate        end date
     * @param customerId     the customer ID
     * @param customerGender the customer gender
     * @param auto           true, if you need get all domiciles assigned automatically
     * @param operatorId     the ID of user operator
     * @param dispatcherId   the ID of user dispatcher
     * @param operationId    the operation ID
     * @param canceled       true, if you need get all domiciles canceled
     * @param start          index of first element to get
     * @param size           size of list
     * @param orderBy        property to ordering
     * @param orderType      ASC to order ascending and DESC to order descending
     * @return list response of domicile execute response
     */
    ListResponse<DomicileExecuteResponse> getAll(DomicileStatus state, Calendar startDate, Calendar endDate,
                                                 Long customerId, Gender customerGender, Boolean auto, Long operatorId,
                                                 Long dispatcherId, Long operationId, Boolean canceled,
                                                 int start, int size, String orderBy, OrderType orderType);

    /**
     * Return a domicile execute from the given ID.
     *
     * @param id domicile execute ID
     * @return domicile execute data
     */
    DomicileExecuteResponse get(Long id);

    /**
     * Save a new domicile execute data.
     *
     * @param request  domicile execute data
     * @param userInfo user information
     * @return domicile execute with ID
     */
    DomicileExecuteResponse add(DomicileExecuteRequest request, UserInfo userInfo);

    /**
     * Cancel a domicile execute from the given ID.
     *
     * @param id           domicile execute ID
     * @param cancelReason cancel reason
     * @return domicile execute canceled
     */
    DomicileExecuteResponse cancel(Long id, CancelData cancelReason);

    /**
     * Finish a domicile execute from the given ID.
     *
     * @param id domicile execute ID
     * @return domicile execute finished
     */
    DomicileExecuteResponse finish(Long id);

    /**
     * Generates an ordeer for the dispatched domicile execute.
     *
     * @param id the ID of the domicile execute
     * @return The pdf document
     * @throws FileNotFoundException If it doesn't find the file
     * @throws DocumentException     If have problems to get the PdfWriter instance
     */
    Document generateOrder(Long id) throws FileNotFoundException, DocumentException;

}
