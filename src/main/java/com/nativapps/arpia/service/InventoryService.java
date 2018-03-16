package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.*;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public interface InventoryService extends Service {

    /**
     * Get all inventory from the given parameters.
     *
     * @param search    value to search
     * @param orderBy   property to ordering
     * @param orderType ASC or DESC
     * @param start     index of first element to get
     * @param size      list size
     * @return list of inventory elements
     */
    ListResponse<InventoryResponse> getAll(String search, String orderBy,
                                           OrderType orderType, int start, int size);

    /**
     * Get a inventory element from the given ID.
     *
     * @param id element ID
     * @return inventory element
     */
    InventoryResponse get(Long id);

    /**
     * Create new inventory element from the given data.
     *
     * @param data inventory element provided
     * @return inventory element saved
     */
    InventoryResponse add(InventoryRequest data);

    /**
     * Update information of inventory element from the given ID,
     * you can only change the element name.
     *
     * @param id   element ID
     * @param data information of inventory entity
     * @return saved entity
     */
    InventoryResponse update(Long id, InventoryRequest data);

    /**
     * Delete inventory element from the given ID.
     *
     * @param id element ID
     * @return inventory element deleted
     */
    InventoryResponse delete(Long id);

    /**
     * Get all messengers that have the item with the given inventory ID.
     *
     * @param inventoryId the inventory ID
     * @return messengers info
     */
    List<MessengerInfoResponse> getMessengers(Long inventoryId);

    /**
     * Get chip information of a inventory element from the given ID.
     *
     * @param inventoryId element ID
     * @return chip info
     */
    ChipInfoResponse getChipInfo(Long inventoryId);

}
