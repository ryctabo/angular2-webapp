package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.MarketingMessage;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface MarketingMessageDao extends DataAccessObject<MarketingMessage, Long> {

    /**
     * Return all marketing messages in a paginated way
     * 
     * @param start Initial index
     * @param size List size
     * @param customerId Customer ID to filter
     * @return Marketing messages list
     */
    List<MarketingMessage> findAll(int start, int size, Long customerId);
    
    /**
     * Return the find all result count
     * 
     * @param customerId Customer ID to filter
     * @return Result count
     */
    long findAllCount(Long customerId);
}
