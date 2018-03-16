package com.nativapps.arpia.service;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.DiscountChangeLogResponse;
import java.util.List;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public interface DiscountChangeLogService extends Service{
    
    /**
     * Get all the discount change logs from the given discount ID.
     *
     * @param discountId discount ID
     * @return
     */
    List<DiscountChangeLogResponse> getLogs(Long discountId);

    /**
     * Get discount information from the given ID.
     *
     * @param id discount ID
     * @param logIndex discount change log index
     * @return
     */
    public DiscountChangeLogResponse getLog(Long id, Integer logIndex);
}
