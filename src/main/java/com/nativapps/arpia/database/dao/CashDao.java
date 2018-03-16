package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Cash;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface CashDao extends DataAccessObject<Cash, Long> {

    /**
     * Returns all cash register in a paginated way
     *
     * @param start Initial index
     * @param size List size
     * @return Cash list
     */
    List<Cash> findAll(int start, int size);

    long findAllCount();

    Cash lastRegister();
}
