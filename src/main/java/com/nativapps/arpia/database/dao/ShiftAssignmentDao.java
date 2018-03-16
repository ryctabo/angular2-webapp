package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.entity.ShiftAssignment;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftAssignmentDao extends DataAccessObject<ShiftAssignment, 
        ShiftAssignment.ShiftAssignmentPK> {

    /**
     * Return all the shift assignment
     * 
     * @param id shift ID
     * @param clockin If true, allows to filter the messengers that have registered their clock in
     * @param clockout If true, allows to filter the messengers that have registered their clock out
     * @return shift assignment list
     */
    List<ShiftAssignment> findAll(Shift.ShiftPK id, Boolean clockin, 
            Boolean clockout);
    
    /**
     * Returns the shift assignment count
     * 
     * @param id shift ID
     * @return shift assignment count
     */
    long count(Shift.ShiftPK id);
}
