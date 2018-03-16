package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.ChipInfo;
import com.nativapps.arpia.database.entity.Inventory;
import com.nativapps.arpia.database.entity.MessengerInfo;
import com.nativapps.arpia.model.OrderType;

import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public interface InventoryDao extends DataAccessObject<Inventory, Long> {

    /**
     * Get a item inventory with the given name.
     *
     * @param name the unique name
     * @return item of inventory
     */
    Inventory findByName(String name);

    /**
     * Get items of inventory.
     *
     * @param search  string to search
     * @param orderBy property to ordering
     * @param type    ASC or DESC
     * @param start   initial index
     * @param size    list size
     * @return list of inventory items
     */
    List<Inventory> findAll(String search, String orderBy, OrderType type,
                            int start, int size);

    /**
     * Get count of all items of inventory.
     *
     * @param search value to search
     * @return real size of the list
     */
    long count(String search);

    /**
     * Get all messengers that have the item with the given inventory ID.
     *
     * @param inventoryId inventory ID
     * @return messenger with inventory stocks of element.
     */
    List<MessengerInfo> getMessengers(long inventoryId);

    /**
     * Get amount of times that has been borrowed and returned that item of inventory.
     *
     * @param inventoryId the inventory ID
     * @return chip information
     */
    ChipInfo getChipInfo(long inventoryId);

}
