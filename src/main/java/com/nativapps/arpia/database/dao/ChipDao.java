package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Chip;
import com.nativapps.arpia.database.entity.ChipType;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface ChipDao extends DataAccessObject<Chip, Long> {

    /**
     * Get chips from the given parameters.
     *
     * @param messengerId the messenger ID
     * @param type        chip type
     * @param start       index of first element to get
     * @param size        list size
     * @return list of chips
     */
    List<Chip> findAll(Long messengerId, ChipType type, int start, int size);

    /**
     * Get total count of elements in the database from the given paramters.
     *
     * @param messengerId the messenger ID
     * @param type        chip type
     * @return count of elements
     */
    long count(Long messengerId, ChipType type);

}
