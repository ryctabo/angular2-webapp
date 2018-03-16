package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.MessengerFB;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface MessengerFBDao extends DataAccessObject<MessengerFB, 
        MessengerFB.MessengerFBPK> {

    List<MessengerFB> findAllByType(Long customerId, MessengerFB.Type type);

    MessengerFB find(Long customerId, Long messengerId, MessengerFB.Type type);
}
