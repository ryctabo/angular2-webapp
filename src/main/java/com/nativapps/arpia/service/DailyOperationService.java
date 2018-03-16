package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.DailyOperationResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface DailyOperationService extends Service {

    /**
     * Returns all the daily operations registers in a paginated way
     * 
     * @param start Initial index
     * @param size List size
     * @param operationId Operation ID to filter
     * @return Daily operation list
     */
    ListResponse<DailyOperationResponse> getAll(int start, int size, 
            Long operationId);
    
    /**
     * Returns a specific daily operation by ID provided
     * 
     * @param operationId Operation ID
     * @param id Daily operation ID
     * @return Searched daily operation
     */
    DailyOperationResponse get(Long operationId, Long id);
    
    /**
     * Start a daily operation
     * 
     * @param operationId Operation ID
     * @param userInfo User information
     * @return Opened daily operation object
     */
    DailyOperationResponse open(Long operationId, UserInfo userInfo);
    
    /**
     * Close a daily operation
     * 
     * @param operationId Operation ID
     * @param userInfo User information
     * @return Closed daily operation
     */
    DailyOperationResponse close(Long operationId, UserInfo userInfo);
}
