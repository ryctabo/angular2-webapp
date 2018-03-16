package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.MessengerFB;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class MessengerFBDaoController extends EntityDao<MessengerFB, 
        MessengerFB.MessengerFBPK> implements MessengerFBDao {
    
    private static final MessengerFBDaoController INSTANCE
            = new MessengerFBDaoController();

    private MessengerFBDaoController() {
        super(MessengerFB.class);
    }
    
    public static MessengerFBDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<MessengerFB> findAllByType(Long customerId, 
            MessengerFB.Type type) {
        return executeNamedQueryForList("messengerFB.findAllByType", 
                new Parameter("customerId", customerId), 
                new Parameter("type", type));
    }

    @Override
    public MessengerFB find(Long customerId, Long messengerId, 
            MessengerFB.Type type) {
        return executeNamedQuery("messengerFB.find", 
                new Parameter("customerId", customerId), 
                new Parameter("messengerId", messengerId), 
                new Parameter("type", type));
    }
}
