package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Assignment;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class AssignmentDaoController extends EntityDao<Assignment, Assignment.AssignmentPK> implements AssignmentDao {

    private static final Logger LOG = Logger.getLogger(AssignmentDaoController.class.getName());

    private static final AssignmentDaoController INSTANCE = new AssignmentDaoController();

    private AssignmentDaoController() {
        super(Assignment.class);
    }

    public static AssignmentDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Assignment> getAll(long domicileExeId) {
        return this.executeNamedQueryForList("assignment.findByDomicileId",
                new Parameter("id", domicileExeId));
    }

    @Override
    public int count(long domicileExeId) {
        return Integer.parseInt("" + this.executeCountNamedQuery("assignment.countByDomicileId",
                new Parameter("id", domicileExeId)));
    }
}
