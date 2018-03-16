package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.MSettlement;

import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface MSettlementDao extends DataAccessObject<MSettlement, Long> {

    /**
     * Return all the messenger settlements
     *
     * @param start initial index
     * @param size list size
     * @param messengerId messenger ID to filter
     * @return messenger settlement list
     */
    List<MSettlement> findAll(int start, int size, Long messengerId);

    /**
     * Return the result count of the find all method
     *
     * @param messengerId messenger ID to filter
     * @return result count
     */
    long count(Long messengerId);
}
