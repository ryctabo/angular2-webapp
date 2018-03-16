package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.AbbreviationRequest;
import com.nativapps.arpia.model.dto.AbbreviationResponse;

import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public interface AbbreviationService extends Service {

    /**
     * Get all abbreviations.
     *
     * @return list of abbreviation entities
     */
    List<AbbreviationResponse> get();

    /**
     * Get abbreviation information by id provided.
     *
     * @param id entity identifier to search
     * @return searched entity
     */
    AbbreviationResponse get(Long id);

    /**
     * Get abbreviation from the given short text.
     *
     * @param shortText the short text value
     * @return the abbreviation
     */
    AbbreviationResponse get(String shortText);

    /**
     * Create new abbreviation from the given data.
     *
     * @param data information of abbreviation entity
     * @return saved entity
     */
    AbbreviationResponse add(AbbreviationRequest data);

    /**
     * Update information of abbreviation by id provided.
     *
     * @param id   entity identifier to update
     * @param data information of abbreviation entity
     * @return saved entity
     */
    AbbreviationResponse update(Long id, AbbreviationRequest data);

    /**
     * Delete abbreviation entity by id provided
     *
     * @param id entity identifier to delete
     * @return deleted entity
     */
    AbbreviationResponse delete(Long id);
}
