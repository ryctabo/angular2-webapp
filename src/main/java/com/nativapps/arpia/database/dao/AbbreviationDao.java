package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Abbreviation;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public interface AbbreviationDao extends DataAccessObject<Abbreviation, Long> {

    /**
     * Get a abbreviation from the given short text.
     *
     * @param shortText short text value
     * @return abbreviation data
     */
    Abbreviation findByShortText(String shortText);

}
