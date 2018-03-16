package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.PeaceAndSafetyRequest;
import com.nativapps.arpia.model.dto.PeaceAndSafetyResponse;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public interface PeaceAndSafetyService extends Service {

    /**
     * Get a list with all Peace And Safey.
     *
     * @param start pagination init
     * @param size pagination size
     * @return Peace And Safey list.
     */
    public List<PeaceAndSafetyResponse> getAll(int start, int size);

    /**
     * Get a Peace And Safey information from the given ID.
     *
     * @param id Peace and safety ID.
     * @return Peace and safety information.
     */
    public PeaceAndSafetyResponse get(Long id);

    /**
     * Create a new Peace and safety from the information provided.
     *
     * @param request Peace and safety information.
     * @return Peace and safety created.
     */
    public PeaceAndSafetyResponse add(PeaceAndSafetyRequest request);

    /**
     * Update the Peace and safety data from the information provided.
     *
     * @param id Peace and safety ID.
     * @param request Peace and safety information.
     *
     * @return Peace and safety updated.
     */
    PeaceAndSafetyResponse update(Long id, PeaceAndSafetyRequest request);

    /**
     * Delete a Peace and safety information from the given ID.
     *
     * @param id Peace and safety ID.
     * @return the information of Peace and safety deleted.
     */
    PeaceAndSafetyResponse delete(Long id);
}
