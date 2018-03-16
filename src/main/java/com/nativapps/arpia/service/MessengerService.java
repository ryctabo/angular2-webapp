package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.InventoryInfoResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MessengerRequest;
import com.nativapps.arpia.model.dto.MessengerResponse;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public interface MessengerService extends Service {

    /**
     * Create new Messenger from the given data.
     * <p>
     * In this method you can add references, addresses, emails and phones.
     *
     * @param dataRequest information of Messenger entity
     * @return saved entity
     */
    MessengerResponse add(MessengerRequest dataRequest);

    /**
     * Get Messenger information by id provided
     *
     * @param id entity identifier to search
     * @return searched entity
     */
    MessengerResponse get(Long id);

    /**
     * Get all messengers.
     *
     * @param dismissal true, if you need get list of messenger dismissal
     * @param category messenger category
     * @param search string to search
     * @param start initial index
     * @param size list size
     * @param orderBy property to ordering
     * @param orderType ASC or DESC
     *
     * @return messengers list.
     */
    ListResponse<MessengerResponse> getAll(Boolean dismissal, Integer category,
            String search, int start, int size, String orderBy,
            OrderType orderType, Boolean retire);

    /**
     * Update information of Messenger by id provided.
     * <p>
     * The references, addresses, emails and phones are updated from your
     * service.
     *
     * @param id entity identifier to update
     * @param dataRequest information of Messenger entity
     * @return saved entity
     */
    MessengerResponse update(Long id, MessengerRequest dataRequest);

    /**
     * Delete Messenger entity by id provided
     *
     * @param id entity identifier to delete
     * @return deleted entity
     */
    MessengerResponse delete(Long id);
    
    /**
     * This method allows to put a messenger in a retire state
     * 
     * @param id messenger id
     * @return Retired messenger
     */
    MessengerResponse retire(Long id);
    
    /**
     * This method allows comeback a messenger to be available in the system
     * 
     * @param id messenger id
     * @return messenger information
     */
    MessengerResponse comeback(Long id);

    /**
     * Get inventory of messenger from the given ID.
     *
     * @param messengerId the messenger ID
     * @return inventory info
     */
    List<InventoryInfoResponse> getInventoryInfo(Long messengerId);

}
