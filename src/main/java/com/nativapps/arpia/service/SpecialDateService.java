package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.SpecialDateRequest;
import com.nativapps.arpia.model.dto.SpecialDateResponse;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface SpecialDateService extends Service {

    /**
     * Get all special dates.
     *
     * @return list of special dates
     */
    List<SpecialDateResponse> get();

    /**
     * Get a special date from the given ID.
     *
     * @param id special date ID
     * @return special date data
     */
    SpecialDateResponse get(Long id);

    /**
     * Add a new special date from the information provided.
     *
     * @param request special date data
     * @return special date saved
     */
    SpecialDateResponse add(SpecialDateRequest request);

    /**
     * Update special date from the given ID.
     *
     * @param id special date ID
     * @param request special date data
     * @return special date updated
     */
    SpecialDateResponse update(Long id, SpecialDateRequest request);

    /**
     * Delete special date from the given ID.
     *
     * @param id special date ID
     * @return special date deleted
     */
    SpecialDateResponse delete(Long id);

}
