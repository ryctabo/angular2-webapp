package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.DomicileReview;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface DomicileReviewDao extends DataAccessObject<DomicileReview, Long> {
    
    /**
     * Returns all the domicile executes reviews
     * 
     * @param start initial index
     * @param size list size
     * @param dexecId domicile execute ID
     * @return domicile executes reviews list
     */
    List<DomicileReview> findAll(int start, int size, Long dexecId);
    
    /**
     * Return the find all result count
     * 
     * @param dexecId domicile execute ID 
     * @return result count
     */
    long findAllCount(Long dexecId);
}
