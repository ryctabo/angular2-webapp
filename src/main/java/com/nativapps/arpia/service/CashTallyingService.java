package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.CashTallyingRequest;
import com.nativapps.arpia.model.dto.CashTallyingResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;
import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface CashTallyingService extends Service {
    
    /**
     * Returns all the cash tallying in a paginated way
     * 
     * @param start Initial index
     * @param size List size
     * @param from initial date to filter results
     * @param to final date to filter results 
     * @return Cash tallying list
     */
    ListResponse<CashTallyingResponse> getAll(int start, int size, Calendar from, 
            Calendar to);
    
    /**
     * Return a specific cash tallying register
     * 
     * @param id Cash tallying ID
     * @return Cash Tallying register
     */
    CashTallyingResponse get(Long id);
    
    /**
     * Create a new cash tallying register
     * 
     * @param data Cash tallying information
     * @param userInfo User information who create the register
     * @return Created cash tallying
     */
    CashTallyingResponse add(CashTallyingRequest data, UserInfo userInfo);
}
