package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Base;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface BaseDao extends DataAccessObject<Base, Long> {

    /**
     * Search de base registers
     * 
     * @param start Initial index
     * @param size List size
     * @param messengerId Filter by messenger ID
     * @param debt Filter bases debts
     * @return Base registers list
     */
    List<Base> findAll(int start, int size, Long messengerId, Boolean debt);
    
    /**
     * Return result count of the named query base.findAll
     * 
     * @param messengerId Messenger filter ID
     * @param debt Filter bases debts
     * @return result count
     */
    Long findAllCount(Long messengerId, Boolean debt);
    
    /**
     * Returns the last messenger base register.
     * 
     * @param messengerId Messenger ID
     * @return The last messenger base register
     */
    Base findLastRegister(Long messengerId);
}
