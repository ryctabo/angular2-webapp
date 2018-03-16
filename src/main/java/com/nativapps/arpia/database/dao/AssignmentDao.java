package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Assignment;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface AssignmentDao extends DataAccessObject<Assignment, Assignment.AssignmentPK> {

    /**
     * Get messenger list assigned to domicile execute.
     *
     * @param domicileExeId the domicile execute ID
     * @return list fo messenger assigned to domicile
     */
    List<Assignment> getAll(long domicileExeId);

    /**
     * Get count messenger assigned to domicile execute.
     *
     * @param domicileExeId the domicile execute ID
     * @return count of messenger assigned
     */
    int count(long domicileExeId);

}
