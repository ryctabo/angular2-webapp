package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.entity.ShiftAssignment;
import com.nativapps.arpia.database.entity.ShiftCheck;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftCheckDao extends DataAccessObject<ShiftCheck, Long> {
    
    /**
     * Find all the checks by shift ID
     * 
     * @param shiftId shift ID
     * @param messengerId messenger ID to filter
     * @return shift check list
     */
    List<ShiftCheck> findAll(Shift.ShiftPK shiftId, Long messengerId);
    
    /**
     * Find a check by shift assigment ID
     * 
     * @param assignId shift assignment ID
     * @return searched shift check
     */
    ShiftCheck find(ShiftAssignment.ShiftAssignmentPK assignId);
}
