package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.CustomerDataDto;
import com.nativapps.arpia.model.dto.ListResponse;
import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface CustomerDataService extends Service {

    /**
     * Returns customer data list
     * 
     * @param start Initial indez
     * @param size List size
     * @param search String to filter the customer search
     * @return Customer data list
     */
    ListResponse<CustomerDataDto> getAll(int start, int size, String search);
    
    /**
     * Return a specific customer data by ID
     * 
     * @param id Customed ID
     * @return Return customer data entity
     */
    CustomerDataDto get(Long id);
    
    /**
     * Returns all the customers by not communication date
     * 
     * @param start Initial index
     * @param size List size
     * @param date Not communication date
     * @return Customer list
     */
    ListResponse<CustomerDataDto> getAllByNotCommunication(int start, int size, 
            Calendar date);
}
