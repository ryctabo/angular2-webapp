package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.CurriculumVitae;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class CurriculumVitaeDaoController extends EntityDao<CurriculumVitae, Long>
        implements CurriculumVitaeDao {

    private static final CurriculumVitaeDaoController INSTANCE
            = new CurriculumVitaeDaoController();

    private CurriculumVitaeDaoController() {
        super(CurriculumVitae.class);
    }

    public static CurriculumVitaeDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public CurriculumVitae findByMessengerId(Long messengerId) {
        return executeNamedQuery("cv.findByMessengerId",
                new Parameter("id", messengerId));
    }

}
