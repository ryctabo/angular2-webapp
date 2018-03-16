package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Bonus;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface BonusDao extends DataAccessObject<Bonus, Long> {
    
    /**
     * Returns a bonus list by customer ID
     * 
     * @param start Initial index
     * @param size List size
     * @param customerId Customer ID
     * @return Bonus list
     */
    public List<Bonus> findAllByCustomerIdPag(int start, int size, 
            Long customerId);
    
    /**
     * Returns a bonus by his own ID and his customer ID
     * 
     * @param customerId Customer ID
     * @param id Bonus ID
     * @return Bonus entity
     */
    public Bonus findByCustomerId(Long customerId, Long id);
    
    /**
     * Returns the last added bonus to customer
     * 
     * @param customerId Customer ID
     * @return Last added bonus
     */
    public Bonus findLastBonusByCustomerId(Long customerId);
    
    /**
     * Returns the result count of method findAllByCustomerId
     * 
     * @param customerId Customer ID
     * @return result count
     */
    public Long findAllCount(Long customerId);
}
