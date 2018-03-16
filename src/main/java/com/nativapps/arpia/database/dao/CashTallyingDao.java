package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.CashTallying;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface CashTallyingDao extends DataAccessObject<CashTallying, Long> {
    
    /**
     * Returns all the cash tallying registers in a paginated way
     * 
     * @param start Initial index
     * @param size List size
     * @param from initial date to filter results
     * @param to final date to filter results 
     * @return Cash tallying list
     */
    List<CashTallying> findAll(int start, int size, Calendar from, 
            Calendar to);
    
    /**
     * Return find all result count
     * 
     * @return result count
     * @param from initial date to filter results
     * @param to final date to filter results 
     */
    long findAllCount(Calendar from, Calendar to);
}
