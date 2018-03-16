package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.CurriculumVitae;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface CurriculumVitaeDao extends DataAccessObject<CurriculumVitae, Long> {

    /**
     * Get curriculum vitae from the given messenger ID.
     *
     * @param messengerId the messenger ID
     * @return the curriculum vitae object
     */
    CurriculumVitae findByMessengerId(Long messengerId);

}
