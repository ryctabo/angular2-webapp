package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Prohibition;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 *
 * @version 1.0
 */
public class ProhibitionDaoController extends EntityDao<Prohibition, Long>
        implements ProhibitionDao {

    private static final ProhibitionDaoController INSTANCE
            = new ProhibitionDaoController();

    private ProhibitionDaoController() {
        super(Prohibition.class);
    }

    public static ProhibitionDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Prohibition findAllByMessengerId(Long messengerId) {
        return executeNamedQuery("prohibition.findByMessengerId",
                new Parameter("messengerId", messengerId));
    }

}
