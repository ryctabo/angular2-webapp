package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.CashRequest;
import com.nativapps.arpia.model.dto.CashResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface CashService extends Service {

    /**
     * Returns all the cash register in a paginated way
     * 
     * @param start Initial index
     * @param size List size
     * @return Cash register list
     */
    ListResponse<CashResponse> getAll(int start, int size);
    
    /**
     * Returns a cash register by ID
     * 
     * @param id Cash register ID
     * @return Cash register
     */
    CashResponse get(Long id);
    
    /**
     * Create a new cash register
     * 
     * @param data Cash register information
     * @param userInfo User information who made the register
     * @return Created cash register
     */
    CashResponse add(CashRequest data, UserInfo userInfo);
}
