package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.GoodStandingRequest;
import com.nativapps.arpia.model.dto.GoodStandingResponse;
import com.nativapps.arpia.model.dto.ListResponse;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface GoodStandingService extends Service {
    
    /**
     * Return a list of messenger good standings in a paginate and filter way
     * 
     * @param start initial index
     * @param size list size
     * @param messengerId messenger ID to filter the request
     * @return good standing list
     */
    ListResponse<GoodStandingResponse> getAll(int start, int size, Long messengerId);
    
    /**
     * Return a specific good standing by provided ID
     * 
     * @param id good standing ID
     * @return searched good standing 
     */
    GoodStandingResponse get(Long id);
    
    /**
     * Create a new good standing request from the information provided
     * 
     * @param data good standing information
     * @return created good standing
     */
    GoodStandingResponse add(GoodStandingRequest data);
    
    /**
     * It approves a good standing request 
     * 
     * @param id good standing ID
     * @param data good standing information
     * @return approved good standing
     */
    GoodStandingResponse approve(Long id, GoodStandingRequest data);
    
    /**
     * It denies a good standing request
     * 
     * @param id good standing ID
     * @param data good standing information
     * @return denied good standing
     */
    GoodStandingResponse deny(Long id, GoodStandingRequest data);
}
