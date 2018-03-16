package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.DomicileReviewRequest;
import com.nativapps.arpia.model.dto.DomicileReviewResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface DomicileReviewService extends Service {

    /**
     * Returns all the domicile reviews
     *
     * @param start Initial index
     * @param size List size
     * @param dexecId Domicile execute ID
     * @return Domicile review list
     */
    ListResponse<DomicileReviewResponse> getAll(int start, int size, Long dexecId);

    /**
     * Return a specific domicile review by domicile execute ID
     *
     * @param id Domicile review to search
     * @return Domicile review
     */
    DomicileReviewResponse get(Long id);

    /**
     * Add a new domicile review
     *
     * @param data Domicile review information
     * @param userInfo User information
     * @return Added domicile review
     */
    DomicileReviewResponse add(DomicileReviewRequest data, UserInfo userInfo);

    /**
     * Updates a domicile review by id
     *
     * @param id Domicile review to update
     * @param data Domicile review information
     * @return Updated domicile review
     */
    DomicileReviewResponse update(Long id, DomicileReviewRequest data);
}
