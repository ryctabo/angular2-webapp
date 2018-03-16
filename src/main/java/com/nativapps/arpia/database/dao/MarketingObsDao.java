package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.MarketingObs;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface MarketingObsDao extends DataAccessObject<MarketingObs, Long>{

    List<MarketingObs> findAll(int start, int size, Long customerId);
    
    long findAllCount(Long customerId);
}
