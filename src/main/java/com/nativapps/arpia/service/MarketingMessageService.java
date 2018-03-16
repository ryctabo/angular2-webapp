package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MarketingMessageRequest;
import com.nativapps.arpia.model.dto.MarketingMessageResponse;
import com.nativapps.arpia.rest.UserInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface MarketingMessageService extends Service {

    /**
     * Return all the marketing message entities
     * 
     * @param start Initial index
     * @param size List size
     * @param customerId Customer ID to filter
     * @return Marketing message list response
     */
    ListResponse<MarketingMessageResponse> getAll(int start, int size, 
            Long customerId);
    
    /**
     * Return a specific marketing message by provided ID
     * 
     * @param id Marketing message ID
     * @return Marketing message response
     */
    MarketingMessageResponse get(Long id);
    
    /**
     * Create a new marketing message
     * 
     * @param data Marketing message information
     * @param userInfo User information
     * @return Created marketing message
     */
    MarketingMessageResponse add(MarketingMessageRequest data, UserInfo userInfo);
    
    /**
     * Delete a marketing message
     * 
     * @param id Marketing message ID
     * @return Deleted marketing message
     */
    MarketingMessageResponse delete(Long id);
}
