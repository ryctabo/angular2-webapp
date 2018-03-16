package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.RestrictionRequest;
import com.nativapps.arpia.model.dto.RestrictionResponse;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@gmail.com>
 * @version 1.0
 */
public interface RestrictionService extends Service {

    /**
     * Create a new restriction
     *
     * @param messengerId messenger ID
     * @param request restriction information
     * @return the restriction with ID
     */
    RestrictionResponse add(Long messengerId, RestrictionRequest request);

    /**
     * Get a list with all restrictions.
     *
     * @return restriction list.
     */
    List<RestrictionResponse> getAll();

    /**
     * Get a restriction information from the given ID.
     *
     * @param messengerId messenger ID.
     * @return restriction information.
     */
    RestrictionResponse get(Long messengerId);

    /**
     * Update the restriction data from the information provided.
     *
     * @param id messenger ID.
     * @param request restriction information.
     *
     * @return operation updated.
     */
    RestrictionResponse update(Long id, RestrictionRequest request);

    /**
     * Delete restriction the messenger.
     *
     * @param messengerId Messenger id
     * @return Deleted restriction
     */
    RestrictionResponse delete(Long messengerId);
}
