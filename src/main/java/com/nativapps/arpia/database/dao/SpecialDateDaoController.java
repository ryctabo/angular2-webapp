package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.SpecialDate;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class SpecialDateDaoController extends EntityDao<SpecialDate, Long>
        implements SpecialDateDao {

    private static final SpecialDateDaoController INSTANCE
            = new SpecialDateDaoController();

    private SpecialDateDaoController() {
        super(SpecialDate.class);
    }

    public static SpecialDateDaoController getInstance() {
        return INSTANCE;
    }

}
