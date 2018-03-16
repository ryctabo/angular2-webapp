package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Penalty;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface PenaltyDao extends DataAccessObject<Penalty, Long> {

    /**
     *
     * Return a filter {@link Penalty} list.
     *
     * @param messenger messenger id for search.
     * @param start initial index
     * @param size list size
     * @return
     */
    public List<Penalty> findAll(Long messenger, int start, int size);
    /**
     * Return the find all result count 
     * 
     * @param messenger messenger id for search.
     * @return 
     */
    public long findAllCount(Long messenger) ;
}
