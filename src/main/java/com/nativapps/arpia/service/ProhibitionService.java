package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ProhibitionRequest;
import com.nativapps.arpia.model.dto.ProhibitionResponse;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@gmail.com>
 * @version 1.0
 */
public interface ProhibitionService extends Service {

    /**
     * Create new restriction from the given data.
     *
     * @param messengerId messenger ID
     * @param request restriction information
     * @return the restriction with ID
     */
    ProhibitionResponse add(Long messengerId, ProhibitionRequest request);

    /**
     * Get a list with all restrictions.
     *
     * @return restriction list.
     */
    List<ProhibitionResponse> getAll();

    /**
     * Get a restriction information from the given ID.
     *
     * @param messengerId messenger ID.
     * @return restriction information.
     */
    ProhibitionResponse get(Long messengerId);

    /**
     * Update the restriction data from the information provided.
     *
     * @param id messenger ID.
     * @param request restriction information.
     *
     * @return operation updated.
     */
    ProhibitionResponse update(Long id, ProhibitionRequest request);
}
