package com.nativapps.arpia.service;

import com.nativapps.arpia.database.entity.MessengerFB;
import com.nativapps.arpia.model.dto.SimpleMessengerRequest;
import com.nativapps.arpia.model.dto.SimpleMessengerResponse;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface MessengerFBService extends Service {

    /**
     * Returns a messenger list depending on type.
     *
     * @param customerId Customer ID
     * @param type MessengerFB type
     * @return Messenger blacklist
     */
    List<SimpleMessengerResponse> getAll(Long customerId, MessengerFB.Type type);

    /**
     * Returns a messenger by id and depending on type.
     *
     * @param customerId Customer ID
     * @param messengerId Messenger ID
     * @param type MessengerFB type
     * @return Searched messenger
     */
    SimpleMessengerResponse get(Long customerId, Long messengerId, 
            MessengerFB.Type type);

    /**
     * Add a new messenger in the list.
     *
     * @param id Customer ID
     * @param data Required messenger information
     * @param type MessengerFB type
     * @return Added messenger
     */
    SimpleMessengerResponse add(Long id, SimpleMessengerRequest data, 
            MessengerFB.Type type);

    /**
     * Delete a messenger in the list
     *
     * @param id Customer ID
     * @param messengerId Messenger ID
     * @param type MessengerFB type
     * @return Deleted messenger
     */
    SimpleMessengerResponse delete(Long id, Long messengerId, MessengerFB.Type type);
}
