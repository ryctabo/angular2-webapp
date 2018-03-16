package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Issue;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface IssueDao extends DataAccessObject<Issue, Long>{

    /**
     * Returns the issue list register in the database in a paginated way
     * 
     * @param start Initial index
     * @param size List size
     * @return Issue list
     */
    List<Issue> findAll(int start, int size);
    
    /**
     * Returns the find all result count 
     * 
     * @return Result count
     */
    long findAllCount();
}
