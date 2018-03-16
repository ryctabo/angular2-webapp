package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.MessengerPlanning;
import com.nativapps.arpia.database.entity.Shiftplanning;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftplanningDao extends DataAccessObject<Shiftplanning, Long> {
    
    /**
     * Return all the shiftplanning entities
     * 
     * @param start initial index
     * @param size list size
     * @param operationId operation ID to filter
     * @return shiftplanning list
     */
    List<Shiftplanning> findAll(int start, int size, Long operationId);
    
    /**
     * Return the find all result count 
     * 
     * @param operationId operation to filter
     * @return result count
     */
    long findAllCount(Long operationId);
    
    /**
     * Search a shiftplanning day by provided date
     * 
     * @param date Date to search
     * @return search shiftplanning
     */
    Shiftplanning findByDate(Calendar date);
    
    /**
     * Returns all the messengers with his scheduling
     * 
     * @param start initial index
     * @param size list size
     * @param shiftplanningId shiftplanning ID
     * @param assign if true returns the messengers with shifts assigned, if false returns the messenger without shifts
     * @return messenger planning list
     */
    List<MessengerPlanning> getMessengerPlanning(int start, int size, Long shiftplanningId, 
            Boolean assign);
    
    /**
     * Returns the result count of get messenger planning
     * 
     * @param shiftplanningId shiftplanning ID
     * @param assign if true counts the messengers with shifts assigned, if false counts the messenger without shifts
     * @return result count
     */
    long getMessengerPlanningCount(Long shiftplanningId, Boolean assign);
}
