package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Prohibition;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 *
 * @version 1.0
 */
public interface ProhibitionDao extends DataAccessObject<Prohibition, Long> {

    /**
     * Gets messenger restriction information by messenger id provided.
     *
     * @param messengerId
     * @return vehicle list from messenger
     */
    Prohibition findAllByMessengerId(Long messengerId);

}
