package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.PenaltyRequest;
import com.nativapps.arpia.model.dto.PenaltyResponse;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface PenaltyService extends Service {

    /**
     * Create new penalty from the given data.
     *
     * @param dataRequest information of penalty entity
     * @param assignedById user who authorized the penalty
     * @return saved entity
     */
    PenaltyResponse add(PenaltyRequest dataRequest, Long assignedById);

    /**
     * Get penalty information by id provided.
     *
     * @param id entity identifier to search
     * @return searched entity
     */
    PenaltyResponse get(Long id);

    /**
     * Get all penalties.
     *
     * @param messenger messenger id for search.
     * @param start
     * @param size
     * @return list of penalty entities
     */
    ListResponse<PenaltyResponse> getAll(Long messenger, int start, int size);

    /**
     * Update information of penalty by id provided.
     *
     * @param id entity identifier to update
     * @param dataRequest information of penalty entity
     * @param assignedById
     * @return saved entity
     */
    PenaltyResponse update(Long id, PenaltyRequest dataRequest, Long assignedById);

    /**
     * Delete penalty entity by id provided
     *
     * @param id entity identifier to delete
     * @return deleted entity
     */
    PenaltyResponse delete(Long id);

}
