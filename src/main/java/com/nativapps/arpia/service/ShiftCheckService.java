package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ShiftCheckRequest;
import com.nativapps.arpia.model.dto.ShiftCheckResponse;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftCheckService extends Service {
    
    /**
     * Return all the checks from a shift
     * 
     * @param shiftplanningId shiftplanning ID
     * @param shiftIndex shift index
     * @param messengerId messenger ID to filter
     * @return shift check list
     */
    List<ShiftCheckResponse> getAll(Long shiftplanningId, Integer shiftIndex, 
            Long messengerId);
    
    /**
     * Start a shift
     * 
     * @param shiftplanningId shiftplanning ID
     * @param shiftIndex shift index
     * @param request check information
     * @return shift check
     */
    ShiftCheckResponse clockin(Long shiftplanningId, Integer shiftIndex, 
            ShiftCheckRequest request);
    
    /**
     * End a shift
     * 
     * @param shiftplanningId shiftplanning ID
     * @param shiftIndex shift index
     * @param request check information
     * @return shift check 
     */
    ShiftCheckResponse clockout(Long shiftplanningId, Integer shiftIndex, 
            ShiftCheckRequest request);
}
