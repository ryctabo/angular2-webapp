package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MessengerPlanningResponse;
import com.nativapps.arpia.model.dto.ShiftplanningRequest;
import com.nativapps.arpia.model.dto.ShiftplanningResponse;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftplanningService extends Service {
    
    /**
     * Returns all the shiftplannings days in a paginated way
     * 
     * @param start initial index
     * @param size list size
     * @param operationId operation ID to filter
     * @return shifplanning list
     */
    ListResponse<ShiftplanningResponse> getAll(int start, int size, Long operationId);
    
    /**
     * Return a specific shiftplanning by provided ID
     * 
     * @param id Shiftplanning ID
     * @return Shiftplanning entity
     */
    ShiftplanningResponse get(Long id);
    
    /**
     * Create a new shiftplanning day
     * 
     * @param request Shiftplanning information
     * @return Created shiftplanning
     */
    ShiftplanningResponse add(ShiftplanningRequest request);
    
    /**
     * Delete a shiftplanning day by provided OD
     * 
     * @param id Shiftplanning ID
     * @return Deleted Shiftplanning day
     */
    ShiftplanningResponse delete(Long id);
    
    /**
     * Returns all the messengers with his scheduling
     * 
     * @param start initial index
     * @param size list size
     * @param shiftplanningId shiftplanning ID
     * @param assign if true returns the messengers with shifts assigned, if false returns the messenger without shifts
     * @return messenger planning list
     */
    ListResponse<MessengerPlanningResponse> getMessengerPlanning(int start, int size, 
            Long shiftplanningId, Boolean assign);
}
