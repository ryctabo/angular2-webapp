package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.ShiftCheckKey;

import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftCheckKeyDao extends DataAccessObject<ShiftCheckKey, Long> {

    /**
     * Return all the shift check by check ID
     *
     * @param shiftCheckId check ID
     * @return check key list
     */
    List<ShiftCheckKey> findAll(Long shiftCheckId);

    /**
     * Return the key result count
     *
     * @param shiftCheckId check ID
     * @return result count
     */
    long count(Long shiftCheckId);

    /**
     * find a check key entity by check ID and string key
     *
     * @param checkId check ID
     * @param key string key to search
     * @return check key entity
     */
    ShiftCheckKey find(Long checkId, String key);
}
