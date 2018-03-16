package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ShiftAssignRequest;
import com.nativapps.arpia.model.dto.ShiftAssignResponse;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftAssignmentService extends Service {
    
    /**
     * Return all the shift assigments
     * 
     * @param shiftplanningId shiftplanning ID
     * @param shiftIndex shift index
     * @param clockin If true, allows to filter the messengers that have registered their clock in
     * @param clockout If true, allows to filter the messengers that have registered their clock out
     * @return shift assigment list
     */
    List<ShiftAssignResponse> getAll(Long shiftplanningId, Integer shiftIndex, 
            Boolean clockin, Boolean clockout);
    
    /**
     * Assign a shift to a messenger
     * 
     * @param shiftplanningId shiftplanning ID
     * @param shiftIndex shift Index
     * @param request assignment data
     * @return shift assignment
     */
    ShiftAssignResponse assign(Long shiftplanningId, Integer shiftIndex, 
            ShiftAssignRequest request);
    
    /**
     * Delete a shift assigment to a messenger
     * 
     * @param shiftplanningId shiftplanning ID
     * @param shiftIndex shift Index
     * @param messengerId assignment data
     * @return delete shift assignment
     */
    ShiftAssignResponse delete(Long shiftplanningId, Integer shiftIndex, 
            Long messengerId);
}
