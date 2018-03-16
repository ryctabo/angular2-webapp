package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.DailyOperation;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface DailyOperationDao extends DataAccessObject<DailyOperation, Long> {

    /**
     * Returns all the daily operation in a paginated way
     * 
     * @param start Initial index
     * @param size List size
     * @param operationId Operation ID to filter
     * @return Daily operation list
     */
    List<DailyOperation> findAll(int start, int size, Long operationId);
    
    /**
     * Returns the find all result count 
     * 
     * @param operationId Operation ID to filter
     * @return Result count
     */
    long findAllCount(Long operationId);
    
    /**
     * Return the last daily operation
     * 
     * @param operationId Operation ID to filter
     * @return daily operation register
     */
    DailyOperation last(Long operationId);
    
    /**
     * Return a specific daily operation by ID and operation ID
     * 
     * @param operationId Operation ID
     * @param id Daily operation ID
     * @return Daily operation register
     */
    DailyOperation find(Long operationId, Long id);
}
