package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Schedule;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface ScheduleDao extends DataAccessObject<Schedule, Schedule.SchedulePK> {

    /**
     * Delete all schedules from the given map point ID.
     *
     * @param mapPointId the map point ID
     */
    void deleteByMapPointId(long mapPointId);

}
