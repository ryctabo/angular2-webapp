package com.nativapps.arpia.service;

import com.nativapps.arpia.database.entity.StockType;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.StockRequest;
import com.nativapps.arpia.model.dto.StockResponse;

import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public interface StockService extends Service {

    /**
     * Get all stocks from the given parameters.
     *
     * @param inventoryId the inventory element ID
     * @param type        stock type, can be INPUT or OUTPUT
     * @param startDate   initial date
     * @param endDate     final date
     * @param start       index of first element to get
     * @param size        size of list
     * @return list of stocks
     */
    ListResponse<StockResponse> get(Long inventoryId, StockType type, Calendar startDate,
                                    Calendar endDate, int start, int size);

    /**
     * Get a stock from the given inventory element ID and index of stock.
     *
     * @param inventoryId inventory element ID
     * @param index       stock index
     * @return stock information
     */
    StockResponse get(Long inventoryId, Integer index);

    /**
     * Create a new stock with the inventory ID provided.
     *
     * @param inventoryId inventory ID
     * @param stock       stock data
     * @return stock data
     */
    StockResponse add(Long inventoryId, StockRequest stock);

}
