package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Shift;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftDao extends DataAccessObject<Shift, Shift.ShiftPK> {
    
    /**
     * Returns all the shift, filter by planning ID
     * 
     * @param shiftplanningId shiftplanning ID
     * @return shift list
     */
    List<Shift> findAll(Long shiftplanningId);
    
    /**
     * Returns the planning shift count
     * 
     * @param shiftplanningId shiftplanning count
     * @return result count
     */
    int count(Long shiftplanningId);
}
