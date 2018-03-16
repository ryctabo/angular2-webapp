package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ClosureData;
import com.nativapps.arpia.model.dto.EgressData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.EgressType;

import java.util.Calendar;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
public interface EgressService extends Service {

    /**
     * Get all egress information from the given data.
     *
     * @param userId     the egress user id
     * @param closured   the egress closure state
     * @param egressType the egress type
     * @param search     egress concept
     * @param from       start date
     * @param to         end data
     * @param start      first element to get
     * @param size       list size
     * @param orderBy    property to ordering
     * @param type       order type ASC or DESC
     * @return list of egresses
     */
    ListResponse<EgressData> get(Long userId, Boolean closured, EgressType egressType,
                                 String search, Calendar from, Calendar to,
                                 int start, int size, String orderBy, OrderType type);

    /**
     * Get egress information from the given ID.
     *
     * @param id egress ID
     * @return egress information
     */
    EgressData get(Long id);

    /**
     * add a new egress.
     *
     * @param request  egress data
     * @param userInfo user information
     * @return egress saved
     */
    EgressData add(EgressData request, UserInfo userInfo);

    /**
     * Update information of egress from the given data and egress ID.
     *
     * @param id       egress ID
     * @param request  egress data
     * @param userInfo user information
     * @return egress updated
     */
    EgressData update(Long id, EgressData request, UserInfo userInfo);

    /**
     * Update closure information of egress from the given data and egress ID.
     *
     * @param id          egress ID
     * @param closureData closure data
     * @param userInfo    user information
     * @return egress updated
     */
    EgressData closure(Long id, ClosureData closureData, UserInfo userInfo);
}
