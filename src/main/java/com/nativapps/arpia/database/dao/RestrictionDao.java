package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Restriction;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public interface RestrictionDao extends DataAccessObject<Restriction, Long> {

    /**
     * Gets messenger restriction information by messenger id provided.
     *
     * @param messengerId
     * @return vehicle list from messenger
     */
    Restriction findAllByMessengerId(Long messengerId);
}
