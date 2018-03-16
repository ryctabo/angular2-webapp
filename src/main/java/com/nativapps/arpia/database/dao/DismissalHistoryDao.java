package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.DismissalHistory;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface DismissalHistoryDao extends DataAccessObject<DismissalHistory, Long> {

    /**
     * Get all dismissal history from the given messenger ID.
     *
     * @param messengerId the messenger ID
     * @return list of dismissal history
     */
    List<DismissalHistory> findAll(long messengerId);

}
