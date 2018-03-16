package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.FestiveDayRequest;
import com.nativapps.arpia.model.dto.FestiveDayResponse;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface FestiveDayService extends Service {

    /**
     * Get all festive days.
     *
     * @return list of festive days
     */
    List<FestiveDayResponse> get();

    /**
     * Get a festive day from the given ID.
     *
     * @param id festive day ID
     * @return festive day data
     */
    FestiveDayResponse get(Long id);

    /**
     * Add a new festive day from the given data.
     *
     * @param request festive day data
     * @return festive day saved
     */
    FestiveDayResponse add(FestiveDayRequest request);

    /**
     * Update festive day from the given ID and data.
     *
     * @param id festive day ID
     * @param request festive day data
     * @return festive date updated
     */
    FestiveDayResponse update(Long id, FestiveDayRequest request);

    /**
     * Delete a festive day from the given ID.
     *
     * @param id festive day ID
     * @return festive day deleted
     */
    FestiveDayResponse delete(Long id);

}
