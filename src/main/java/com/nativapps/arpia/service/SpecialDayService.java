package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.SpecialDayRequest;
import com.nativapps.arpia.model.dto.SpecialDayResponse;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface SpecialDayService extends Service {

    /**
     * Create new special day from the given data.
     *
     * @param dataRequest information of special day entity
     * @return saved entity
     */
    SpecialDayResponse add(SpecialDayRequest dataRequest);

    /**
     * Get special day information by id provided.
     *
     * @param id entity identifier to search
     * @return searched entity
     */
    SpecialDayResponse get(Long id);

    /**
     * Get all specials days.
     *
     * @return list of special day entities
     */
    List<SpecialDayResponse> getAll();

    /**
     * Update information of special day by id provided.
     *
     * @param id entity identifier to update
     * @param dataRequest information of special day entity
     * @return saved entity
     */
    SpecialDayResponse update(Long id, SpecialDayRequest dataRequest);

    /**
     * Delete special day entity by id provided
     *
     * @param id entity identifier to delete
     * @return deleted entity
     */
    SpecialDayResponse delete(Long id);
}
