package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MarketingObsRequest;
import com.nativapps.arpia.model.dto.MarketingObsResponse;
import com.nativapps.arpia.rest.UserInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface MarketingObsService extends Service {
    
    /**
     * Returns a paginated marketing observation list
     * 
     * @param start Initial index
     * @param size List size
     * @param customerId Customer ID
     * @return Marketing observation list
     */
    ListResponse<MarketingObsResponse> getAll(int start, int size, Long customerId);
    
    /**
     * Returns a specific marketing observation by ID
     * 
     * @param id Marketing observation ID
     * @return Marketing observation entity
     */
    MarketingObsResponse get(Long id);
    
    /**
     * Create a new marketing observation information
     * 
     * @param data Marketing obsevation information
     * @param userInfo User information that create the observation
     * @return Added marketing information
     */
    MarketingObsResponse add(MarketingObsRequest data, UserInfo userInfo);
    
    /**
     * Updates a marketing observation 
     * 
     * @param id Marketing observation ID
     * @param data Marketing observation information
     * @param userInfo User information that update the observation 
     * @return Updated marketing observation
     */
    MarketingObsResponse update(Long id, MarketingObsRequest data, UserInfo userInfo);
    
    /**
     * Delete a marketing observation
     * 
     * @param id Marketing observation ID
     * @return Deleted marketing observation
     */
    MarketingObsResponse delete(Long id);
}
