package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.InventoryInfo;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.model.OrderType;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Paheco <ryctabo@gmail.com>
 *
 * @version 1.2.0
 */
public interface MessengerDao extends DataAccessObject<Messenger, Long> {

    /**
     * Return a filter Messenger list by string
     *
     * @param start Initial index
     * @param size List size
     * @return Filter list
     */
    List<Messenger> findAll(int start, int size);

    /**
     * Return a filter {@link Messenger} list.
     *
     * @param dismissal true, if you need get list of messenger dismissal
     * @param category messenger category
     * @param search string to search
     * @param start initial index
     * @param size list size
     * @param orderBy property to ordering
     * @param orderType ASC or DESC
     * @param retire allows to filter
     *
     * @return messenger list
     */
    List<Messenger> findAll(Boolean dismissal, Integer category,
            String search, int start, int size, String orderBy,
            OrderType orderType, Boolean retire);

    /**
     * Get count of all register inner messenger table.
     *
     * @param dismissal true, if you need get list of messenger dismissal
     * @param category messenger category
     * @param search string to search
     * @param retire allows to filter
     * @return total count of messengers
     */
    long getCount(Boolean dismissal, Integer category, String search, Boolean retire);

    /**
     * Get inventory of a messenger from the given ID.
     *
     * @param messengerId messenger ID
     * @return inventory info
     */
    List<InventoryInfo> getInventoryInfo(long messengerId);
}
