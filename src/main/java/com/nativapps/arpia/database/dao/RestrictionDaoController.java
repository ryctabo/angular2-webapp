package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Restriction;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class RestrictionDaoController extends EntityDao<Restriction, Long>
        implements RestrictionDao {

    private static final RestrictionDaoController INSTANCE
            = new RestrictionDaoController();

    private RestrictionDaoController() {
        super(Restriction.class);
    }

    public static RestrictionDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Restriction findAllByMessengerId(Long messengerId) {
        return executeNamedQuery("restriction.findByMessengerId",
                new Parameter("messengerId", messengerId));
    }
}
