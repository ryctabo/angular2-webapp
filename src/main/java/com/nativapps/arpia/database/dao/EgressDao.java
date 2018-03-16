package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Egress;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.rest.bean.EgressType;

import java.util.Calendar;
import java.util.List;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
public interface EgressDao extends DataAccessObject<Egress, Long> {

    /**
     * Get all Discount entities from the given data.
     *
     * @param search       search param
     * @param from         start date
     * @param to           end date
     * @param start        initial element to get
     * @param size         size of list
     * @param orderBy      property to ordering
     * @param type         order type ASC or DESC
     * @param userId       the egress user id
     * @param closureState the egress closure state
     * @param egressType   the egress type
     * @return list of egresses
     */
    List<Egress> findAll(Long userId, Boolean closureState, EgressType egressType,
                         String search, Calendar from, Calendar to,
                         int start, int size, String orderBy, OrderType type);

    /**
     * Get count all elements from the given data.
     *
     * @param search       search param
     * @param from         start date
     * @param to           end date
     * @param userId       the egress user id
     * @param closureState the egress closure state
     * @param egressType   the egress type
     * @return the number of egresses
     */
    long count(Long userId, Boolean closureState, EgressType egressType,
               String search, Calendar from, Calendar to);

}
