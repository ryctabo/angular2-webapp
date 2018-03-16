package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ShiftAssignRequest;
import com.nativapps.arpia.model.dto.ShiftAssignResponse;
import com.nativapps.arpia.model.dto.ShiftRequest;
import com.nativapps.arpia.model.dto.ShiftResponse;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftService extends Service {
    
    /**
     * Return all the shift in the planning day
     * 
     * @param shiftplanningId shiftplanning ID
     * @return Shift list
     */
    List<ShiftResponse> getAll(Long shiftplanningId);
    
    /**
     * Return a specific shift by shift ID and planning ID
     * 
     * @param shiftplanningId shiftplanning ID
     * @param index shift index
     * @return searched shift
     */
    ShiftResponse get(Long shiftplanningId, Integer index);
    
    /**
     * Create a shift in a planning day
     * 
     * @param shiftplanningId shiftplanning ID
     * @param request shift data
     * @return created shift
     */
    ShiftResponse add(Long shiftplanningId, ShiftRequest request);
    
    /**
     * Update a shift in a planning day
     * 
     * @param shiftplanningId shiftplanning ID
     * @param index shift index
     * @param request shift data to update
     * @return updated shift
     */
    ShiftResponse update(Long shiftplanningId, Integer index, ShiftRequest request);
    
    /**
     * Delete a shift in a planning day
     * 
     * @param shiftplanningId shiftplanning ID
     * @param index shift index
     * @return delete shift
     */
    ShiftResponse delete(Long shiftplanningId, Integer index);
}
