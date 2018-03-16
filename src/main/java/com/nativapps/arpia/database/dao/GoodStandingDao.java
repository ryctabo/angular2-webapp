package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.GoodStanding;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface GoodStandingDao extends DataAccessObject<GoodStanding, Long>{

    List<GoodStanding> findAll(int start, int size, Long messengerId);
    
    long findAllCount(Long messengerId);
}
