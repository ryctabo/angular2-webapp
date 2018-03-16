package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Stock;
import com.nativapps.arpia.database.entity.StockType;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public interface StockDao extends DataAccessObject<Stock, Stock.StockPK> {

    /**
     * Get all stocks from the given parameters.
     *
     * @param inventoryId the inventory element ID
     * @param type        stock type, can be INPUT or OUTPUT
     * @param startDate   initial date
     * @param endDate     final date
     * @param start       index of first element to get
     * @param size        size of list
     * @return stocks list
     */
    List<Stock> findAll(Long inventoryId, StockType type, Calendar startDate,
                        Calendar endDate, int start, int size);

    /**
     * Get count of total elements in stocks list from the given parameters.
     *
     * @param inventoryId the inventory element ID
     * @param type        stock type, can be INPUT or OUTPUT
     * @param startDate   initial date
     * @param endDate     final date
     * @return total count stocks
     */
    long count(Long inventoryId, StockType type, Calendar startDate,
               Calendar endDate);

}
