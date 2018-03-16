package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Issue;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class IssueDaoController extends EntityDao<Issue, Long> 
        implements IssueDao {
    
    private static final IssueDaoController INSTANCE 
            = new IssueDaoController();

    private  IssueDaoController() {
        super(Issue.class);
    }

    public static IssueDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Issue> findAll(int start, int size) {
        return executeNamedQueryForList("issue.findAll", start, size);
    }

    @Override
    public long findAllCount() {
        return executeCountNamedQuery("issue.findAllCount");
    }

}
