package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.BaseRequest;
import com.nativapps.arpia.model.dto.BaseResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface BaseService extends Service {

    /**
     * Returns the bases list registers
     * 
     * @param start Initial index
     * @param size List size
     * @param messengerId Filter the list by messengerId
     * @param debt Filter the list by debts
     * @return Base list registers
     */
    ListResponse<BaseResponse> getAll(int start, int size, Long messengerId, 
            Boolean debt);
    
    /**
     * Return a specific base register by ID
     * 
     * @param baseId Base ID
     * @return Base register
     */
    BaseResponse get(Long baseId);
    
    /**
     * Add a new base record or update by base ID.
     * 
     * @param baseId Base ID to update
     * @param data Base record information
     * @param userInfo User information
     * @return Added base register.
     */
    BaseResponse add(Long baseId, BaseRequest data, UserInfo userInfo);
}
