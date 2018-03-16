package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.CurriculumVitaeRequest;
import com.nativapps.arpia.model.dto.CurriculumVitaeResponse;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface CurriculumVitaeService extends Service {

    /**
     * Get curriculum vitae data from the given messenger ID.
     *
     * @param messengerId the messenger ID
     * @return curriculum vitae object
     */
    CurriculumVitaeResponse getByMessengerId(Long messengerId);

    /**
     * Get curriculum vitae data from the given ID.
     *
     * @param id the curriculum vitae ID
     * @return curriculum vitae object
     */
    CurriculumVitaeResponse get(Long id);

    /**
     * Update the curriculum vitae data from the information provided.
     *
     * @param id the curriculum vitae ID
     * @param cv the curriculum vitae data
     *
     * @return curriculum vitae object
     */
    CurriculumVitaeResponse update(Long id, CurriculumVitaeRequest cv);

}
